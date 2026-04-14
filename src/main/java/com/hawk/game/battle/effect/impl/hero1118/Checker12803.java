package com.hawk.game.battle.effect.impl.hero1118;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12803)
public class Checker12803 implements IChecker {
	/**
	 * 12802】
- 【万分比】【12802】流纹释放 无视敌方攻击增加+XX.XX%
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本场战斗全程生效
  - 此作用号绑定流纹释放4层效果无视敌方攻击作用号【12786】
  - 同为无视攻击增加，与【12786】作用号累加计算
    - 即 敌方攻击= 原本攻击 -（【12786作用值】+【本作用值】）* 敌方兵种修正系数/10000 
    - 各固定值沿用作用号【12786】参数

【12803】
- 【万分比】【12803】战技持续期间，荣耀流纹己方对敌方各兵种伤害减免固定值+XX.XX%
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本场战斗全程生效
  - 此作用号绑定荣耀流纹作用号【12784】
  - 此伤害减少效果，与【12784】作用号累加计算
    - 即 最终伤害 = 基础伤害 *（1 + 各类加成）*（1 - 各类减免）*（1 - （【12784作用值】+【12803作用值】））
	 */
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.solider.getEffVal(EffType.HERO_12791) == 0) {
			return CheckerKVResult.DefaultVal;
		}
		int effPer = 0;
		int effNum = 0;
		effPer = parames.unity.getEffVal(effType()); 
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
