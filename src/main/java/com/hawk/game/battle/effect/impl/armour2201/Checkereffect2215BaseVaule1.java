package com.hawk.game.battle.effect.impl.armour2201;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.protocol.Const.EffType;

/**
 * 【2215】通用
【万分比】自身攻、防、血、增加XX.XX%（effect2215BaseVaule1），自身超能攻击增加XX.XX%（effect2215BaseVaule2），受到伤害降低XX.XX%【2215】
- 于战报中隐藏
- 不合并至精简战报中
- 在战斗开始前判定，满足条件后本次战斗全程生效
- 自身攻、防、血、增加XX.XX%（effect2215BaseVaule1），自身超能攻击增加XX.XX%（effect2215BaseVaule2）
  - 外围属性加法叠加
  - 固定值：万分比
- 受到伤害降低XX.XX%【2215】
  - 常规减伤效果，与其他减伤作用号累乘计算，即 
    - 最终伤害 = 基础伤害 *（1 + 各类加成）*（1 - 各类减免）*（1 - 【该作用值】）
      - 配置格式：万分比
  - 固定值：万分比
 */
@BattleTupleType(tuple = { Type.ATK, Type.DEF, Type.HP })
@EffectChecker(effType = EffType.EFF_1000009)
public class Checkereffect2215BaseVaule1 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {

		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			if (parames.unity.getEffVal(EffType.EFF_2215) > 0) {
				effPer = ConstProperty.getInstance().effect2215BaseVaule1;
			}
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
