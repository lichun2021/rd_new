package com.hawk.game.battle.effect.impl.homeland;

import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

@BattleTupleType(tuple = { Type.HP, Type.DEF })
@EffectChecker(effType = EffType.EFF_10088)
public class Checker10088 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int cen = parames.unitStatic.getStaffOfficePoint() / 50000;

			effPer = parames.unity.getEffVal(effType()) * cen;
		}
		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
