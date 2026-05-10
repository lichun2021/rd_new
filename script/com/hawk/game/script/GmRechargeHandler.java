package com.hawk.game.script;

import java.util.Map;
import java.util.Optional;

import org.hawk.app.HawkApp;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.hawk.activity.ActivityManager;
import com.hawk.activity.event.impl.DiamondRechargeEvent;
import com.hawk.activity.event.impl.FirstRechargeEvent;
import com.hawk.activity.event.impl.RechargeMoneyEvent;
import com.hawk.game.config.PayCfg;
import com.hawk.game.config.PayGiftCfg;
import com.hawk.game.entity.RechargeEntity;
import com.hawk.game.global.GlobalData;
import com.hawk.game.msg.PlayerRechargeGrantItemMsg;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Script.ScriptError;
import com.hawk.game.recharge.RechargeManager;
import com.hawk.game.util.GsConst;
import com.hawk.game.util.GsConst.DiamondPresentReason;
import com.hawk.game.recharge.RechargeType;
import com.hawk.log.Action;

/**
 * 模拟充值回调通知入口
 * 
 * localhost:8080/script/gmRecharge?playerName=?&rechargeType=?&goodsId=?
 * 
 * recharteType 1：充值 2：礼包
 * goodsId 充值：pay.xml的key 礼包：payGift.xml的key
 * 
 * @author hawk
 */
public class GmRechargeHandler extends HawkScript {
	static Logger logger = LoggerFactory.getLogger("Recharge");

	@Override
	public String action(Map<String, String> params, HawkScriptHttpInfo httpInfo) {
		Player player = GlobalData.getInstance().scriptMakesurePlayer(params);
		if (player == null) {
			return HawkScript.failedResponse(ScriptError.PARAMS_ERROR_VALUE, "player not found");
		}

		// 1 充值 2礼包
		int rechargeType = Integer.parseInt(params.get("rechargeType"));
		// 物品id
		String goodsId = params.get("goodsId");

		// 初始化返回json对象
		JSONObject result = new JSONObject();

		String billno = params.get("billno");

		if (rechargeType == RechargeType.RECHARGE) {
			gmRecharge(player, goodsId, billno, result);

		} else if (rechargeType == RechargeType.GIFT) {
			gmBuyGift(player, goodsId, billno, result);

		} else {
			result.put("result", "rechargeType error!");
		}

		return result.toJSONString();
	}

	/**
	 * 充值
	 * 
	 * @param player
	 * @param goodsId
	 */
	public void gmRecharge(Player player, String goodsId, String billno, JSONObject result) {
		// 获取充值配置
		PayCfg payCfg = HawkConfigManager.getInstance().getConfigByKey(PayCfg.class, goodsId);
		if (payCfg == null) {
			result.put("result", "payCfg is null!");
			return;
		}

		int diamonds = payCfg.getGainDia();

		// 使用平台传入的订单号，没有则自动生成
		String orderId = HawkOSOperator.isEmptyString(billno) ? HawkOSOperator.randomUUID() : billno;
		RechargeManager.getInstance().createRechargeRecord(player, orderId, "",
				payCfg.getId(), (int) (payCfg.getPayRMB() * GsConst.RECHARGE_BASE), payCfg.getPayRMB(),
				"RMB", RechargeType.RECHARGE, HawkTime.getSeconds(), diamonds);

		// 发放钻石并触发充值成功全套流程（复用IDIP模拟充值逻辑）
		int playerSaveAmt = player.getPlayerBaseEntity().getSaveAmt();
		int diamondsBefore = player.getDiamonds();
		player.increaseDiamond(diamonds, Action.IDIP_CHANGE_PLAYER_ATTR, null, DiamondPresentReason.COMPENSATION);
		int oldNum = player.getPlayerBaseEntity().getSaveAmt();
		player.getPlayerBaseEntity().setSaveAmt(oldNum + diamonds);
		player.rechargeSuccess(playerSaveAmt, diamonds, diamondsBefore);
		player.getPlayerBaseEntity().setSaveAmt(oldNum);

		player.getPush().syncHasFirstRecharge();
		result.put("result", "recharge success!!");
	}

	/**
	 * 礼包
	 * 
	 * @param player
	 * @param goodsId
	 */
	public void gmBuyGift(Player player, String goodsId, String billno, JSONObject result) {
		PayGiftCfg payGiftCfg = HawkConfigManager.getInstance().getConfigByKey(PayGiftCfg.class, goodsId);
		if (payGiftCfg == null) {
			result.put("result", "payGiftCfg is null!");
			return;
		}

		// 构造参数
		String orderId = HawkOSOperator.isEmptyString(billno) ? HawkOSOperator.randomUUID() : billno;
		int payMoney = payGiftCfg.getPayRMB() / 10;

		// 创建订单
		RechargeManager.getInstance().createRechargeRecord(player, orderId, "", goodsId,
				payMoney, payMoney * 10, "RMB", RechargeType.GIFT, HawkTime.getSeconds(), payGiftCfg.getGainDia());

		// 发货通知
		HawkApp.getInstance().postMsg(player.getXid(),
				PlayerRechargeGrantItemMsg.valueOf(payGiftCfg, orderId, payMoney * 10));
		result.put("result", "buy gift success!");
	}
}
