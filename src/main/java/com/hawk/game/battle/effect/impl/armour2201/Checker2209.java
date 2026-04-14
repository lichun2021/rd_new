package com.hawk.game.battle.effect.impl.armour2201;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【2209】主战&轰炸（兵种类型 = 2&3）
【万分比】战斗 15 （effect2209StarRound）回合开始，每5（effect2209AtkRound）回合开始时，提升自身部队攻击12.00%【2209】*敌方最多的单兵种排数，持续 3 （effect2209ContinueRound）回合； 期间自身部队生命和防御降低12.00%（effect2209BaseVaule）
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 战斗 15 （effect2209StarRound）回合开始，每5（effect2209AtkRound）回合开始时
  - 固定值：绝对值
- 提升自身部队攻击增加12.00%【2209】*敌方最多的单兵种排数
  - 外围攻击增加
    - 加法
  - 敌方最多的单兵种排数
    - 指敌方兵种单位最多的兵种，其数量
- 持续 3 （effect2209ContinueRound）回合
  - 固定值：绝对值
- 期间自身部队生命和防御减少12.00%（effect2209BaseVaule）
  - 外围生命和防御数值减少
    - 减法
  - 固定值：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2209)
public class Checker2209 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
//		String playerId = parames.unity.getPlayerId();
//		if (parames.unitStatic.getPlayerSoldierCountMarch().get(playerId, SoldierType.TANK_SOLDIER_2)
//				+ parames.unitStatic.getPlayerSoldierCountMarch().get(playerId, SoldierType.PLANE_SOLDIER_3) <= 0) {
//			return CheckerKVResult.DefaultVal;
//		}
		
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			effPer = parames.unity.getEffVal(effType()) * parames.tarStatic.maxArmyPaiCount();
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
