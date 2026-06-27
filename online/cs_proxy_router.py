#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
CrossProxy Router - ZMQ 跨服中转服务
用于替代原有的 ZMQ Router 中转，负责在多个游戏服之间转发消息。

协议格式 (DEALER -> ROUTER):
  Frame 1: identity (自动添加, ROUTER 自动处理)
  Frame 2: ProxyHeader (文本, '|' 分隔)
           格式: type|from|to|source|target|rpcid|timestamp
           type: 0=PROTOCOL, 1=NOTIFY, 2=RPC_REQ, 3=RPC_REP, 4=BROADCAST, 99=HEART_BEAT
  Frame 3: protocol body (二进制, 非心跳消息才有)
  Frame 4: broadcast ids (二进制, 仅广播消息才有)

路由逻辑:
  - 心跳(99): 回显给发送方, 用于keepalive
  - 其他消息: 根据 header.to 字段查找目标服的 identity, 转发过去

用法:
  python cs_proxy_router.py [bind_port]
  python cs_proxy_router.py 8280
"""

import sys
import time
import signal
import logging
from datetime import datetime

try:
    import zmq
except ImportError:
    print("ERROR: pyzmq not installed. Run: pip install pyzmq")
    sys.exit(1)

# ============================================================
# 配置
# ============================================================
DEFAULT_PORT = 8280
STATS_INTERVAL = 10  # 统计输出间隔(秒)
IDENTITY_EXPIRE = 120  # identity 过期时间(秒), 超过此时间没心跳则移除

# ============================================================
# 日志
# ============================================================
logging.basicConfig(
    level=logging.INFO,
    format='%(asctime)s %(levelname)s [%(name)s] %(message)s',
    datefmt='%Y-%m-%d %H:%M:%S'
)
logger = logging.getLogger('CSProxyRouter')


class ProxyHeader:
    """解析 ProxyHeader, 格式: type|from|to|source|target|rpcid|timestamp"""

    TYPE_PROTOCOL = 0
    TYPE_NOTIFY = 1
    TYPE_RPC_REQ = 2
    TYPE_RPC_REP = 3
    TYPE_BROADCAST = 4
    TYPE_HEART_BEAT = 99

    TYPE_NAMES = {
        0: 'PROTOCOL', 1: 'NOTIFY', 2: 'RPC_REQ', 3: 'RPC_REP',
        4: 'BROADCAST', 99: 'HEART_BEAT'
    }

    def __init__(self, raw_bytes):
        self.raw = raw_bytes
        self.raw_str = raw_bytes.decode('utf-8', errors='replace')
        parts = self.raw_str.split('|', -1)

        self.type = int(parts[0]) if len(parts) > 0 and parts[0] else -1
        self.from_server = parts[1] if len(parts) > 1 else ''
        self.to_server = parts[2] if len(parts) > 2 else ''
        self.source = parts[3] if len(parts) > 3 else ''
        self.target = parts[4] if len(parts) > 4 else ''
        self.rpcid = parts[5] if len(parts) > 5 else ''
        self.timestamp = parts[6] if len(parts) > 6 else ''

    @property
    def type_name(self):
        return self.TYPE_NAMES.get(self.type, f'UNKNOWN({self.type})')

    def is_heartbeat(self):
        return self.type == self.TYPE_HEART_BEAT

    def is_valid(self):
        return self.type >= 0 and self.from_server != ''

    def __repr__(self):
        return f'[{self.type_name}] {self.from_server}->{self.to_server} src={self.source} tgt={self.target}'


class CSProxyRouter:
    """ZMQ ROUTER 跨服中转服务"""

    def __init__(self, bind_port):
        self.bind_port = bind_port
        self.context = zmq.Context()
        self.router = None
        self.running = False

        # identity -> serverId 映射 (一个serverId可能有多个identity, 如 identifyMode>0)
        # 但我们用 serverId -> identity 来做路由
        self.server_identities = {}   # serverId -> identity (bytes)
        self.identity_servers = {}    # identity (bytes) -> serverId
        self.identity_heartbeat = {}  # identity (bytes) -> last_heartbeat_time

        # 统计
        self.stats_time = time.time()
        self.stats_forwarded = 0
        self.stats_heartbeat = 0
        self.stats_dropped = 0
        self.stats_received = 0

    def start(self):
        """启动中转服务"""
        self.router = self.context.socket(zmq.ROUTER)
        
        # 设置 ROUTER 选项
        self.router.setsockopt(zmq.ROUTER_MANDATORY, 0)  # 不强制路由, 找不到目标不报错
        self.router.setsockopt(zmq.SNDHWM, 100000)
        self.router.setsockopt(zmq.RCVHWM, 100000)
        self.router.setsockopt(zmq.LINGER, 1000)

        bind_addr = f"tcp://*:{self.bind_port}"
        self.router.bind(bind_addr)
        self.running = True

        logger.info("=" * 60)
        logger.info("  CrossProxy Router started")
        logger.info(f"  Listening on: {bind_addr}")
        logger.info(f"  Stats interval: {STATS_INTERVAL}s")
        logger.info("=" * 60)

        try:
            self._event_loop()
        except KeyboardInterrupt:
            logger.info("Shutting down...")
        finally:
            self.router.close()
            self.context.term()
            logger.info("Router stopped.")

    def _event_loop(self):
        """主事件循环"""
        poller = zmq.Poller()
        poller.register(self.router, zmq.POLLIN)

        while self.running:
            try:
                events = dict(poller.poll(timeout=100))  # 100ms 超时
            except KeyboardInterrupt:
                break

            if self.router in events:
                self._handle_message()

            # 定期输出统计
            now = time.time()
            if now - self.stats_time >= STATS_INTERVAL:
                self._print_stats()
                self._cleanup_expired()
                self.stats_time = now

    def _handle_message(self):
        """处理收到的消息"""
        try:
            frames = self.router.recv_multipart(zmq.NOBLOCK)
        except zmq.Again:
            return
        except Exception as e:
            logger.error(f"recv error: {e}")
            return

        if len(frames) < 2:
            logger.warning(f"Invalid message: only {len(frames)} frames")
            return

        self.stats_received += 1

        # Frame 0: sender identity (ROUTER 自动加的)
        sender_identity = frames[0]
        # Frame 1: ProxyHeader
        header_bytes = frames[1]

        try:
            header = ProxyHeader(header_bytes)
        except Exception as e:
            logger.error(f"Failed to parse header: {e}, raw={header_bytes[:200]}")
            return

        if not header.is_valid():
            logger.warning(f"Invalid header from identity={sender_identity}: {header.raw_str[:200]}")
            return

        # 注册/更新 identity 映射
        self._register_identity(sender_identity, header)

        if header.is_heartbeat():
            # 心跳: 回显给发送方
            self._handle_heartbeat(sender_identity, header, frames)
        else:
            # 转发消息
            self._forward_message(sender_identity, header, frames)

    def _register_identity(self, identity, header):
        """注册 identity 与 serverId 的映射"""
        server_id = header.from_server
        if not server_id:
            return

        # 对于心跳, from_server 就是发送方的 serverId
        # 对于 identifyMode > 0, identity 格式为 "serverId@uuid"
        # 我们用 source 字段来获取带 @uuid 的完整 identity 标识
        
        old_identity = self.server_identities.get(server_id)
        if old_identity != identity:
            if old_identity:
                # 清理旧映射
                self.identity_servers.pop(old_identity, None)
                self.identity_heartbeat.pop(old_identity, None)
                logger.info(f"Server [{server_id}] identity updated: {old_identity} -> {identity}")
            else:
                logger.info(f"Server [{server_id}] registered, identity={identity}")

            self.server_identities[server_id] = identity
            self.identity_servers[identity] = server_id

        self.identity_heartbeat[identity] = time.time()

    def _handle_heartbeat(self, sender_identity, header, frames):
        """处理心跳: 原样回显"""
        self.stats_heartbeat += 1
        try:
            # 回显: [sender_identity, header_bytes]
            self.router.send_multipart([sender_identity, frames[1]], zmq.NOBLOCK)
        except zmq.Again:
            logger.warning(f"Heartbeat reply failed (send buffer full) to {header.from_server}")
        except Exception as e:
            logger.error(f"Heartbeat reply error: {e}")

    def _forward_message(self, sender_identity, header, frames):
        """转发消息到目标服务器"""
        target_server = header.to_server
        if not target_server:
            logger.warning(f"No target server in header: {header}")
            self.stats_dropped += 1
            return

        # 查找目标服的 identity
        target_identity = self.server_identities.get(target_server)
        if target_identity is None:
            # 目标服未注册, 尝试用 serverId 作为 identity (identifyMode=0 的情况)
            target_identity = target_server.encode('utf-8')
            logger.debug(f"Server [{target_server}] not registered, trying raw identity")

        # 构造转发帧: [target_identity] + [header + body ...]
        forward_frames = [target_identity] + frames[1:]

        try:
            self.router.send_multipart(forward_frames, zmq.NOBLOCK)
            self.stats_forwarded += 1
        except zmq.Again:
            logger.warning(f"Forward failed (buffer full): {header}")
            self.stats_dropped += 1
        except Exception as e:
            logger.error(f"Forward error: {e}, header: {header}")
            self.stats_dropped += 1

    def _print_stats(self):
        """输出统计信息"""
        servers = list(self.server_identities.keys())
        logger.info(
            f"Stats: received={self.stats_received}, forwarded={self.stats_forwarded}, "
            f"heartbeat={self.stats_heartbeat}, dropped={self.stats_dropped}, "
            f"servers={servers}"
        )
        self.stats_received = 0
        self.stats_forwarded = 0
        self.stats_heartbeat = 0
        self.stats_dropped = 0

    def _cleanup_expired(self):
        """清理过期的 identity"""
        now = time.time()
        expired = []
        for identity, last_hb in self.identity_heartbeat.items():
            if now - last_hb > IDENTITY_EXPIRE:
                expired.append(identity)

        for identity in expired:
            server_id = self.identity_servers.pop(identity, None)
            self.identity_heartbeat.pop(identity, None)
            if server_id:
                if self.server_identities.get(server_id) == identity:
                    self.server_identities.pop(server_id, None)
                logger.warning(f"Server [{server_id}] expired (no heartbeat for {IDENTITY_EXPIRE}s)")

    def stop(self):
        self.running = False


def main():
    port = DEFAULT_PORT
    if len(sys.argv) > 1:
        try:
            port = int(sys.argv[1])
        except ValueError:
            print(f"Usage: {sys.argv[0]} [port]")
            print(f"Example: {sys.argv[0]} 8280")
            sys.exit(1)

    router = CSProxyRouter(port)

    # 优雅退出
    def signal_handler(sig, frame):
        logger.info(f"Received signal {sig}, stopping...")
        router.stop()

    signal.signal(signal.SIGINT, signal_handler)
    signal.signal(signal.SIGTERM, signal_handler)

    router.start()


if __name__ == '__main__':
    main()
