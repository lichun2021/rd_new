package com.hawk.game.battle.effect.impl.hero1118;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12787)
public class Checker12787 implements IChecker {
	/**
	 * - 【万分比】【12785~12787】流纹释放：流纹达到指定层数时自身防御坦克触发追加效果：
  - 2 （effect12785AtkThresholdValue1）层：战翼形态时，鹰隼虚影附带眩晕效果，触发精准打击时，敌方所有XX.XX%【12785】部队下个回个无法攻击。（乘法结算）->针对敌方兵种留个内置系数effect12785SoldierAdjust）
  - 4（effect12785AtkThresholdValue2） 层：壁垒形态时，特效屏障生效时，无视目标XX.XX%【12786】攻击加成。（乘法结算）->针对敌方兵种留个内置系数effect12786SoldierAdjust）
  - 6 （effect12785AtkThresholdValue3）层：释放流纹中蕴含的武器数据，自身部队各兵种攻击、防御、生命增加+XX.XX%【12787】*盾面纹路中该兵种刻印次数 ->针对己方兵种留个内置系数effect12787SoldierAdjust）
  - 8 （effect12785AtkThresholdValue4）层：自身防御坦克同时获得【苍翎战翼】和【金羽壁垒】形态效果
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 2 （effect12785AtkThresholdValue1）层：战翼形态时，鹰隼虚影附带眩晕效果，敌方所有部队XX.XX%【12785】部队下个回个无法攻击。（乘法结算）->针对敌方兵种留个内置系数effect12785SoldierAdjust）
    - 指定数值读取const表，字段effect12785AtkThresholdValue1
      - 配置格式：绝对值
    - 敌方所有部队无法进行攻击逻辑为完全同作用号【12724】效果
      - 敌方所有部队下一回合伤害计算时，部队中兵的数量计算时减少即可
      - 即 实际攻击的兵的数量 = 原本数量 * （1 - A防坦【12785作用值】* 敌方兵种修正系数/10000-B防坦【12785作用值】* 敌方兵种修正系数/10000）
        - 配置格式：万分比
      - 实际针对敌方各兵种类型，单独配置系数；自身兵种修正系数 读取const表，字段effect12785SoldierAdjust
        - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
          - 修正系数具体配置为万分比
  - 4（effect12785AtkThresholdValue2） 层：壁垒形态时，特效屏障生效时，无视目标XX.XX%【12786】攻击加成。（乘法结算）->针对敌方兵种留个内置系数effect12786SoldierAdjust）
    - 指定数值读取const表，字段effect12785AtkThresholdValue1
      - 配置格式：绝对值
    - 无视敌方攻击加成指敌方造成伤害时，计算攻击力时，减少【12786】的攻击加成
      - 即 敌方攻击= 原本攻击  - 【12786作用值】* 敌方兵种修正系数/10000 
        - 实际针对敌方各兵种类型，单独配置系数；自身兵种修正系数 读取const表，字段effect12786SoldierAdjust
          - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
            - 修正系数具体配置为万分比
  - 6 （effect12785AtkThresholdValue3）层：释放流纹中蕴含的武器数据，己方部队各兵种攻击、防御、生命增加+XX.XX%【12787】*盾面纹路中该兵种刻印次数 ->针对己方兵种留个内置系数effect12787SoldierAdjust）
    - 指定数值读取const表，字段effect12785AtkThresholdValue3
      - 配置格式：绝对值
    - 该作用号为常规外围属性加成效果，与其他作用号累加计算
      - 即 实际属性 = 基础属性*（1 + 各类加成 +【12787作用值】*盾面纹路中该兵种刻印次数* 己方兵种修正系数/10000）
        - 盾面纹路中该兵种刻印次数 生效逻辑同 作用号【12784】
        - 实际针对己方方各兵种类型，单独配置系数；自身兵种修正系数 读取const表，字段effect12787SoldierAdjust
        - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
          - 修正系数具体配置为万分比
  - 8 （effect12785AtkThresholdValue4）层：自身防御坦克同时获得【苍翎战翼】和【金羽壁垒】形态效果
    - 指定数值读取const表，字段effect12785AtkThresholdValue4
      - 配置格式：绝对值
    - 叠层为回合结束时，则下个回合开始时变为双形态即可
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
