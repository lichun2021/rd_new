package com.hawk.game.player.strength.imp.bonus;

import org.hawk.config.HawkConfigManager;
import com.hawk.game.config.SoldierStrengthTypeCfg;
import com.hawk.game.module.homeland.cfg.HomeLandProsperityAttrCfg;
import com.hawk.game.module.homeland.entity.HomeLandComponent;
import com.hawk.game.module.homeland.entity.PlayerHomeLandEntity;
import com.hawk.game.player.Player;
import com.hawk.game.player.strength.imp.PlayerStrengthCell;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 家园繁荣度类型
 * @author lating
 *
 */
@StrengthType(strengthType = 410)
public class StrengthImp410 implements StrengthBonusImp {
	
	@Override
	public void calc(Player player, SoldierType soldierType, PlayerStrengthCell cell) {
		SoldierStrengthTypeCfg typeCfg = getStrengthTypeCfg();
		int atkValue = 0, hpValue = 0, shdValue = 0;
		PlayerHomeLandEntity entity = player.getData().getHomeLandEntity();
        HomeLandComponent component = entity.getComponent();
        for (int attrId : component.getAttrComp().getActiveProsperityAttrSet()) {
        	HomeLandProsperityAttrCfg attrCfg = HawkConfigManager.getInstance().getConfigByKey(HomeLandProsperityAttrCfg.class, attrId);
        	if (attrCfg == null) {
        		continue;
        	}
        	atkValue += attrCfg.getAtkAttr(soldierType.getNumber());
        	hpValue += attrCfg.getHpAttr(soldierType.getNumber());
        	shdValue += attrCfg.getShdAttr(soldierType.getNumber());
        }
        
		cell.setAtk(Math.min(atkValue, typeCfg.getAtkAttrMax()));
		cell.setHp(Math.min(hpValue, typeCfg.getHpAttrMax()));
		cell.setShd(Math.min(shdValue, typeCfg.getShdAttrMax()));
	}
	
}