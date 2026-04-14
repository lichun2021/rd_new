package com.hawk.game.battle.effect.impl.manhattan;

import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleConst;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

/**
 *- 【万分比】【12861】进攻战斗开始时，随机选中敌方某指挥官（优先选中未被选中的指挥官），对其全部部队进行1次波动攻击，造成200%【12861】伤害（本次伤害以生命属性作为攻击属性计算）->针对自身兵种留个内置修正系数（effect12861SoldierAdjust）
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 进攻战斗开始时，随机选中敌方某指挥官（优先选中未被选中的指挥官），对其全部部队进行1次波动攻击
    - 参考作用号【11041】，选择目标和开局生效一次逻辑一致
    
      - 造成造成200%【12861】伤害（本次伤害以生命属性作为攻击属性计算）
    - 注之前计算伤害是按攻击算，这里用生命成自身兵种系数替换攻击
      - 普通：伤害 = 兵团攻击 * 兵团攻击/（兵团攻击 + 敌方兵团防御）*伤害率
      - 本次：伤害 = 兵团生命* 自身兵种修正系数  *  兵团生命* 自身兵种修正系数 /（兵团生命* 自身兵种修正系数  + 敌方兵团防御）*伤害率
    - 即实际伤害 = 基础伤害 *（1 + 各类加成）*（1+【本作用值】 ）
      - 注：若存在荣耀所罗门的幻影部队，此作用号对幻影部队无效！！
  - ->针对敌方兵种留个内置修正系数
    - 实际针对敌方各兵种类型，单独配置系数；敌方兵种修正系数 读取const表，字段effect12861SoldierAdjust
      - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
        - 修正系数具体配置为万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF_12861)
public class Checker12861 implements IChecker {

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!BattleConst.WarEff.ATK.check(parames.troopEffType)) {
			return CheckerKVResult.DefaultVal;
		}
		String pid = parames.unity.getPlayerId();
		BattleUnity max = parames.getPlayeMaxMarchArmy(pid);

		if (parames.unity != max) {
			return CheckerKVResult.DefaultVal;
		}

		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			effPer = parames.unity.getEffVal(effType());
		}

		return new CheckerKVResult(effPer, effNum);
	}


	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
