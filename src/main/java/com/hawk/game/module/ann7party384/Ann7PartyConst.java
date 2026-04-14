package com.hawk.game.module.ann7party384;

public class Ann7PartyConst {
	/**
	 * 跨服请求处理的类所在包路径
	 */
	public static final String CROSS_PROC_PACKAGE = "com.hawk.game.module.ann7party384.cross";
	/**
	 * 服务器分组信息存储key
	 */
	public static final String GROUP_LOCK_KEY = "ann7PartyGroupLock:";
	/**
	 * 房间信息存储key
	 */
	public static final String PARTY_ROOM_KEY = "ann7PartyRoom:";
	/**
	 * 房间聚会记录存储key
	 */
	public static final String PARTY_RECORD_KEY = "ann7PartyRecord:";
	/**
	 * 排队信息
	 */
	public static final String PARTY_QUEUE_KEY = "ann7PartyQueue:";
	public static final String PARTY_QUEUE_RANK_KEY = "ann7PartyQueueRank:";
	public static final String PARTY_QUEUE_ROBOT_KEY = "ann7PartyQueueRobot:";
	/**
	 * 抽奖锁（同一时间只能有一个人选择奖励）
	 */
	public static final String PARTY_AWARD_LOCK_KEY = "ann7PartyAwardLock:";
	/**
	 * 机器人检测
	 */
	public static final String CHECK_ROBOT_KEY = "ann7PartyCheckRobot:";
	
	/**
	 * (跨服)玩家信息
	 */
	public static final String PLAYER_INFO_KEY = "ann7PartyPlayerInfo:";
	
	/**
	 * 拒绝邀请的时间
	 */
	public static final String INVITE_REFUSE_KEY = "ann7PartyRefuseInvite:";
	
	
	/** 服务器操作 */
	public static class CrossServerOper {
		public static final int ROOM_DISSOLVE    = 1; //解散房间
		public static final int REC_QUEUE_PLAYER = 2; //接收排队玩家进入房间
		public static final int INTO_ROUND_SHOW  = 3; //本轮抽奖结束进入展示期
		public static final int SWITCH_ROUND     = 4; //奖励轮次切换
		public static final int SEARCH_ROLE_REQ  = 5; //搜索玩家请求
		public static final int SEARCH_ROLE_RESP = 6; //搜索玩家回应
	}
	
	public static final String SHARE_SERVER = "server";
	
	/**
	 * 成功加入房间
	 */
	public static final int JOIN_ROOM_SUCC = 1;
	/**
	 * 机器人房间设置
	 */
	public static final int ROBOT_ROOM_AUTO_START = 1; //人满10s后自动开始聚会，1是0否
	public static final int ROBOT_ROOM_JOIN_APPLY = 0; //加入需申请，1是0否
	/**
	 * 商店购买消耗货币类型
	 */
	public static final int SHOP_BUY_RMB  = 1;
	public static final int SHOP_BUY_COIN = 2;
	
	/**
	 * 加入房间的方式
	 */
	public static class RoomJoinType {
		public static final int JOIN_DIRECT      = 1; //直接加入
		public static final int DEAL_INVITE      = 2; //接受邀请加入
		public static final int JOIN_APPLY_AGREE = 3; //房主同意申请加入
		public static final int REC_BY_ROBOT     = 4; //被系统自动加入机器人房间
	}
	
	public static final String ROBOT_PREFIX = "NPC_";
}
