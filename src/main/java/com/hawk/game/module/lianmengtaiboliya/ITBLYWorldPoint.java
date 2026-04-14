package com.hawk.game.module.lianmengtaiboliya;

import java.util.Objects;
import java.util.Set;

import com.hawk.game.module.lianmengtaiboliya.player.ITBLYPlayer;
import com.hawk.game.module.lianmengtaiboliya.worldmarch.ITBLYWorldMarch;
import com.hawk.game.module.lianmengtaiboliya.worldpoint.TBLYPointUtil;
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
public interface ITBLYWorldPoint {

	TBLYBattleRoom getParent();
	int getX();

	int getY();

	int getPointId();

	String getGuildId();
	/**
	 * 转换为协议所需pb格式
	 * 
	 * @param viewerId
	 *            观察者ID
	 * @return
	 */
	WorldPointPB.Builder toBuilder();
	
	default WorldPointPB.Builder toSecondMapBuilder(){
		return toBuilder();
	}
	
	default void onMarchCome(ITBLYWorldMarch march) {

	}
	
	default void onMarchReach(ITBLYWorldMarch leaderMarch) {
	}
	
//	default void fillWithOcuPointId(Set<Integer> set,boolean lite) {
//		WorldPointType pt = this.getPointType();
//		int k = TBLYPointUtil.pointRedis(pt);
//		int xishu = 0;
//		if(lite){
//			xishu = 1;
//		}
//		for (int i = -k + xishu; i <= k; i++) {
//			for (int j = -k; j <= k - xishu; j++) {
//				if (i == k && j == -k) {
//					continue;
//				}
//				int x = this.getX() + i;
//				int y = this.getY() + j;
//				set.add(GameUtil.combineXAndY(x, y));
//			}
//		}
//	}
	
	default void fillWithOcuPointId(Set<Integer> set) {
		WorldPointType pt = this.getPointType();
		int radius = TBLYPointUtil.pointRedis(pt);

		TBLYPointUtil.getOcuPointId(getX(), getY(), radius, set);
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
	WorldPointDetailPB.Builder toDetailBuilder(ITBLYPlayer viewer);

	WorldPointType getPointType();

	boolean onTick();

	boolean needJoinGuild();

	void removeWorldPoint();
	default int getHashThread(int threadNum) {
		return Math.abs(getPointId() % threadNum);
	}

	default boolean isInview(ITBLYPlayer player){
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
