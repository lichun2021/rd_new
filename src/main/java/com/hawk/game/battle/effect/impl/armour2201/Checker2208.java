package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleSoldier_8;
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
 * 【2208】采矿（兵种类型 = 8）
【万分比】自身采矿车成规模时，战斗中每5（effect2208AtkRound）回合开始时，为我方随机2（effect2208AtkNums）个近战单位设置【陷阱】（持续2（effect2208ContinueRound）回合，优先选取本场战斗未选取目标），
如果敌方攻击了带有【陷阱】的单位，则减少攻击方XX.XX%【2208】*己方成规模兵种数量的下次参与攻击的部队数量；当己方成规模采矿数量不超过 2（effect2208NumLimit） 个时，选择目标增加1（effect2208AtkNumsPlus）个
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 自身采矿车成规模时
  - 成规模兵种参考新概念介绍，辅助需要自身兵种足够数量才能生效辅助技能
- 战斗中每5（effect2208AtkRound）回合开始时
  - 固定值：绝对值
- 为我方随机2（effect2208AtkNums）个近战单位设置【陷阱】（持续2（effect2208ContinueRound）回合
  - 固定值：绝对值
  - 近战部队类型包含有：防御坦克（兵种类型 = 1）、采矿车（兵种类型 = 8）、主战坦克（兵种类型 = 2）、轰炸机（兵种类型 = 3）
- 优先选取本场战斗未选取目标）
  - 优先选择整场战斗中，被该技能选取次数较低的部队
- 如果敌方攻击了带有【陷阱】的单位，则减少攻击方XX.XX%【2208】*己方成规模兵种数量的下次参与攻击的部队数量；
  - 敌方部队无法进行攻击逻辑为：敌方部队下一回合伤害计算时，部队中兵的数量计算时减少即可
    - 即 实际攻击的兵的数量 = 原本数量 * （1 - 【作用值】- 其他兵力减少作用号）
      - 配置格式：万分比
- 当己方成规模采矿数量不超过 2（effect2208NumLimit） 个时
  - 成规模兵种参考新概念介绍
    - 己方成规模采矿车≤2 生效
  - 固定值：绝对值
- 选择目标增加1（effect2208AtkNumsPlus）个
  - 实际目标数量 = effect2208AtkNums + effect2208AtkNumsPlus
  - 固定值：绝对值
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2208)
public class Checker2208 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type != SoldierType.CANNON_SOLDIER_8 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.CANNON_SOLDIER_8);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int effect2208AtkNums = ConstProperty.getInstance().effect2208AtkNums;
			if (mass505TypeArmy.size() <= ConstProperty.getInstance().effect2208NumLimit) {
				effect2208AtkNums += ConstProperty.getInstance().effect2208AtkNumsPlus;
			}
			effPer = parames.unity.getEffVal(effType()) * mass505Army;
			BattleSoldier_8 soldier = (BattleSoldier_8) parames.solider;
			soldier.effect2208AtkNums = effect2208AtkNums;
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
