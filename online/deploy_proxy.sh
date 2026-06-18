#!/bin/bash
# ============================================================
# CrossProxy Router 一键部署脚本
# 用法: bash deploy_proxy.sh [port]
# 示例: bash deploy_proxy.sh 8280
# ============================================================

set -e

PORT=${1:-8280}
SCRIPT_DIR="$(cd "$(dirname "$0")" && pwd)"
PROXY_SCRIPT="$SCRIPT_DIR/cs_proxy_router.py"
PID_FILE="$SCRIPT_DIR/cs_proxy_router.pid"
LOG_FILE="$SCRIPT_DIR/cs_proxy_router.log"

echo "============================================"
echo "  CrossProxy Router Deploy Script"
echo "============================================"
echo ""

# 1. 检查 Python3
if ! command -v python3 &> /dev/null; then
    echo "[ERROR] python3 not found!"
    echo "Install: yum install -y python3  OR  apt install -y python3"
    exit 1
fi

PYTHON_VER=$(python3 --version 2>&1)
echo "[OK] $PYTHON_VER"

# 2. 检查/安装 pip
if ! python3 -m pip --version &> /dev/null; then
    echo "[INFO] Installing pip..."
    python3 -m ensurepip --default-pip 2>/dev/null || {
        echo "[INFO] Trying get-pip.py..."
        curl -sSL https://bootstrap.pypa.io/get-pip.py | python3
    }
fi

# 3. 安装 pyzmq
echo "[INFO] Checking pyzmq..."
if ! python3 -c "import zmq" 2>/dev/null; then
    echo "[INFO] Installing pyzmq..."
    python3 -m pip install pyzmq --quiet
fi

ZMQ_VER=$(python3 -c "import zmq; print(f'pyzmq={zmq.__version__}, libzmq={zmq.zmq_version()}')")
echo "[OK] $ZMQ_VER"

# 4. 检查脚本是否存在
if [ ! -f "$PROXY_SCRIPT" ]; then
    echo "[ERROR] cs_proxy_router.py not found at: $PROXY_SCRIPT"
    exit 1
fi
echo "[OK] Script: $PROXY_SCRIPT"

# 5. 停止旧进程(如果有)
if [ -f "$PID_FILE" ]; then
    OLD_PID=$(cat "$PID_FILE")
    if kill -0 "$OLD_PID" 2>/dev/null; then
        echo "[INFO] Stopping old process (PID=$OLD_PID)..."
        kill "$OLD_PID"
        sleep 2
        # 强制杀
        if kill -0 "$OLD_PID" 2>/dev/null; then
            kill -9 "$OLD_PID" 2>/dev/null
        fi
    fi
    rm -f "$PID_FILE"
fi

# 也检查端口占用
EXISTING_PID=$(lsof -ti :$PORT 2>/dev/null || true)
if [ -n "$EXISTING_PID" ]; then
    echo "[WARN] Port $PORT is in use by PID=$EXISTING_PID"
    echo "       Kill it? (y/n)"
    read -r answer
    if [ "$answer" = "y" ]; then
        kill -9 $EXISTING_PID 2>/dev/null
        sleep 1
    else
        echo "[ABORT] Please free port $PORT first."
        exit 1
    fi
fi

# 6. 启动
echo ""
echo "[INFO] Starting CrossProxy Router on port $PORT..."
nohup python3 "$PROXY_SCRIPT" "$PORT" >> "$LOG_FILE" 2>&1 &
NEW_PID=$!
echo $NEW_PID > "$PID_FILE"

sleep 1

if kill -0 "$NEW_PID" 2>/dev/null; then
    echo ""
    echo "============================================"
    echo "  CrossProxy Router started successfully!"
    echo "  PID:  $NEW_PID"
    echo "  Port: $PORT"
    echo "  Log:  $LOG_FILE"
    echo "  PID:  $PID_FILE"
    echo "============================================"
    echo ""
    echo "Commands:"
    echo "  View logs:   tail -f $LOG_FILE"
    echo "  Stop:        kill \$(cat $PID_FILE)"
    echo "  Restart:     bash $0 $PORT"
else
    echo "[ERROR] Failed to start! Check log: $LOG_FILE"
    tail -20 "$LOG_FILE"
    exit 1
fi
