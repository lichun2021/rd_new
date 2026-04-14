package com.hawk.game.module.lianmengyqzz.battleroom;

import java.util.concurrent.TimeUnit;

public class YQZZConst {
	/**1-外圈区域1（左上），2-内圈，3-外圈区域2（左下），4-外圈区域3（右）
	需要你处理一下，1 3 4之间不能相互迁城
	 占领了关隘才能迁入2*/	
	public final static int WAI1 = 1;
	public final static int NEI2 = 2;
	public final static int WAI3 = 3;
	public final static int WAI4 = 4;
	public final static int[] WAIArr = new int[]{1,3,4};
	/** 一分钟毫秒数 */
	public static final long MINUTE_MICROS = TimeUnit.MINUTES.toMillis(1);
	public static final String YURI_GUILD = "YURI";
	
	public static class ModuleType {
		public static final int YQZZWorld = 3330;
		public static final int YQZZMarch = 3331;
		public static final int YQZZArmy = 3332;
		public static final int YQZZIdle = 3333;
		public static final int YQZZGuildFormation = 3334;
	}

	// public enum YQZZOverType {
	// /**连续控制1/4时间*/
	// CTCROL,
	// /**累计控制1/2*/
	// LJCROL,
	// /**时间结束*/
	// TIMEOVER;
	// }

	public enum YQZZState {
		/** 联盟军演战场中 */
		GAMEING(11);

		YQZZState(int value) {
			this.value = value;
		}

		private int value;

		public int intValue() {
			return value;
		}
	}
}
