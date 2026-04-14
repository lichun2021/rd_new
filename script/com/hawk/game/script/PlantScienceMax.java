package com.hawk.game.script;

import java.util.Map;

import com.hawk.game.module.plantsoldier.science.PlantScience;
import com.hawk.game.module.plantsoldier.science.PlantScienceComponent;
import com.hawk.game.module.plantsoldier.science.PlantScienceState;
import com.hawk.game.module.plantsoldier.science.cfg.PlantScienceCfg;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.os.HawkException;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;

import com.hawk.game.GsConfig;
import com.hawk.game.global.GlobalData;
import com.hawk.game.player.Player;
import com.hawk.log.LogConst.PowerChangeReason;

/**
 * 泰能科技一键满级
 * http://localhost:8080/script/plantScienceMax?playerId=1aax-42ky73-1
 * @author zhy
 *
 */
public class PlantScienceMax extends HawkScript {
	
	@Override
	public String action(Map<String, String> params, HawkScriptHttpInfo httpInfo) {
		return doAction(params);
	}
	
	public static String doAction(Map<String, String> params) {
		if (!GsConfig.getInstance().isDebug()) {
			return HawkScript.failedResponse(SCRIPT_ERROR, "");
		}
		
		try {
			if (!GsConfig.getInstance().isDebug()) {
				return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "is not debug");
			}

			Player player = GlobalData.getInstance().scriptMakesurePlayer(params);
			if (player == null) {
				return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "player not exist");
			}
			PlantScience plantScience = player.getPlantScience();
			for (Map.Entry<Integer, Integer> techIdEntry : PlantScienceCfg.getTechIdLevelMaxMap().entrySet()) {
				PlantScienceComponent component = plantScience.getComponentScienceId(techIdEntry.getKey());
				if (component == null) {
					component = plantScience.createScienceComponent(techIdEntry.getKey(), techIdEntry.getValue());
				}
				component.setState(0);
			}
			plantScience.notifyChange();
			player.refreshPowerElectric(PowerChangeReason.PLANT_SCIENCE_LEVEL_UP);
			return HawkScript.successResponse("ok");
		} catch (Exception e) {
			HawkException.catchException(e);
			return HawkException.formatStackMsg(e);
		}
	}
}
