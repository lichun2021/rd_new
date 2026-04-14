package com.hawk.game.battle.effect.impl.hero1118;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12782)
public class Checker12782 implements IChecker {
	/**
	 * 【12782】
- 【万分比】【12782】苍翎战翼：战翼展开化为鹰隼虚影，进行远距离精准信息获取，每X（effect12782AtkRound）回合开始时，引导我方全体部队对敌方全体进行精准打击，己方本回合伤害增加 XX.XX%【12782】->针对己方兵种留个内置系数effect12782SoldierAdjust
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 每X（effect12782AtkRound）回合开始时
    - 指定数值读取const表，字段effect12782AtkRound
      - 配置格式：绝对值
  - 引导我方全体部队对敌方全体进行精准打击，己方本回合伤害增加 XX.XX%【12782】->针对己方兵种留个内置系数effect12782SoldierAdjust
    - 该作用号为外围伤害加成效果，与其他伤害增加作用号累加计算（与12101/10082等伤害增加作用号加法运算）
      - 即 实际伤害 = 基础伤害*（1 + 各类伤害加成+【12782作用值】* 敌方兵种修正系数/10000）
        - 配置格式：万分比
      - 实际针对己方各兵种类型，单独配置系数；自身兵种修正系数 读取const表，字段effect12782SoldierAdjust
        - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
          - 修正系数具体配置为万分比
	 */
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.solider.getEffVal(EffType.HERO_12781) == 0) {
			return CheckerKVResult.DefaultVal;
		}
		int effPer = 0;
		int effNum = 0;
		effPer = parames.unity.getEffVal(effType());
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
