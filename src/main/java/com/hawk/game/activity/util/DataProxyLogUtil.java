package com.hawk.game.activity.util;

import com.hawk.game.global.GlobalData;
import com.hawk.game.player.Player;

public class DataProxyLogUtil {

	public static void recordTlog(String playerId, TlogCallback callback) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player != null) {
			callback.log(player);
		}
	}
}
