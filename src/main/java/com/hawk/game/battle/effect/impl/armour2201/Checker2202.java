package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleSoldier_3;
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
 * 【【2202】轰炸
【万分比】回合开始时，自身数量最多轰炸机有8%（effect2202BaseVaule）*己方成规模兵种数量 概率触发【覆盖打击】，对敌方随机目标及其附近1（effect2202AtkNums）个单位造成一次额外攻击（优先选取步兵单位），
伤害率XX.XX%【2202】；当己方成规模轰炸数量不超过 2（effect2202umLimit） 个时，触发概率增加4%（effect2202BaseVaulePlus）*己方成规模兵种数量
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 回合开始时，自身数量最多轰炸机有8%（effect2202BaseVaule）*己方成规模兵种数量 概率触发【覆盖打击】
  - 固定值：万分比
- 对敌方随机目标及其附近1（effect2202AtkNums）个单位造成一次额外攻击（优先选取步兵单位）
  - 固定值：绝对值
  - 步兵单位包含：突击步兵（兵种类型 = 5）、狙击兵（兵种类型 = 6）
    - 无步兵会随机选择其他兵种
- 伤害率XX.XX%【2202】
  - 伤害率为常规伤害率效果
    - 即 实际伤害 = 基础伤害 *（1 + 各类加成）*【2202作用值】
      - 配置格式：万分比
- 当己方成规模轰炸数量不超过 2（effect2202NumLimit） 个时，【覆盖打击】目标增加1（effect2202AtkNumsPlus）个
  - 成规模兵种参考新概念介绍
    - 成规模轰炸≤2 生效
- 触发概率增加4%（effect2202BaseVaulePlus）*己方成规模兵种类型数量
  - 实际触发概率 = （effect2202BaseVaule+effect2202BaseVaulePlus）*己方成规模兵种类型数量
  - 固定值：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2202)
public class Checker2202 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type != SoldierType.PLANE_SOLDIER_3 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.PLANE_SOLDIER_3);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int cen = ConstProperty.getInstance().effect2202BaseVaule;
			if (mass505TypeArmy.size() <= ConstProperty.getInstance().effect2202umLimit) {
				cen += ConstProperty.getInstance().effect2202BaseVaulePlus;
			}
			BattleSoldier_3 plan = (BattleSoldier_3) parames.solider;
			plan.eff2202Pct = cen* mass505Army;
			effPer = parames.unity.getEffVal(effType());
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
