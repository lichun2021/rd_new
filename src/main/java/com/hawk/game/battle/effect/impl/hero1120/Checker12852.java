package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
/**https://n0iz2e8vz58.feishu.cn/wiki/K3aYwJnstiXGM4k7Z6Gcd4sjnRi
 * - 【万分比】【12852】集群轰炸 伤害倍率增加 +XX.XX%
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 此作用号绑定光轨锁定作用号【12831】，作用号生效时，造成伤害增加
  - 此伤害加成为额外伤害倍率，与【12831】作用号累加计算
    - 即 伤害倍率 =【12831作用值】+【本作用值】
    - 各固定值沿用作用号【12831】参数
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL2)
@EffectChecker(effType = EffType.HERO_12852)
public class Checker12852 implements IChecker {
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
