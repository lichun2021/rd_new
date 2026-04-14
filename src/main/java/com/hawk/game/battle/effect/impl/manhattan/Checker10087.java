package com.hawk.game.battle.effect.impl.manhattan;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;

/**
 * 10087=每5万点全队参谋值，为自身提供{0}%攻击力加成
参数{0}，所有兵种攻击力百分比加成。配置万分数，显示为百分比。
实际逻辑：
战斗开始时，为自己的部队增加攻击力。
增加的攻击力加成，与我方所有玩家上阵的所有参谋提供的总参谋值有关。
攻击力加成 = 全队总参谋值 /50000 *{0}
 */
@BattleTupleType(tuple = Type.ATK)
@EffectChecker(effType = EffType.EFF_10087)
public class Checker10087 implements IChecker {

	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			effPer = parames.unity.getEffVal(effType()) * (parames.unitStatic.getStaffOfficePoint() / 50000);
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
