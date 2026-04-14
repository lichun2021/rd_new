package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
/**- 【万分比】【12837】六翼护盾：自身轰炸机智能护盾发动时，由六翼护盾接收次效果，将智能护盾效果延长至5回合，（但智能护盾减伤效果变为10%（effect12837BaseVaule）），期间无法再次触发智能护盾，期间自身获得20%【12837】减伤。(->针对敌方兵种留个内置系数effect12837SoldierAdjust)
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 自身轰炸机智能护盾发动时，将智能护盾效果延长至5回合，（但智能护盾减伤效果变为10%（effect12837BaseVaule））
    - 智能护盾为轰炸机兵种技能 battle_soldier_skill表 30201~30205
      - 此前效果为 18%概率抵挡当次75%伤害 →→→ 改为常驻10%减伤
        - 常驻减伤固定值读取const表，effect12837BaseVaule字段
          - 配置格式 万分比
      - 减伤叠加逻辑沿用智能护盾减伤逻辑（注：应该和其他所有作用号乘法叠加）
    - 持续5回合（假的），效果直接变为常驻
[图片]
  - 期间自身获得20%【12837】减伤，期间无法再次触发智能护盾
    - 注：与智能护盾减伤是两个减伤，两者乘法叠加
    - 该作用号为伤害减少效果，作用号自身叠加为作用值累加计算，与其他作用号累乘计算，即 
      - 最终伤害 = 基础伤害 *（1 + 各类加成）*（1 - 各类减免）*（1 - 【12837作用值】* 敌方兵种修正系数/10000）
      - 实际针对敌方各兵种类型，单独配置系数；敌方兵种修正系数 读取const表，字段effect12837SoldierAdjust
        - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
          - 修正系数具体配置为万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12837)
public class Checker12837 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.solider.getEffVal(EffType.HERO_12831) == 0) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
