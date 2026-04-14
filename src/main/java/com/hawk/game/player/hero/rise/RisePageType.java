package com.hawk.game.player.hero.rise;

import com.hawk.game.protocol.Const.SoldierType;

public enum RisePageType {
	None(0), One(1), Two(2), Three(3), Four(4);

	RisePageType(int val) {
		this.intVal = val;
	}

	private final int intVal;

	public static SoldierType[] getSolderType(RisePageType ptype) {
		SoldierType[] result = null;
		switch (ptype) {
		case One:
			result = new SoldierType[] { SoldierType.TANK_SOLDIER_1, SoldierType.TANK_SOLDIER_2 };
			break;
		case Two:
			result = new SoldierType[] { SoldierType.PLANE_SOLDIER_3, SoldierType.PLANE_SOLDIER_4 };
			break;
		case Three:
			result = new SoldierType[] { SoldierType.FOOT_SOLDIER_5, SoldierType.FOOT_SOLDIER_6 };
			break;
		case Four:
			result = new SoldierType[] { SoldierType.CANNON_SOLDIER_7, SoldierType.CANNON_SOLDIER_8 };
			break;
		default:
			break;
		}
		return result;
	}

	public static RisePageType getSoldierPageType(SoldierType type) {
		RisePageType pageType = None;
		switch (type) {
		case TANK_SOLDIER_1:
		case TANK_SOLDIER_2:
			pageType = RisePageType.One;
			break;

		case PLANE_SOLDIER_3:
		case PLANE_SOLDIER_4:
			pageType = RisePageType.Two;
			break;

		case FOOT_SOLDIER_5:
		case FOOT_SOLDIER_6:
			pageType = RisePageType.Three;
			break;

		case CANNON_SOLDIER_7:
		case CANNON_SOLDIER_8:
			pageType = RisePageType.Four;
			break;

		default:
			break;
		}
		return pageType;
	}

	public static RisePageType valueOf(int index) {
		switch (index) {
		case 0:
			return None;
		case 1:
			return One;
		case 2:
			return Two;
		case 3:
			return Three;
		case 4:
			return Four;
		default:
			break;
		}
		return null;
	}

	public int getIntVal() {
		return intVal;
	}
}
