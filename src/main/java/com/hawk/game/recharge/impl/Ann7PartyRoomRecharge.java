package com.hawk.game.recharge.impl;

import org.hawk.log.HawkLog;

import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384ShopCfg;
import com.hawk.activity.type.impl.ann7party384.entity.Ann7Party384Entity;
import com.hawk.game.GsApp;
import com.hawk.game.config.PayGiftCfg;
import com.hawk.game.entity.RechargeEntity;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.msg.Ann7PartyGiftBuyMsg;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Status;
import com.hawk.game.protocol.Recharge.RechargeBuyItemRequest;
import com.hawk.game.recharge.AbstractGiftRecharge;
import com.hawk.game.recharge.RechargeType;

/**
 * 
 * 周年聚会礼包
 * 
 * @author lating
 *
 */
public class Ann7PartyRoomRecharge extends AbstractGiftRecharge {
	
	@Override
	public boolean detailGiftBuyCheck(Player player, PayGiftCfg giftCfg, RechargeBuyItemRequest req, int protocol) {
		if (!Ann7Party384Service.getInstance().isActivityOpening(player.getId())) {
			player.sendError(protocol, Status.Error.ACTIVITY_NOT_OPEN_VALUE, 0);
			return false;
		}
		if (Ann7Party384Service.getInstance().nearActivityEnd()) {
			player.sendError(protocol, Status.Error.ANN7_PARTY_NEAR_ACT_END_VALUE, 0);
			HawkLog.errPrintln("anny party shop buy gift error: {}, playerId: {}", "near act end", player.getId());
			return false;
		}
		Ann7Party384ShopCfg shopCfg = Ann7Party384ShopCfg.getCfgByGoodsId(giftCfg.getId());
		if (shopCfg == null) {
			player.sendError(protocol, Status.SysError.CONFIG_ERROR_VALUE, 0);
			return false;
		}
		Ann7Party384Entity entity = Ann7Party384Service.getInstance().getDBEntity(player.getId());
		if (entity == null) {
			player.sendError(protocol, Status.SysError.DATA_ERROR_VALUE, 0);
			return false;
		}
		if (entity.getShopBuyCount(shopCfg.getId()) >= shopCfg.getTimes()) {
			player.sendError(protocol, Status.Error.ANN7_PARTY_SHOP_BUY_LIMIT_VALUE, 0);
			return false;
		}
		return true;
	}

	@Override
	public boolean deliverGoodsDetail(Player player, PayGiftCfg giftCfg, RechargeEntity rechargeEntity) {
		GsApp.getInstance().postMsg(player, new Ann7PartyGiftBuyMsg(giftCfg.getId()));
		return true;
	}

	@Override
	public int getGiftType() {
		return RechargeType.ANN7_PARTY_GIFT;
	}

}
