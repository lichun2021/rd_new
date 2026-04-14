package com.hawk.game.battle.effect.impl.forcefield;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 【11104~11105&11118】攻城
- 【万分比】【11094~11095】每回合一次，自身出征数量最多的攻城车，给予己方后排单位受到的前3（5）【11104】次伤害时防御、生命+50/100/150/200/300%【11105】。满级时数值+队伍现有护盾百分比*50%【11118】->针对己方兵种留个内置系数effect11104SoldierAdjust
- 战报相关
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 注：自身护盾存在时才生效
- 每回合一次，自身出征数量最多的攻城车
  - 每回合触发一次
  - 回合开始时触发（1阶段）
  - 仅自身出征数量最多的攻城车触发（兵种类型 = 7）
    - 战斗开始前判定，战斗中不改变
    - 真实出征部队数量，在战斗开始前判定，即参谋军威技能、所罗门、吉迪恩 这种战斗中改变部队数量的机制对此无影响
- 给予己方后排单位受到的前3（5）【11104】次伤害时
  - 伤害次数读取作用号【11104】
    - 配置格式：绝对值
- 防御、生命+50/100/150/200/300%【11105】
  - 该作用号为常规外围属性加成效果，与其他作用号累加计算
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +【本作用值】）
      - 配置格式：万分比
- 满级时数值+队伍现有护盾百分比*50%【11118】
  - 技能等级 = 5 时，追加此效果
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +(【本作用值】+队伍现有护盾百分比*【11118】)*兵种修正系数）
      - 配置格式：万分比
- ->针对己方兵种留个内置系数effect11104SoldierAdjust
  - 实际针对敌方各兵种类型，单独配置系数；兵种修正系数 读取const表，字段effect11104SoldierAdjust
    - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
      - 修正系数具体配置为万分比
  - 配置格式：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_11104)
public class Checker11104 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;

		if (parames.unity != parames.getPlayerMaxFreeArmy(parames.unity.getPlayerId(), SoldierType.CANNON_SOLDIER_7)) {
			return CheckerKVResult.DefaultVal;
		}
		effPer = parames.unity.getEffVal(effType());
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
