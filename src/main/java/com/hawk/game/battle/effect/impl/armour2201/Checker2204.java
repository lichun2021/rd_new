package com.hawk.game.battle.effect.impl.armour2201;

import java.util.Map;

import com.hawk.game.battle.BattleSoldier_6;
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
 * 【2204】狙击（兵种类型 = 6）
【万分比】自身数量最多的狙击在命中同一目标 3（effect2204AtkNums） 次后（造成伤害就可以施加），会对该目标施加【透析】状态，下次对该目标攻击时，伤害增加XX.XX%*己方成规模兵种数量；当己方成规模狙击兵数量不超过2 （effect2204NumLimit）个时，【透析】触发所需命中次数减少 1 （effect2204AtkNumsMinus）次
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 自身数量最多狙击在命中同一目标 3 （effect2204AtkNums ）次后（造成伤害就可以施加），会对该目标施加【透析】状态
  - 固定值：绝对值
  - 造成伤害就可以施加层数
  - 层数到3层时立即转换为【透析】debuff
- 下次对该目标攻击时，伤害增加XX.XX%*己方成规模兵种数量；
  - 下一次伤害消耗【透析】，获得伤害增加效果，且此次不叠层。
  - 该作用号为外围伤害加成效果，与其他伤害增加作用号累加计算（与12101/10082等伤害增加作用号加法运算）
    - 即 实际伤害 = 基础伤害*（1 + 各类伤害加成+【作用值】）
      - 配置格式：万分比
- 当己方成规模狙击兵数量不超过2 （effect2204NumLimit）个时
  - 成规模兵种参考新概念介绍
    - 成规模狙击兵≤2 生效
  - 固定值：绝对值
- 【透析】触发所需命中次数减少 1 （effect2204AtkNumsMinus）次
  - 实际所需攻击次数 = effect2204AtkNums - effect2204AtkNumsMinus
  - 固定值：绝对值
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_2204)
public class Checker2204 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.unity.getEffVal(effType()) == 0 || parames.type != SoldierType.FOOT_SOLDIER_6 || parames.unity != parames.unitStatic.getPlayer505Army(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int mass505Army = parames.unitStatic.getMass505ArmyTypeCnt();
		Map<String, BattleUnity> mass505TypeArmy = parames.unitStatic.getMass505TypeArmy(SoldierType.FOOT_SOLDIER_6);
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int cen = ConstProperty.getInstance().effect2204AtkNums;
			if (mass505TypeArmy.size() <= ConstProperty.getInstance().effect2204NumLimit) {
				cen -= ConstProperty.getInstance().effect2204AtkNumsMinus;
			}
			BattleSoldier_6 plan = (BattleSoldier_6) parames.solider;
			plan.eff2204Atkround = cen + 1;
			effPer = parames.unity.getEffVal(effType()) * mass505Army;
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
