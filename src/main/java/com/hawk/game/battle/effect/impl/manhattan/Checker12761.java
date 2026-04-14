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
 * 【12761】
- 【万分比】【12761】战斗效果：进攻战斗时，战斗开始随机选选中敌方某指挥官进行5（effect12761AtkTimes）次粒子光束打击（伤害率：XX%）【12761】（优先选择未被选中指挥官），每个受击单位都会将伤害传递至其最近的2（effect12761AtkNums）个单位->针对敌方兵种留个内置修正系数（effect12761SoldierAdjust）
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 此效果对战斗开始时（算作0回合？）造成的伤害也生效（数值算第1回合的）；如【11041】
[图片]
  - 进攻战斗时，战斗开始随机选选中敌方某指挥官进行5（effect12761AtkTimes）次粒子光束打击（优先选择未被选中指挥官）
    - 选中的指挥官下所有单位都会受击
    - 指定数值读取const表，字段effect12761AtkTimes
      - 配置格式：绝对值
    - 随机逻辑同作用号【11041】，仅选取单位逻辑不同
      - 若存在多个部队数量一样且最高，取等级高的；若存在多个部队数量一样且最高且等级最高，取兵种类型小的部队（最后有且仅有1个战斗单位有此效果）
  - （伤害率：XX%）【12761】
    - 即实际伤害 = 基础伤害 *（1 + 各类加成）*（1+【本作用值】 * 敌方兵种修正系数 ）
      - 注：若存在荣耀所罗门的幻影部队，此作用号对幻影部队无效！！
  - 并将伤害传递至所有受击部队最近的2（effect12761AtkNums）个单位
    - 每个受击部队都会进行伤害传递，如果一个玩家上多个单位，那就会多溅射
    - 传递伤害为初始伤害，还是按上述伤害率并单独计算受击目标的防御
      - 即实际伤害 = 基础伤害 *（1 + 各类加成）*（1+【本作用值】 * 敌方兵种修正系数 ）
        - 注：若存在荣耀所罗门的幻影部队，此作用号对幻影部队无效！！
    - 回合数值读取const表，字段effect12761AtkNums
      - 配置格式：万分比
    - 作用号效果不叠加
  - ->针对敌方兵种留个内置修正系数
    - 实际针对敌方各兵种类型，单独配置系数；敌方兵种修正系数 读取const表，字段effect12761SoldierAdjust
      - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
        - 修正系数具体配置为万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.EFF12761)
public class Checker12761 implements IChecker {

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
