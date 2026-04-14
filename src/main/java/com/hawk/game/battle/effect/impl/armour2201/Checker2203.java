package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleSoldier_5;
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
 * 【2203】突击（兵种类型 = 5）
【万分比】回合开始时，有 4%（effect2203BaseVaule）*己方成规模兵种数量 概率获得【突进】状态，自身数量最多的突击立即对敌方全体近战部队进行一次攻击，伤害率XX.XX%【2203】；当己方成规模突击步兵数量不超过 2（effect2203NumLimit） 个时，触发概率增加2%（effect2203BaseVaulePlus）*己方成规模兵种数量
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 回合开始时，有 4%（effect2203BaseVaule）*己方成规模兵种数量 概率获得【突进】状态
  - 固定值：万分比
- 自身数量最多突击立即对敌方全体近战部队进行一次攻击，伤害率XX.XX%【2203】
  - 伤害率为常规伤害率效果
    - 即 实际伤害 = 基础伤害 *（1 + 各类加成）*【2203作用值】
      - 配置格式：万分比
- 当己方成规模突击步兵数量不超过 2（effect2203NumLimit） 个时，
  - 成规模兵种参考新概念介绍
    - 成规模突击≤2 生效
  - 固定值：绝对值
- 触发概率增加2%（effect2203BaseVaulePlus）*己方成规模兵种数量
  - 实际概率数量 =（effect2203BaseVaule + effect2203BaseVaulePlus）*己方成规模兵种数量
  - 固定值：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2203)
public class Checker2203 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type != SoldierType.FOOT_SOLDIER_5 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.FOOT_SOLDIER_5);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int cen = ConstProperty.getInstance().effect2203BaseVaule;
			if (mass505TypeArmy.size() <= ConstProperty.getInstance().effect2203NumLimit) {
				cen += ConstProperty.getInstance().effect2203BaseVaulePlus;
			}
			BattleSoldier_5 plan = (BattleSoldier_5) parames.solider;
			plan.eff2203Pct = cen* mass505Army;
			effPer = parames.unity.getEffVal(effType());
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
