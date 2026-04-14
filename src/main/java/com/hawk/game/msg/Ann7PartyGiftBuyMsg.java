package com.hawk.game.msg;

import org.hawk.msg.HawkMsg;

public class Ann7PartyGiftBuyMsg extends HawkMsg {
	/**
	 * 购买的gift
	 */
	private String payGiftId;
	public Ann7PartyGiftBuyMsg(String giftId) {
		this.payGiftId = giftId;
	}
	public String getPayGiftId() {
		return payGiftId;
	}
	public void setPayGiftId(String payGiftId) {
		this.payGiftId = payGiftId;
	}
}
