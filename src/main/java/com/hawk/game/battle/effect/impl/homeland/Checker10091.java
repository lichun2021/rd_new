package com.hawk.game.battle.effect.impl.homeland;

import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.protocol.Const.EffType;

@BattleTupleType(tuple = { Type.FORCE_FIELD })
@EffectChecker(effType = EffType.EFF_10091)
public class Checker10091 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		String pid = parames.unity.getPlayerId();
		BattleUnity max = parames.getPlayeMaxMarchArmy(pid);

		if (parames.unity != max) {
			return CheckerKVResult.DefaultVal;
		}

		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type)) {
			int cen = parames.unitStatic.getStaffOfficePoint() / 10000;
			effPer = parames.unity.getEffVal(effType()) * cen;
			parames.solider.setForceFieldMarch(parames.unitStatic.getPlayerArmyCountMapMarch().get(pid));
		}

		return new CheckerKVResult(effPer, effNum);
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
