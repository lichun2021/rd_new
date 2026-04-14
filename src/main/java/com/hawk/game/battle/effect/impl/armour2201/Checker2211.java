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
 * 【2211】直升&攻城（兵种类型 = 4&7）
【万分比】自身直升或者攻城成规模时，战斗 15（effect2211StarRound） 回合开始，每 5 （effect2211AtkRound）回合开始时，使所有友方部队攻击增加6.00% 【2211】* 敌方最多的单兵种排数，持续 3（effect2211ContinueRound） 回合；期间自身部队防御和生命降低12.00%（effect2211BaseVaule）
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 自身直升或者攻城成规模时
  - 成规模兵种参考新概念介绍，辅助需要自身兵种足够数量才能生效辅助技能
- 战斗 15 （effect2211StarRound）回合开始，每5（effect2211AtkRound）回合开始时
  - 固定值：绝对值
- 使所有友方部队攻击增加6.00% * 敌方最多的单兵种排数
  - 外围攻击增加
    - 加法
  - 敌方最多的单兵种排数
    - 指敌方兵种单位最多的兵种，其数量
- 持续 3 （effect2211ContinueRound）回合
  - 固定值：绝对值
- 期间自身部队生命和防御减少12.00%（effect2211BaseVaule）
  - 外围生命和防御数值减少
    - 减法
  - 固定值：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2211)
public class Checker2211 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		
		if (parames.unity.getEffVal(effType()) == 0 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int effPer = 0;
		int effNum = 0;
		if (parames.type == SoldierType.PLANE_SOLDIER_4 || parames.type == SoldierType.CANNON_SOLDIER_7) {
			effPer = parames.unity.getEffVal(effType()) * parames.tarStatic.maxArmyPaiCount();
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
