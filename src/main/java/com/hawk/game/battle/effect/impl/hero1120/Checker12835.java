package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
/**https://n0iz2e8vz58.feishu.cn/wiki/K3aYwJnstiXGM4k7Z6Gcd4sjnRi
 * 【12835~12836】
- 【万分比】【12835~12836】忠诚僚机：维萝莎拥有100（effect12835BaseVaule）架战蜂无人机群，每轮集群轰炸都会使 1 （effect12835AtkThresholdValue1）架战蜂获得充能，每回合开始也会使2（effect12836AtkThresholdValue1）架战蜂获得充能。
  - 自身未充能蜂群，每个使自身轰炸机受到攻击时，受到伤害减少XX.XX%【12835】。(->针对敌方兵种留个内置系数effect12835SoldierAdjust)
  - 当所有战蜂都充能完毕时，自身轰炸机在下个回合开始时获得【振翅】效果，集群轰炸发动轮次额外+18（effect12836BaseVaule），自身轰炸机超能攻击增加XX.XX%【12836】，持续5（effect12836ContinueRound）回合，持续结束后战蜂充能重置(->针对敌方兵种留个内置系数effect12836SoldierAdjust)
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 维萝莎拥有100（effect12835BaseVaule）架战蜂无人机群，
    - 拥有100架无人机等于充能上限是100
    - 固定值读取const表，effect12835BaseVaule字段
      - 配置格式 绝对值
  - 每轮集群轰炸都会使 1 （effect12835AtkThresholdValue1）架战蜂获得充能，每回合开始也会使2（effect12836AtkThresholdValue1）架战蜂获得充能。
    - 集群轰炸为自身作用号【12831~12832】
    - 集结和单打，轰炸轮次不同，读取作用号【12831~12832】常量
      - 集结：effect12832AtkTimesForMass
      - 单打：effect12832AtkTimesForPerson
[图片]
  - 自身未充能蜂群，每个使自身轰炸机受到攻击时，受到伤害减少XX.XX%【12835】。(->针对敌方兵种留个内置系数effect12835SoldierAdjust)
    - 该作用号为伤害减少效果，作用号自身叠加为作用值累加计算，与其他作用号累乘计算，即 
      - 最终伤害 = 基础伤害 *（1 + 各类加成）*（1 - 各类减免）*（1 - 未充能数*【12835作用值】* 敌方兵种修正系数/10000）
      - 实际针对敌方各兵种类型，单独配置系数；敌方兵种修正系数 读取const表，字段effect12835SoldierAdjust
        - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
          - 修正系数具体配置为万分比
  - 当所有战蜂都充能完毕时，自身轰炸机在下个回合开始时获得【振翅】效果，集群轰炸发动轮次额外+18（effect12836BaseVaule），自身轰炸机超能攻击增加XX.XX%【12836】，持续5（effect12836ContinueRound）回合，持续结束后战蜂充能重置。(->针对敌方兵种留个内置系数effect12836SoldierAdjust)
    - 满足条件 充能≥100后，下个回合触发
    - 集群轰炸为自身作用号【12831~12832】
    - 自身超能攻击增加为常规外围属性加成效果，与其他作用号累加计算
      - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +【12836作用值】* 敌方兵种修正系数/10000）
        - 配置格式：万分比
    - 实际针对敌方各兵种类型，单独配置系数；敌方兵种修正系数 读取const表，字段effect12836SoldierAdjust
      - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
        - 修正系数具体配置为万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12835)
public class Checker12835 implements IChecker {
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
