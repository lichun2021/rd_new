package com.hawk.game.battle.effect.impl.hero1118;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12791)
public class Checker12791 implements IChecker {
	/**
	 * 【12791】
  - 【万分比】【12791】战技持续期间，自身出征数量最多的防御坦克受到攻击时，伤害减少 +XX.XX%【12791】
    - 战报相关
      - 于战报中展示
      - 不合并至精简战报中
    - 在战斗开始前判定，满足条件后本次战斗全程生效
    - 自身出征数量最多的狙击兵
      - 真实出征部队数量，在战斗开始前判定，即参谋军威技能、所罗门、吉迪恩 这种战斗中改变部队数量的机制对此无影响
      - 满足条件后，该作用号效果仅对玩家出征时数量最多的防御坦克（兵种类型 = 1）生效（若存在多个部队数量一样且最高，取等级高的）
    - 该作用号为伤害减少效果，与其他作用号累乘计算，即 
      - 实际伤害 = 基础伤害*（1 - 其他作用号）*（1 - 【本作用值】）
    - 此为英雄战技专属作用号，配置格式如下
      - 作用号id_参数1_参数2
        - 参数1：作用值系数
          - 配置格式：浮点数
          - 即 本作用值 = 英雄军事值 * 参数1/10000
        - 参数2：战技持续时间
          - 配置格式：绝对值（单位：秒）

- 军魂技能
  - 鹰隼虚影 己方伤害增加 +XX.XX%
  - 流纹释放 无视目标攻击比例增加+XX.XX%
  - 战技持续期间，荣耀流纹己方对敌方各兵种伤害减免固定值+2.00%，
  - 西格玛专属战技持续时间 +XX 秒
	 */
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.solider.getEffVal(EffType.HERO_12781) == 0) {
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
