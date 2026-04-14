package com.hawk.game.player.tick.impl;

import com.hawk.game.guild.activestat.PlayerActiveStatService;
import com.hawk.game.player.Player;
import com.hawk.game.player.tick.PlayerTickLogic;

public class ActiveStatTicker implements PlayerTickLogic {

	@Override
	public void onTick(Player player) {
		PlayerActiveStatService.getInstance().savePlayerActiveStatInfo(player.getId());
	}

}
