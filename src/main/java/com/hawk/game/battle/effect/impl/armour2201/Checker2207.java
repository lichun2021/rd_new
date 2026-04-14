package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleSoldier_1;
import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【2207】防坦（兵种类型 = 1）
【万分比】自身防御坦克成规模时，自身防坦数量每损失10%（effect2207BaseVaule）时触发一次【联防】，己方随机2（effect2207AtkNums）个近战单位获得 2%【2207】*己方成规模兵种数量生命护盾（优先选取本场战斗被选取次数较少目标）；当己方成规模防坦数量不超过 2（effect2207NumLimit） 个时，目标增加1（effect2207AtkNumsPlus）个
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 自身防御坦克成规模时
  - 成规模兵种参考新概念介绍，辅助需要自身兵种足够数量才能生效辅助技能
- 自身防坦数量每损失10%（effect2207BaseVaule）时触发一次【联防】
  - 固定值：万分比
  - 若固定值10%，则在自身防坦数量90%、80%、70%……20%、10%、0%各触发一次
- 己方随机2（effect2207AtkNums）个近战单位获得 2%【2207】*己方成规模兵种数量生命护盾
  - 近战部队类型包含有：防御坦克（兵种类型 = 1）、采矿车（兵种类型 = 8）、主战坦克（兵种类型 = 2）、轰炸机（兵种类型 = 3）
- （优先选取本场战斗被选取次数较少目标）
  - 优先选择整场战斗中，被该技能选取次数较低的部队
  - 如有ABCD，选2个
    - 本次随机选到选AB，下次就会选CD，再下次则又重新在ABCD中随机2个
  - 如有ABCD，选3个
    - 本次随机选到选ABC，下次就会选D+ABC中选取2个
- 当己方成规模防坦数量不超过 2（effect2207NumLimit） 个时
  - 成规模兵种参考新概念介绍
    - 己方成规模防御坦克≤2 生效
  - 固定值：绝对值
- 目标增加1（effect2207AtkNumsPlus）个
  - 实际目标数量 = effect2207AtkNums + effect2207AtkNumsPlus
  - 固定值：绝对值
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2207)
public class Checker2207 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type != SoldierType.TANK_SOLDIER_1 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.TANK_SOLDIER_1);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int effect2207AtkNums = ConstProperty.getInstance().effect2207AtkNums;
			if (mass505TypeArmy.size() <= ConstProperty.getInstance().effect2207NumLimit) {
				effect2207AtkNums += ConstProperty.getInstance().effect2207AtkNumsPlus;
			}
			effPer = parames.unity.getEffVal(effType()) * mass505Army;
			BattleSoldier_1 soldier = (BattleSoldier_1) parames.solider;
			soldier.effect2207AtkNums = effect2207AtkNums;
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
