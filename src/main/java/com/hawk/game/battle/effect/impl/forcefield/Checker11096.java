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
 * 【11096~11097&11114】轰炸
- 【万分比】【11096~11097】每回合一次，自身出征数量最多的轰炸机对目标造成伤害时，对其最近3（5）【11096】个单位造成+20/40/60/80/120%【11097】伤害。满级时数值+队伍现有护盾百分比*20%【11114】->针对敌方兵种留个内置系数effect11096SoldierAdjust
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 注：自身护盾存在时才生效
- 每回合一次，自身出征数量最多的轰炸机
  - 每回合触发一次
  - 回合开始时触发（1阶段）
  - 仅自身出征数量最多的轰炸机触发（兵种类型 = 3）
    - 战斗开始前判定，战斗中不改变
    - 真实出征部队数量，在战斗开始前判定，即参谋军威技能、所罗门、吉迪恩 这种战斗中改变部队数量的机制对此无影响
- 对目标造成伤害时，对其最近3（5）【11096】个单位
  - 所谓距离最近的 X 个敌方单位（可参考12412）
    - 战斗单位排序在原目标前面且最近的 X/2 个（若原目标前面没有单位，则往后取）
    - 战斗单位排序在原目标后面且最近的 X/2 个（若原目标后面没有单位，则往前取）
      - 注：若敌方其他战斗单位不足，则有多少取多少（但不能选择原目标作为目标）
      - 注：若目标数量为奇数，同样距离下，优先取靠后的敌方单位
  - 选择单位数量读取作用号【11096】
    - 配置格式：绝对值
- 造成+20/40/60/80/120%【11097】伤害
  - 该作用号为常规伤害率
    - 即 实际伤害 =  【本作用值】* 基础伤害 *（1 + 各类加成）
      - 配置格式：万分比
- 满级时数值+队伍现有护盾百分比*50%【11114】
  - 技能等级 = 5 时，追加此效果
    - 即 实际伤害 =  （【本作用值】+【11114】）*兵种修正系数* 基础伤害 *（1 + 各类加成）
      - 固定值读取读取const表，字段【11114】
        - 配置格式：万分比
- ->针对敌方兵种留个内置系数effect11096SoldierAdjust
  - 实际针对敌方各兵种类型，单独配置系数；兵种修正系数 读取const表，字段effect11096SoldierAdjust
    - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
      - 修正系数具体配置为万分比
  - 配置格式：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_11096)
public class Checker11096 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;

		if (parames.unity != parames.getPlayerMaxFreeArmy(parames.unity.getPlayerId(), SoldierType.PLANE_SOLDIER_3)) {
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
