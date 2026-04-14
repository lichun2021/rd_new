package com.hawk.game.msg;

import org.hawk.msg.HawkMsg;

import com.hawk.gamelib.GameConst.MsgId;

/**
 * 消息 - 取消科技研究队列消息
 * 
 * @author Jesse
 *
 */
public class TechQueueFinishMsg extends HawkMsg {
	/**
	 * 科技Id
	 */
	int scienceId;
	/**
	 * 升级次数
	 */
	int multi;

	public int getScienceId() {
		return scienceId;
	}

	public int getMulti() {
		return multi;
	}

	public void setScienceId(int scienceId) {
		this.scienceId = scienceId;
	}
	
	public TechQueueFinishMsg() {
		super(MsgId.TECH_QUEUE_FINISH);
	}

	/**
	 * 构造消息对象
	 * 
	 * @return
	 */
	public static TechQueueFinishMsg valueOf(int scienceId, int multi) {
		TechQueueFinishMsg msg = new TechQueueFinishMsg();
		msg.scienceId = scienceId;
		msg.multi = multi;
		return msg;
	}
}
