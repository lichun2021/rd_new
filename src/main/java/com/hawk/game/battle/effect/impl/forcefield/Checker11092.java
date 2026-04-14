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
 * 【11092~11093&11112】防坦
- 【万分比】【11092~11093】每回合一次，自身出征数量最多的防御坦克，给予己方前排单位受到的前3（5）【11092】次伤害时防御、生命+50/100/150/200/300%【11093】。满级时数值+队伍现有护盾百分比*50%【11112】->针对己方兵种留个内置系数effect11092SoldierAdjust
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 注：自身护盾存在时才生效
- 每回合一次，自身出征数量最多的防御坦克
  - 每回合触发一次
  - 回合开始时触发（1阶段）
  - 仅自身出征数量最多的防御坦克触发（兵种类型 = 1）
    - 战斗开始前判定，战斗中不改变
    - 真实出征部队数量，在战斗开始前判定，即参谋军威技能、所罗门、吉迪恩 这种战斗中改变部队数量的机制对此无影响
- 给予己方前排单位受到的前3（5）【11092】次伤害时
  - 伤害次数读取作用号【11092】
    - 配置格式：绝对值
- 防御、生命+50/100/150/200/300%【11093】
  - 该作用号为常规外围属性加成效果，与其他作用号累加计算
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +【本作用值】）
      - 配置格式：万分比
- 满级时数值+队伍现有护盾百分比*50%【11112】
  - 技能等级 = 5 时，追加此效果
    - 即 实际属性 = 基础属性*（1 + 各类攻击加成 +(【本作用值】+队伍现有护盾百分比*【11112】)*兵种修正系数）
      - 固定值读取读取const表，字段11112
        - 配置格式：万分比
- ->针对己方兵种留个内置系数effect11092SoldierAdjust
  - 实际针对敌方各兵种类型，单独配置系数；兵种修正系数 读取const表，字段effect11092SoldierAdjust
    - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
      - 修正系数具体配置为万分比
  - 配置格式：万分比
 * @author lwt
 * @date 2025年9月22日
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_11092)
public class Checker11092 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;

		if (parames.unity != parames.getPlayerMaxFreeArmy(parames.unity.getPlayerId(), SoldierType.TANK_SOLDIER_1)) {
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
