package com.hawk.game.player.strength.imp.bonus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hawk.log.HawkLog;
import org.hawk.tuple.HawkTuple3;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.config.SoldierStrengthTypeCfg;
import com.hawk.game.player.Player;
import com.hawk.game.player.hero.PlayerHero;
import com.hawk.game.player.hero.rise.HeroRise;
import com.hawk.game.player.strength.imp.PlayerStrengthCell;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 英雄星穹觉醒类型
 * @author lating
 *
 */
@StrengthType(strengthType = 420)
public class StrengthImp420 implements StrengthBonusImp {
	
	@Override
	public void calc(Player player, SoldierType soldierType, PlayerStrengthCell cell) {
		SoldierStrengthTypeCfg typeCfg = getStrengthTypeCfg();
		int atkValue = 0, hpValue = 0, shdValue = 0;
		List<PlayerHero> heros = new ArrayList<>();
		 for (PlayerHero hero : player.getAllHero()) {
            if (hero != null && hero.getRise() != null && hero.getRise().getPower() > 0) {
                heros.add(hero);
            }
        }
		 
	    heros.sort(Comparator
            .comparingInt((PlayerHero hero) -> hero.getRise().getPower())
            .thenComparingInt(PlayerHero::getCfgId).reversed()
        );
	    
	    for (int i = 0; i < heros.size(); i++) {
	    	boolean shouldEffect = i <= ConstProperty.getInstance().heroRiseNum - 1; // 是否应该生效（简化判断）
	    	if (!shouldEffect) {
	    		continue;
	    	}
			HeroRise rise = heros.get(i).getRise();
			HawkTuple3<Integer, Integer, Integer> tuple = rise.getNodeAttr(soldierType.getNumber());
			atkValue += tuple.first;
			hpValue += tuple.second;
			shdValue += tuple.third;
			HawkLog.logPrintln("calc player strength hero, playerId:{}, SoldierType:{}, StrengthType:420, heroId: {}, atk:{}, hp:{}, shd: {}", 
					player.getId(), soldierType.getNumber(), heros.get(i).getCfgId(), tuple.first, tuple.second, tuple.third);
		}
		
	    cell.setAtk(Math.min(atkValue, typeCfg.getAtkAttrMax()));
		cell.setHp(Math.min(hpValue, typeCfg.getHpAttrMax()));
		cell.setShd(Math.min(shdValue, typeCfg.getShdAttrMax()));
	}
	
}