package com.hawk.game.player.tick.impl;

import java.util.Objects;

import org.hawk.app.HawkApp;

import com.hawk.game.entity.GuildMemberObject;
import com.hawk.game.player.Player;
import com.hawk.game.player.strength.PlayerStrengthFactory;
import com.hawk.game.player.tick.PlayerTickLogic;
import com.hawk.game.service.GuildService;
import com.hawk.game.util.GsConst;

public class PlayerStrengthTicker implements PlayerTickLogic {

	@Override
	public void onTick(Player player) {
		// 跨服不重新计算战力
		if (player.isCsPlayer()) {
			return;
		}
		long lastCalcStrengthTime = player.getTickTimeLine().getLastCalcStrengthTime();
		long currentTime = HawkApp.getInstance().getCurrentTime();
		// 5min计算一次
		if (currentTime - lastCalcStrengthTime < GsConst.MINUTE_MILLI_SECONDS * 5) {
			return;
		}
		player.getTickTimeLine().setLastCalcStrengthTime(currentTime);
		//如果去兵战力没有变化，则不重算
		long power = player.getNoArmyPower();
		if(player.getTickTimeLine().getLastCalcStrengthPoint() == power){
			return;
		}
		//重算战力
		long strengthPower = PlayerStrengthFactory.getInstance().calcStrength(player);
		player.updateStength(strengthPower);
		player.getTickTimeLine().setLastCalcStrengthPoint(power);
		GuildMemberObject gmember = GuildService.getInstance().getGuildMemberObject(player.getId());
		if(Objects.nonNull(gmember)){
			gmember.updateStrengthPower(strengthPower);
		}
	}

}
