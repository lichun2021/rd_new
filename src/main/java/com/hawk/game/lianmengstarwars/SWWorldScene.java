package com.hawk.game.lianmengstarwars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.hawk.net.protocol.HawkProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawk.game.lianmengstarwars.SWConst.SWState;
import com.hawk.game.lianmengstarwars.player.ISWPlayer;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.World.WorldPointSync;
import com.hawk.game.util.SpatialIndexSystem;

/**
 * 滑动的时候 是客户端主动删除远处的点的
 * @author lwt
 * @date 2020年9月14日
 */
public class SWWorldScene extends SpatialIndexSystem {
	final Logger logger = LoggerFactory.getLogger("Server");

	public SWWorldScene(int mapX, int mapY) {
		super(mapX, mapY);
	}

	public void onPlayerEnterWorld(ISWPlayer player) {
		player.getInviewPoints().clear();
		onPlayerWorldMove(player);
	}

	public void onPlayerWorldMove(ISWPlayer player) {
		List<ISWWorldPoint> viewPoints = inviewPoints(player.getWorldMovePos()[0], player.getWorldMovePos()[1]);
		if (viewPoints.isEmpty()) {
			return;
		}
		WorldPointSync.Builder builder = WorldPointSync.newBuilder();
		for (ISWWorldPoint point : viewPoints) {
			if (player.getInviewPoints().contains(point.getPointId())) {
				continue;
			}
			builder.addPoints(point.toBuilder());
			player.getInviewPoints().add(point.getPointId());
		}
		if (builder.getPointsCount() > 0) {
			player.sendProtocol(HawkProtocol.valueOf(HP.code.WORLD_POINT_SYNC_VALUE, builder));
		}
	}

	public void updateViewPoint(ISWWorldPoint point, boolean delete) {
		if (delete) {
			viewPointDel(point);
		}
		if (!delete) {
			viewPointUpdate(point);
		}

	}

	private void viewPointUpdate(ISWWorldPoint point) {
		SWBattleRoom room = point.getParent();

		WorldPointSync.Builder builder = WorldPointSync.newBuilder();
		builder.setIsRemove(false);
		builder.addPoints(point.toBuilder());

		for (ISWPlayer player : room.getPlayerList(SWState.GAMEING)) {
			if (!player.isActiveOnline()) {
				continue;
			}
			if (!point.isInview(player)) {
				player.getInviewPoints().remove(point.getPointId());
				continue;
			}
			player.getInviewPoints().add(point.getPointId());

			player.sendProtocol(HawkProtocol.valueOf(HP.code.WORLD_POINT_SYNC_VALUE, builder));
		}
	}

	private void viewPointDel(ISWWorldPoint point) {
		SWBattleRoom room = point.getParent();

		WorldPointSync.Builder builder = WorldPointSync.newBuilder();
		builder.setIsRemove(true);
		builder.addPoints(point.toBuilder());

		for (ISWPlayer player : room.getPlayerList(SWState.GAMEING)) {
			if (!player.isActiveOnline()) {
				continue;
			}
			if (!player.getInviewPoints().contains(point.getPointId())) {
				continue;
			}

			player.getInviewPoints().remove(point.getPointId());

			player.sendProtocol(HawkProtocol.valueOf(HP.code.WORLD_POINT_SYNC_VALUE, builder));
		}
	}

	public void addViewPoint(ISWWorldPoint point) {
		super.addObject(point.getX(), point.getY(), point, point.getPointId());
	}

	public boolean removeViewPoint(ISWWorldPoint point) {
		return super.removeObject(point.getPointId());
	}

	public ISWWorldPoint getWorldPoint(int pointId) {
		GameObject obj = super.getObject(pointId);
		if (obj != null) {
			return (ISWWorldPoint) obj.data;
		}
		return null;
	}

	public List<ISWWorldPoint> getAllWorldPoints() {
		List<ISWWorldPoint> result = new ArrayList<>(getObjectCount());
		for (GameObject obj : super.allObjects.values()) {
			result.add((ISWWorldPoint) obj.data);
		}
		return result;
	}

	public List<ISWWorldPoint> inviewPoints(int centerX, int centerY) {
		long start = System.nanoTime();
		List<GameObject> list = super.findNearbyObjects(centerX, centerY, 20);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<ISWWorldPoint> result = new ArrayList<>(list.size());
		for (GameObject obj : list) {
			result.add((ISWWorldPoint) obj.data);
		}
		long end = System.nanoTime();
		if (end - start > 1000000) {
			logger.info("search cost timeout {}", (end - start) * 0.000001);
		}
		return result;
	}

}
