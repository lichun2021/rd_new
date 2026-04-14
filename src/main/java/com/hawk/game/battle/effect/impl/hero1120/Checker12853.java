package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
/**https://n0iz2e8vz58.feishu.cn/wiki/K3aYwJnstiXGM4k7Z6Gcd4sjnRi
 * - 【万分比】【12853】战技持续期间，六翼护盾期间获得的伤害减少+XX.XX%
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本场战斗全程生效
  - 此作用号绑定六翼护盾作用号【12837】，作用号生效时，伤害降低值与原作用号累加计算（不是智能护盾减伤）
    - 六翼护盾减伤
      - 最终伤害 = 基础伤害 *（1 + 各类加成）*（1 - 各类减免）*（1 - (【12837作用值】+【本作用值】）* 敌方兵种修正系数/10000）
      - 各固定值沿用作用号【12837】参数
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12853)
public class Checker12853 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (parames.solider.getEffVal(EffType.HERO_12831) == 0) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
