package com.hawk.game.module.lianmengtaiboliya;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hawk.net.protocol.HawkProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawk.game.crossproxy.CrossProxy;
import com.hawk.game.module.lianmengtaiboliya.TBLYConst.TBLYState;
import com.hawk.game.module.lianmengtaiboliya.player.ITBLYPlayer;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.World.WorldPointSync;
import com.hawk.game.util.SpatialIndexSystem;

public class TBLYWorldSence extends SpatialIndexSystem {
	final Logger logger = LoggerFactory.getLogger("Server");

	public TBLYWorldSence(int mapX, int mapY) {
		super(mapX, mapY);
	}

	public void onPlayerEnterWorld(ITBLYPlayer player){
		player.getInviewPoints().clear();
		onPlayerWorldMove(player);
	}
	public void onPlayerWorldMove(ITBLYPlayer player) {
		List<ITBLYWorldPoint> viewPoints = inviewPoints(player.getWorldMovePos()[0], player.getWorldMovePos()[1]);
		if (viewPoints.isEmpty()) {
			return;
		}
		WorldPointSync.Builder builder = WorldPointSync.newBuilder();
		for (ITBLYWorldPoint point : viewPoints) {
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
	
	public void updateViewPoint(ITBLYWorldPoint point , boolean delete){
		TBLYBattleRoom room = point.getParent();

		WorldPointSync.Builder builder = WorldPointSync.newBuilder();
		builder.setIsRemove(delete);
		builder.addPoints(point.toBuilder());

		Set<String> csplayerIds = new HashSet<>();
		for (ITBLYPlayer player : room.getPlayerList(TBLYState.GAMEING)) {
			if (delete && !player.getInviewPoints().contains(point.getPointId())) {
				continue;
			}
			if (!delete && !point.isInview(player)) {
				player.getInviewPoints().remove(point.getPointId());
				continue;
			}
			if (!delete) {
				player.getInviewPoints().add(point.getPointId());
			} else {
				player.getInviewPoints().remove(point.getPointId());
			}

			if (player.isCsPlayer()) {
				csplayerIds.add(player.getId());
			} else {
				player.sendProtocol(HawkProtocol.valueOf(HP.code.WORLD_POINT_SYNC_VALUE, builder));
			}
		}

		if (!csplayerIds.isEmpty()) {
			CrossProxy.getInstance().broadcastProtocolV2(room.getCsServerId(), csplayerIds, HawkProtocol.valueOf(HP.code.WORLD_POINT_SYNC_VALUE, builder));
		}

		for (ITBLYPlayer anchor : room.getAnchors()) {
			anchor.sendProtocol(HawkProtocol.valueOf(HP.code.WORLD_POINT_SYNC_VALUE, builder));
		}
	}

	public void addViewPoint(ITBLYWorldPoint point) {
		super.addObject(point.getX(), point.getY(), point, point.getPointId());
	}

	public boolean removeViewPoint(ITBLYWorldPoint point) {
		return super.removeObject(point.getPointId());
	}

	public ITBLYWorldPoint getWorldPoint(int pointId) {
		GameObject obj = super.getObject(pointId);
		if (obj != null) {
			return (ITBLYWorldPoint) obj.data;
		}
		return null;
	}

	public List<ITBLYWorldPoint> getAllWorldPoints() {
		List<ITBLYWorldPoint> result = new ArrayList<>(getObjectCount());
		for (GameObject obj : super.allObjects.values()) {
			result.add((ITBLYWorldPoint) obj.data);
		}
		return result;
	}

	public List<ITBLYWorldPoint> inviewPoints(int centerX, int centerY) {
		long start = System.nanoTime();
		List<GameObject> list = super.findNearbyObjects(centerX, centerY, 20);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<ITBLYWorldPoint> result = new ArrayList<>(list.size());
		for (GameObject obj : list) {
			result.add((ITBLYWorldPoint) obj.data);
		}
		long end = System.nanoTime();
		if (end - start > 1000000) {
			logger.info("search cost timeout {}", end - start);
		}
		return result;
	}
}
