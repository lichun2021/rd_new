package com.hawk.game.lianmengstarwars;

import java.util.Objects;
import java.util.Set;

import com.hawk.game.lianmengstarwars.player.ISWPlayer;
import com.hawk.game.lianmengstarwars.worldmarch.ISWWorldMarch;
import com.hawk.game.lianmengstarwars.worldpoint.SWPointUtil;
import com.hawk.game.protocol.World.WorldPointDetailPB;
import com.hawk.game.protocol.World.WorldPointPB;
import com.hawk.game.protocol.World.WorldPointType;
import com.hawk.game.util.AlgorithmUtil;

/**
 * 世界点
 * 
 * @author lwt
 * @date 2018年10月30日
 */
public interface ISWWorldPoint {

	SWBattleRoom getParent();
	int getX();

	int getY();

	int getPointId();

	String getGuildId();
	
	int getAoiObjId();

	void setAoiObjId(int aoiObjId);
	
	int getWorldPointRadius();
	
	default int getAreaId() {
		return getParent().getWorldPointService().getAreaId(getX(), getY());
	}
	
	/**
	 * 转换为协议所需pb格式
	 * 
	 * @param viewerId
	 *            观察者ID
	 * @return
	 */
	WorldPointPB.Builder toBuilder();

	default void onMarchCome(ISWWorldMarch march) {

	}
	
	default void fillWithOcuPointId(Set<Integer> set) {
		SWPointUtil.getOcuPointId(getX(), getY(), getWorldPointRadius(), set);
	}

	default long getProtectedEndTime() {
		return 0L;
	}

	/**
	 * 转换为协议所需pb格式
	 * 
	 * @param viewerId
	 *            观察者ID
	 * @return
	 */
	WorldPointDetailPB.Builder toDetailBuilder(ISWPlayer viewer);

	WorldPointType getPointType();

	boolean onTick();

	boolean needJoinGuild();

	void removeWorldPoint();

	default int getHashThread(int threadNum) {
		return Math.abs(getPointId() % threadNum);
	}
	
	default boolean isInview(ISWPlayer player){
		int[] aoiObj = player.getWorldMovePos();
		if (Objects.isNull(aoiObj)) {
			return false;
		}
		double dis = AlgorithmUtil.lineDistance(aoiObj[0], aoiObj[1], getX(), getY());
		if (dis > 24) {
			return false;
		}
		
		
		return true;
	}
}
