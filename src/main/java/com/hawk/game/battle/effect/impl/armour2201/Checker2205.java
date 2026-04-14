package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleSoldier_4;
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
 * 【2205】直升（兵种类型 = 4）
【万分比】自身直升机成规模时，战斗中每5（effect2205AtkRound）回合开始时，会对随机 2（effect2205AtkNums）个远程部队施加【锁定】（持续2 （effect2205ContinueRound）回合，优先选取本场战斗被选取次数较少目标），【锁定】状态下部队受到攻击时，攻击方伤害增加XX.XX%【2205】*己方成规模兵种数量；当己方成规模直升机数量不超过2（effect2205NumLimit）个时，目标增加1个（effect2205AtkNumsPlus）
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 自身直升机成规模时
  - 成规模兵种参考新概念介绍，辅助需要自身兵种足够数量才能生效辅助技能
- 战斗中每5（effect2205AtkRound）回合开始时
  - 固定值：绝对值
- 会对随机 2（effect2205AtkNums）个远程目标施加【锁定】（持续2 （effect2205ContinueRound）回合，
  - 固定值：绝对值
  - 远程部队类型包含有：直升机（兵种类型 = 4）、突击步兵（兵种类型 = 5）、狙击兵（兵种类型 = 6）、攻城车（兵种类型 = 7）
- 优先选取本场战斗被选取次数较少目标）
  - 优先选择整场战斗中，被该技能选取次数较低的部队
  - 如有ABCD，选2个
    - 本次随机选到选AB，下次就会选CD，再下次则又重新在ABCD中随机2个
  - 如有ABCD，选3个
    - 本次随机选到选ABC，下次就会选D+ABC中选取2个
- 【锁定】状态下部队受到攻击时，攻击方伤害增加XX.XX%*己方成规模兵种数量；
  - 该作用号为外围伤害加成效果，与其他伤害增加作用号累加计算（与12101/10082等伤害增加作用号加法运算）
    - 即 实际伤害 = 基础伤害*（1 + 各类伤害加成+【作用值】）
      - 配置格式：万分比
- 当己方成规模直升机数量不超过2（effect2205NumLimit）个时
  - 成规模兵种参考新概念介绍
    - 己方成规模直升机≤2 生效
  - 固定值：绝对值
- 目标增加1个（effect2205AtkNumsPlus）
  - 实际目标数量 = effect2205AtkNums + effect2205AtkNumsPlus
  - 固定值：绝对值
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2205)
public class Checker2205 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type!= SoldierType.PLANE_SOLDIER_4 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}
		
		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.PLANE_SOLDIER_4);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int effect2205AtkNums = ConstProperty.getInstance().effect2205AtkNums;
			if(mass505TypeArmy.size() <= ConstProperty.getInstance().effect2205NumLimit){
				effect2205AtkNums += ConstProperty.getInstance().effect2205AtkNumsPlus;
			}
			BattleSoldier_4 plan = (BattleSoldier_4) parames.solider;
			plan.effect2205AtkNums = effect2205AtkNums;
			effPer = parames.unity.getEffVal(effType()) * mass505Army;
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
