package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
/**https://n0iz2e8vz58.feishu.cn/wiki/K3aYwJnstiXGM4k7Z6Gcd4sjnRi
 * - 【万分比】【12851】忠诚僚机中【振翅】期间超能攻击+XX.XX%
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本场战斗全程生效
  - 此作用号绑定忠诚僚机作用号中满充能【振翅】形态【12836】
    - 作用号生效时，攻击和超能攻击与原作用号累加
      - 即 实际超能攻击属性 = 基础属性*（1 + 各类加成 +（【12836作用值】+【本作用值】）* 敌方兵种修正系数/10000）
      - 各固定值沿用作用号【12836】参数
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12851)
public class Checker12851 implements IChecker {
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
