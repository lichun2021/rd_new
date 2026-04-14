package com.hawk.game.module.lianmengXianquhx;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hawk.net.protocol.HawkProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hawk.game.crossproxy.CrossProxy;
import com.hawk.game.module.lianmengXianquhx.XQHXConst.XQHXState;
import com.hawk.game.module.lianmengXianquhx.player.IXQHXPlayer;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.World.WorldPointSync;
import com.hawk.game.util.SpatialIndexSystem;

public class XQHXWorldSence extends SpatialIndexSystem {
	final Logger logger = LoggerFactory.getLogger("Server");

	public XQHXWorldSence(int mapX, int mapY) {
		super(mapX, mapY);
	}

	public void onPlayerEnterWorld(IXQHXPlayer player){
		player.getInviewPoints().clear();
		onPlayerWorldMove(player);
	}
	public void onPlayerWorldMove(IXQHXPlayer player) {
		List<IXQHXWorldPoint> viewPoints = inviewPoints(player.getWorldMovePos()[0], player.getWorldMovePos()[1]);
		if (viewPoints.isEmpty()) {
			return;
		}
		WorldPointSync.Builder builder = WorldPointSync.newBuilder();
		for (IXQHXWorldPoint point : viewPoints) {
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
	
	public void updateViewPoint(IXQHXWorldPoint point , boolean delete){
		XQHXBattleRoom room = point.getParent();

		WorldPointSync.Builder builder = WorldPointSync.newBuilder();
		builder.setIsRemove(delete);
		builder.addPoints(point.toBuilder());

		Set<String> csplayerIds = new HashSet<>();
		for (IXQHXPlayer player : room.getPlayerList(XQHXState.GAMEING)) {
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

		for (IXQHXPlayer anchor : room.getAnchors()) {
			anchor.sendProtocol(HawkProtocol.valueOf(HP.code.WORLD_POINT_SYNC_VALUE, builder));
		}
	}

	public void addViewPoint(IXQHXWorldPoint point) {
		super.addObject(point.getX(), point.getY(), point, point.getPointId());
	}

	public boolean removeViewPoint(IXQHXWorldPoint point) {
		return super.removeObject(point.getPointId());
	}

	public IXQHXWorldPoint getWorldPoint(int pointId) {
		GameObject obj = super.getObject(pointId);
		if (obj != null) {
			return (IXQHXWorldPoint) obj.data;
		}
		return null;
	}

	public List<IXQHXWorldPoint> getAllWorldPoints() {
		List<IXQHXWorldPoint> result = new ArrayList<>(getObjectCount());
		for (GameObject obj : super.allObjects.values()) {
			result.add((IXQHXWorldPoint) obj.data);
		}
		return result;
	}

	public List<IXQHXWorldPoint> inviewPoints(int centerX, int centerY) {
		long start = System.nanoTime();
		List<GameObject> list = super.findNearbyObjects(centerX, centerY, 20);
		if (list.isEmpty()) {
			return Collections.emptyList();
		}
		List<IXQHXWorldPoint> result = new ArrayList<>(list.size());
		for (GameObject obj : list) {
			result.add((IXQHXWorldPoint) obj.data);
		}
		long end = System.nanoTime();
		if (end - start > 1000000) {
			logger.info("search cost timeout {}", end - start);
		}
		return result;
	}
}
