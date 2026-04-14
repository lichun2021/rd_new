package com.hawk.game.lianmengstarwars;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hawk.os.HawkException;
import org.hawk.os.HawkTime;
import org.hawk.thread.HawkTask;

import com.hawk.game.lianmengstarwars.worldmarch.ISWWorldMarch;
import com.hawk.game.lianmengstarwars.worldmarch.SWNotifyMarchEventFunc;
import com.hawk.game.lianmengstarwars.worldpoint.ISWBuilding;
import com.hawk.game.lianmengstarwars.worldpoint.SWBuildState;
import com.hawk.game.log.DungeonRedisLog;
import com.hawk.game.protocol.World.WorldMarchType;

public class SWTickAbleTask extends HawkTask {
	private String battleId;
	private int threadNum;
	private Deque<ISWWorldPoint> viewPoints = new LinkedList<>();

	private Deque<ISWWorldMarch> marchs = new LinkedList<>();
	private ConcurrentSkipListMap<String, SWNotifyMarchEventFunc> noticyMarchEnentMap = new ConcurrentSkipListMap<>();
	/** 准备运行 */
	private AtomicBoolean readyToRace = new AtomicBoolean(false);

	@Override
	public Object run() {
		long beginTimeMs = HawkTime.getMillisecond();
		try {

			while (!marchs.isEmpty()) {
				ISWWorldMarch march = marchs.pop();
				marchTick(march);
			}
			
			while (!viewPoints.isEmpty()) {
				ISWWorldPoint point = viewPoints.pop();
				if (point instanceof ISWBuilding) {
					buildTick(point);
				} else {
					pointTick(point);
				}
			}
			while (!noticyMarchEnentMap.isEmpty()) {
				// 移除当前时间节点集合
				SWNotifyMarchEventFunc func = noticyMarchEnentMap.pollFirstEntry().getValue();
				func.apply(null);
			}
	
		} catch (Exception e) {
			HawkException.catchException(e);
		} finally {
			readyToRace.set(false);
			// 时间消耗的统计信息
			long costTimeMs = HawkTime.getMillisecond() - beginTimeMs;
			if (costTimeMs > 200) {
				DungeonRedisLog.log(battleId, "{} process tick too much time, costtime: {}", Thread.currentThread().getName(), costTimeMs);
			}
		}
		return true;
	}

	private void marchTick(ISWWorldMarch march) {
		try {
			if (march.getParent().getParent().isGameOver()) {
				return;
			}

			long beginTimeMs = HawkTime.getMillisecond();
			int statusOld = march.getMarchStatus();
			String armyOld = march.getMarchEntity().getArmyStr();

			if (march.getMarchType() != WorldMarchType.SPY && march.getMarchEntity().getArmyCount() == 0) {
				march.onMarchBack();
				march.remove();
				return;
			}
			if (march.getMarchStatus() == 0) {
				march.remove();
				return;
			}
			if (march.getMarchStatus() > 0) {
				march.heartBeats();
			}

			long costTimeMs = HawkTime.getMillisecond() - beginTimeMs;
			if (costTimeMs > 50) {
				DungeonRedisLog.log(battleId, "{} march tick too much time, costtime: {} Origion:{},{} Terminal:{},{} marchtype: {} marchstatus: {} -> {} army:{} -> {}",
						Thread.currentThread().getName(), costTimeMs,
						march.getOrigionX(), march.getOrigionY(),
						march.getTerminalX(), march.getTerminalY(),
						march.getMarchType(),
						statusOld, march.getMarchStatus(),
						armyOld, march.getMarchEntity().getArmyStr());
			}

		} catch (Exception e) {
			march.onMarchBack();
			march.remove();
			HawkException.catchException(e);
		}
	}

	private void buildTick(ISWWorldPoint point) {
		try {
			if (point.getParent().isGameOver()) {
				return;
			}

			long beginTimeMs = HawkTime.getMillisecond();
			ISWBuilding build = (ISWBuilding) point;
			SWBuildState stateOld = build.getState();

			point.onTick();
			long costTimeMs = HawkTime.getMillisecond() - beginTimeMs;
			if (costTimeMs > 50) {
				DungeonRedisLog.log(battleId, "{} build tick too much time, costtime: {} pos:{},{}  cfgId: {} state: {} -> {}", Thread.currentThread().getName(), costTimeMs,
						build.getX(), build.getY(),
						build.getClass().getSimpleName(),
						stateOld.name(), build.getState().name());
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}

	private void pointTick(ISWWorldPoint point) {
		try {
			if (point.getParent().isGameOver()) {
				return;
			}

			long beginTimeMs = HawkTime.getMillisecond();
			point.onTick();
			long costTimeMs = HawkTime.getMillisecond() - beginTimeMs;
			if (costTimeMs > 20) {
				DungeonRedisLog.log(battleId, "{} point tick too much time, costtime: {} point {}", Thread.currentThread().getName(), costTimeMs, point.getPointType());
			}
		} catch (Exception e) {
			HawkException.catchException(e);
		}
	}
	
	public void notifyMarchEventAsync(SWNotifyMarchEventFunc func){
		noticyMarchEnentMap.put(func.getMarch().getMarchId(), func);
	}

	public boolean isReadyToRace() {
		return readyToRace.get();
	}

	public void readyToRace() {
		readyToRace.set(true);
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public boolean addPoint(ISWWorldPoint task) {
		if (readyToRace.get()) {
			return false;
		}

		viewPoints.add(task);
		return true;
	}

	public boolean addMarch(ISWWorldMarch task) {
		if (readyToRace.get()) {
			return false;
		}

		marchs.add(task);
		return true;
	}

	public int getThreadNum() {
		return threadNum;
	}

	public void setThreadNum(int threadNum) {
		this.threadNum = threadNum;
	}

}
