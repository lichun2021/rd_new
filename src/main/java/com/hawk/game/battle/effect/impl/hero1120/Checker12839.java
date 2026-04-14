package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleConst;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**- 【万分比】【12838~12839】个人战时，战斗时间越久，蜂群搭载智能体对该区域掌控越稳定，每经过x（effect12838AtkRound）回合，战蜂形成火力稀释层，使自身轰炸机受到的伤害降低XX.XX%【12838】(->针对敌方兵种留个内置系数effect12838SoldierAdjust)，同时目标单位的防护结构被解析，自身轰炸机造成的伤害提高XX.XX%【12839】(->针对敌方兵种留个内置系数effect12839SoldierAdjust)（可叠加，最高x（effect12838Maxinum）层）
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 每经过x回合（effect12838AtkRound）
    - 固定值读取const表，effect12838AtkRound字段
      - 配置格式 绝对值
  - 使自身轰炸机受到的伤害降低XX.XX%【12838】
    - 该作用号为伤害减少效果，作用号自身叠加为作用值累加计算，与其他作用号累乘计算，即 
      - 最终伤害 = 基础伤害 *（1 + 各类加成）*（1 - 各类减免）*（1 - 层数*【12838作用值】* 敌方兵种修正系数/10000）
      - 实际针对敌方各兵种类型，单独配置系数；敌方兵种修正系数 读取const表，字段effect12838SoldierAdjust
        - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
          - 修正系数具体配置为万分比
  - 自身轰炸机造成的伤害提高XX.XX%【12839】
    - 该作用号为外围伤害加成效果，与其他伤害增加作用号累加计算（与12101/10082等伤害增加作用号加法运算）
      - 即 实际伤害 = 基础伤害*（1 + 各类伤害加成+层数*【12839作用值】* 敌方兵种修正系数/10000）
        - 配置格式：万分比
    - 实际针对敌方各兵种类型，单独配置系数；敌方兵种修正系数 读取const表，字段effect12839SoldierAdjust
      - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
        - 修正系数具体配置为万分比
  - （可叠加，最高x（effect12838Maxinum）层）
    - 层数最大值
    - 固定值读取const表，effect12838Maxinum字段
      - 配置格式 绝对值
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12839)
public class Checker12839 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.SELF_FIGHT.check(parames.troopEffType)) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
