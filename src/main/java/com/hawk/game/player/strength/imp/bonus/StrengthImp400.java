package com.hawk.game.player.strength.imp.bonus;

import java.util.Map;
import org.hawk.config.HawkConfigManager;
import com.hawk.game.config.SoldierStrengthTypeCfg;
import com.hawk.game.module.homeland.PlayerHomeLandModule;
import com.hawk.game.module.homeland.cfg.HomeLandBuildingCfg;
import com.hawk.game.module.homeland.cfg.HomeLandBuildingTypeCfg;
import com.hawk.game.player.Player;
import com.hawk.game.player.strength.imp.PlayerStrengthCell;
import com.hawk.game.protocol.Const.SoldierType;
import com.hawk.game.util.GsConst;

/**
 * 家园装扮类型
 * @author lating
 *
 */
@StrengthType(strengthType = 400)
public class StrengthImp400 implements StrengthBonusImp {
	
	@Override
	public void calc(Player player, SoldierType soldierType, PlayerStrengthCell cell) {
		SoldierStrengthTypeCfg typeCfg = getStrengthTypeCfg();
		int atkValue = 0, hpValue = 0, shdValue = 0;
		PlayerHomeLandModule module = player.getModule(GsConst.ModuleType.HOME_LAND_MODULE);
		Map<Integer, HomeLandBuildingCfg> cfgMap = module.getHomeLandBuildMaxLevelCfg();
		for (HomeLandBuildingCfg cfg : cfgMap.values()) {
			HomeLandBuildingTypeCfg buildTypeCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandBuildingTypeCfg.class, cfg.getBuildType());
			if (buildTypeCfg == null) {
				continue;
			}
			atkValue += buildTypeCfg.getAtkAttr(soldierType.getNumber()) * cfg.getLevel();
        	hpValue += buildTypeCfg.getHpAttr(soldierType.getNumber()) * cfg.getLevel();
        	shdValue += buildTypeCfg.getShdAttr(soldierType.getNumber()) * cfg.getLevel();
		}
		
		cell.setAtk(Math.min(atkValue, typeCfg.getAtkAttrMax()));
		cell.setHp(Math.min(hpValue, typeCfg.getHpAttrMax()));
		cell.setShd(Math.min(shdValue, typeCfg.getShdAttrMax()));
	}
	
}