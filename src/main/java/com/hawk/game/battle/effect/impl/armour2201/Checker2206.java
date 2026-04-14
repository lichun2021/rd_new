package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleSoldier_7;
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
 * 【2206】攻城（兵种类型 = 7）
【万分比】自身攻城车成规模时，己方远程部队损伤首次超过20%（effect2206BaseVaule）或者战斗结束时仅一次生效，该部队进入【应急】状态，该部队当前部队损失的 1%【2206】*己方成规模兵种数量立即复原；当己方成规模攻城车数量不超过2（effect2206NumLimit）个时，复原数量增加50%（effect2206RestorePlus）
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 自身攻城车成规模时
  - 成规模兵种参考新概念介绍，辅助需要自身兵种足够数量才能生效辅助技能
- 己方远程部队损伤首次超过20%（effect2206BaseVaule）或者战斗结束时仅一次生效
  - 固定值：万分比
  - 远程部队类型包含有：直升机（兵种类型 = 4）、突击步兵（兵种类型 = 5）、狙击兵（兵种类型 = 6）、攻城车（兵种类型 = 7）
  - 触发条件1 则 不再触发条件2
  - 每场战斗仅触发一次
- 该部队进入【应急】状态，该部队当前部队损失的 1%【2206】*己方成规模兵种数量立即复原
- 当己方成规模攻城车数量不超过2（effect2206NumLimit）个时
  - 成规模兵种参考新概念介绍
    - 己方成规模攻城车≤2 生效
  - 固定值：绝对值
- 复原数量增加50%（effect2206RestorePlus）
  - 实际复原数量 = 触发时当前部队损失 * 【作用值】*己方成规模兵种数量*（1+effect2206RestorePlus）
  - 固定值：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2206)
public class Checker2206 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type != SoldierType.CANNON_SOLDIER_7 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.CANNON_SOLDIER_7);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int effect2206ContinueRound = ConstProperty.getInstance().effect2206ContinueRound;
			if (mass505TypeArmy.size() <= ConstProperty.getInstance().effect2206NumLimit) {
				effect2206ContinueRound += ConstProperty.getInstance().effect2206ContinueRoundPlus;
			}
			effPer = parames.unity.getEffVal(effType()) * mass505Army;
			BattleSoldier_7 soldier = (BattleSoldier_7) parames.solider;
			soldier.buff2206.eff2206Val = effPer;
			soldier.buff2206.effect2206ContinueRound = effect2206ContinueRound;
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
