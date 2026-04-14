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
 * 【11100~11101&11116】突击
- 【万分比】【11100~11101】每回合一次，自身出征数量最多的突击步兵，首次受到的伤害会部分延后3（5）【11100】回生效，延后伤害为30%（effect11100DelayDamage），且自身本回合超能攻击增加10/20/30/40/60%【11101】（延后生效在最终伤害计算后，仅延后此次伤害），满级时数值+队伍现有护盾百分比*20%【11116】->针对敌方兵种留个内置系数effect11096SoldierAdjust
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 注：自身护盾存在时才生效
- 每回合一次，自身出征数量最多的突击步兵
  - 每回合触发一次
  - 回合开始时触发（1阶段）
  - 仅自身出征数量最多的突击步兵触发（兵种类型 = 5）
    - 战斗开始前判定，战斗中不改变
    - 真实出征部队数量，在战斗开始前判定，即参谋军威技能、所罗门、吉迪恩 这种战斗中改变部队数量的机制对此无影响
- 首次受到的伤害会部分延后3（5）【11100】回生效，延后伤害为30%（effect11100DelayDamage）
  - 延后伤害指受到伤害的部分在X回合后才生效，如30%伤害延后，100的伤害，第一回合受伤70，第4回合受伤30
    - 延后伤害回合数读取作用号【11096】
      - 配置格式：绝对值
  - 延后伤害为所有计算后实际应受到的伤害，延后受到伤害时，也为直接伤害不再计算
    - 延后伤害数值读const表，字段effect11100DelayDamage
    - 配置格式：万分比
- 且自身本回合超能攻击增加10/20/30/40/60%【11101】
  - 该作用号为常规外围属性加成效果，与其他作用号累加计算
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +【本作用值】）
      - 配置格式：万分比
- 满级时数值+队伍现有护盾百分比*50%【11116】
  - 技能等级 = 5 时，追加此效果
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +(【本作用值】+队伍现有护盾百分比*【11116】)*兵种修正系数）
      - 固定值读取读取const表，字段【11116】
        - 配置格式：万分比
- ->针对敌方兵种留个内置系数effect11100SoldierAdjust
  - 实际针对敌方各兵种类型，单独配置系数；兵种修正系数 读取const表，字段effect11100SoldierAdjust
    - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
      - 修正系数具体配置为万分比
  - 配置格式：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_11100)
public class Checker11100 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;

		if (parames.unity != parames.getPlayerMaxFreeArmy(parames.unity.getPlayerId(), SoldierType.FOOT_SOLDIER_5)) {
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
