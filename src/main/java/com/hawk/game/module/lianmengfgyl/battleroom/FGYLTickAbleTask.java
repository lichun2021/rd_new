package com.hawk.game.module.lianmengfgyl.battleroom;

import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.atomic.AtomicBoolean;

import org.hawk.os.HawkException;
import org.hawk.os.HawkTime;
import org.hawk.thread.HawkTask;

import com.hawk.game.log.DungeonRedisLog;
import com.hawk.game.module.lianmengfgyl.battleroom.worldmarch.FGYLNotifyMarchEventFunc;
import com.hawk.game.module.lianmengfgyl.battleroom.worldmarch.IFGYLWorldMarch;
import com.hawk.game.module.lianmengfgyl.battleroom.worldpoint.FGYLBuildState;
import com.hawk.game.module.lianmengfgyl.battleroom.worldpoint.IFGYLBuilding;
import com.hawk.game.protocol.World.WorldMarchType;

public class FGYLTickAbleTask extends HawkTask {
	private String battleId;
	private int threadNum;
	private Deque<IFGYLWorldPoint> viewPoints = new LinkedList<>();

	private Deque<IFGYLWorldMarch> marchs = new LinkedList<>();
	private ConcurrentSkipListMap<String, FGYLNotifyMarchEventFunc> noticyMarchEnentMap = new ConcurrentSkipListMap<>();

	/** 准备运行 */
	private AtomicBoolean readyToRace = new AtomicBoolean(false);

	@Override
	public Object run() {
		long beginTimeMs = HawkTime.getMillisecond();
		try {

			while (!marchs.isEmpty()) {
				IFGYLWorldMarch march = marchs.pop();
				marchTick(march);
			}

			while (!viewPoints.isEmpty()) {
				IFGYLWorldPoint point = viewPoints.pop();
				if (point instanceof IFGYLBuilding) {
					buildTick(point);
				} else {
					pointTick(point);
				}
			}
			while (!noticyMarchEnentMap.isEmpty()) {
				// 移除当前时间节点集合
				FGYLNotifyMarchEventFunc func = noticyMarchEnentMap.pollFirstEntry().getValue();
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

	private void marchTick(IFGYLWorldMarch march) {
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

	private void buildTick(IFGYLWorldPoint point) {
		try {
			if (point.getParent().isGameOver()) {
				return;
			}

			long beginTimeMs = HawkTime.getMillisecond();
			IFGYLBuilding build = (IFGYLBuilding) point;
			FGYLBuildState stateOld = build.getState();

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

	private void pointTick(IFGYLWorldPoint point) {
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

	public void notifyMarchEventAsync(FGYLNotifyMarchEventFunc func) {
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

	public boolean addPoint(IFGYLWorldPoint task) {
		if (readyToRace.get()) {
			return false;
		}

		viewPoints.add(task);
		return true;
	}

	public boolean addMarch(IFGYLWorldMarch task) {
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
