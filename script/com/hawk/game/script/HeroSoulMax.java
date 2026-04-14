package com.hawk.game.script;

import java.util.Map;

import com.hawk.game.config.HeroSoulLevelCfg;
import com.hawk.game.config.HeroSoulStageCfg;
import com.hawk.game.module.plantsoldier.science.PlantScience;
import com.hawk.game.module.plantsoldier.science.PlantScienceComponent;
import com.hawk.game.module.plantsoldier.science.cfg.PlantScienceCfg;
import com.hawk.game.player.hero.PlayerHero;
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
 * http://localhost:8080/script/heroSoulMax?playerId=1aax-42ky73-1
 *
 * @author zhy
 */
public class HeroSoulMax extends HawkScript {

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
            for (PlayerHero hero : player.getAllHero()) {
                if (hero.getConfig().getSoulOpen() == 0) {
                    continue;
                }
                ConfigIterator<HeroSoulLevelCfg> soulLevels = HawkConfigManager.getInstance().getConfigIterator(HeroSoulLevelCfg.class);
                hero.getSoul().getSoulLevel().clear();
                Map<Integer, Integer> soulLevel = hero.getSoul().getSoulLevel();
                for (HeroSoulLevelCfg heroSoulLevelCfg : soulLevels) {
                    if (heroSoulLevelCfg.getHero() != hero.getCfgId()) {
                        continue;
                    }
                    soulLevel.put(heroSoulLevelCfg.getStage(), heroSoulLevelCfg.getLevel());
                }
                ConfigIterator<HeroSoulStageCfg> soulStages = HawkConfigManager.getInstance().getConfigIterator(HeroSoulStageCfg.class);
                hero.getSoul().getSoulStage().clear();
                for (HeroSoulStageCfg soulStage : soulStages) {
                    if (soulStage.getHero() != hero.getCfgId()) {
                        continue;
                    }
                    hero.getSoul().getSoulStage().add(soulStage.getId());
                }
                hero.notifyChange();
            }
            player.refreshPowerElectric(PowerChangeReason.PLANT_SCIENCE_LEVEL_UP);
            return HawkScript.successResponse("ok");
        } catch (Exception e) {
            HawkException.catchException(e);
            return HawkException.formatStackMsg(e);
        }
    }
}
