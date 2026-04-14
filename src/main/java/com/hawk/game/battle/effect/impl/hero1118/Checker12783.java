package com.hawk.game.battle.effect.impl.hero1118;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12783)
public class Checker12783 implements IChecker {
	/**
	 * 【12783】
- 【万分比】【12783】金羽壁垒：战翼收拢并重构为 电浆盾牌，期间展开特效屏障抵御高强度攻击。期间自身防御坦克为我方全体非防御坦克部队承担XX.XX%【12783】伤害（->针对敌方兵种留个内置系数effect12783SoldierAdjust）。和埃琳娜一同出征时，承伤率额外增加XX.XX%（effect12783ExtraVaule）。自己防坦抵挡该部分伤害时，获得XX.XX%（effect12783BaseVaule）的减伤。
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 期间自身防御坦克为我方全体非防御坦克部队承担XX.XX%【12783】伤害->针对敌方兵种留个内置系数effect12783SoldierAdjust
    - 此效果与现有埃琳娜的伤害分摊【12282】机制，规则完全相同，此处判定优先级低于埃琳娜和薇拉
      - 承伤顺序严格按照判定优先级，具体为在造成任意伤害时，按以下顺序分摊
        1. 【12461】薇拉缠斗分摊伤害
        2. 【12282】埃琳娜远程庇分摊伤害
        3. 本次【12783】分摊节点
      - 【受攻击方】分摊1次后伤害 = 基础伤害 * （1 - 【本作用值】* 敌方兵种修正系数/10000）（一次分摊）
        - 【受攻击方】实际受伤 = 【受攻击方】分摊1次后伤害 * （1 - 【防御坦克A】【12783】* 敌方兵种修正系数/10000 - 【防御坦克B】【12783】* 敌方兵种修正系数/10000）（二次分摊）
          - 以此类推
          - 实际针对敌方各兵种类型，单独配置系数；自身兵种修正系数 读取const表，字段effect12783SoldierAdjust
            - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
              - 修正系数具体配置为万分比
    - 注：仅替非防御坦克（兵种id=1）分摊伤害，若伤害目标为己方该防御坦克单位，则自然无效
  - 和埃琳娜一同出征时，承伤率额外增加XX.XX%（effect12783ExtraVaule）
    - 埃琳娜（id：1089），拥有作用号12081
    - 即上述公式中 【12783】 值变为 【12783】+（effect12783ExtraVaule）
  - 自己防坦抵挡该部分伤害时，获得XX.XX%（effect12783BaseVaule）的减伤。
    - 承受来源于分摊技能【12783】的伤害时，获得额外减伤
      - 常规减伤效果，与其他减伤作用号累乘计算，即 
        - 最终伤害 = 基础伤害 *（1 + 各类加成）*（1 - 各类减免）*（1 - （effect12783BaseVaule））
          - 配置格式：万分比
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
