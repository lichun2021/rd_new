package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【2201】主战
【万分比】每回合开始，随机选择敌方2（effect2201AtkNums）个单位（优先选取空军单位），本回合对目标施加 2（effect2201BaseVaule）*己方成规模兵种类型数量 层【压迫】状态，
自身数量最多主战坦克攻击目标时，伤害增加XX.XX%【2201】*目标【压迫】层数（【压迫】层数仅对施加着生效）；当己方成规模主战部队不超过 2 （effect2201NumLimit） 个时，层数增加1（effect2201BaseVaulePlus）*己方成规模兵种类型数量
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 每回合开始，随机选择敌方2（effect2201AtkNums）个单位（优先选取空军单位）
  - 固定值：绝对值
  - 空军单位包含：轰炸机（兵种类型 =3）直升机（兵种类型 =4）
    - 无空军会随机选择其他兵种
- 本回合对目标施加 2（effect2201BaseVaule）*己方成规模兵种类型数量 层【压迫】状态
  - 固定值：绝对值
  - debuff只作用于本回合
- 自身数量最多主战坦克攻击目标时，伤害增加XX.XX%【2201】*目标【压迫】层数（【压迫】层数仅对施加着生效）
  - 该作用号为外围伤害加成效果，与其他伤害增加作用号累加计算（与12101/10082等伤害增加作用号加法运算）
    - 即 实际伤害 = 基础伤害*（1 + 各类伤害加成+【作用值】）
      - 配置格式：万分比
  - 【压迫】层数仅对施加着生效
    - A主站施加【压迫A】5层，B主站施加【压迫B】3层，A攻击时只按自身的5层生效
- 当己方成规模主战部队不超过 2 （effect2201NumLimit） 个时
  - 成规模兵种参考新概念介绍
    - 成规模主战≤ 2  生效
- 层数增加1（effect2201BaseVaulePlus）*己方成规模兵种类型数量
  - 实际每次叠层数 = （effect2201BaseVaule+effect2201BaseVaulePlus）*己方成规模兵种类型数量
  - 固定值：绝对值
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2201)
public class Checker2201 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type!= SoldierType.TANK_SOLDIER_2 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}
		
		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.TANK_SOLDIER_2);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int cen = ConstProperty.getInstance().effect2201BaseVaule;
			if(mass505TypeArmy.size() <= ConstProperty.getInstance().effect2201NumLimit){
				cen += ConstProperty.getInstance().effect2201BaseVaulePlus;
			}
			effPer = parames.unity.getEffVal(effType())* cen * mass505Army;
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
