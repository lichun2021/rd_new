package com.hawk.game.module.lianmengfgyl.battleroom.roomstate;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkTime;
import org.hawk.task.HawkTaskManager;
import org.hawk.tuple.HawkTuple2;
import org.hawk.tuple.HawkTuples;

import com.hawk.game.module.lianmengfgyl.battleroom.IFGYLWorldPoint;
import com.hawk.game.module.lianmengfgyl.battleroom.FGYLBattleRoom;
import com.hawk.game.module.lianmengfgyl.battleroom.FGYLConst.FGYLState;
import com.hawk.game.module.lianmengfgyl.battleroom.player.IFGYLPlayer;
import com.hawk.game.module.lianmengfgyl.battleroom.worldmarch.IFGYLWorldMarch;
import com.hawk.game.module.lianmengfgyl.battleroom.worldpoint.FGYLBuildState;
import com.hawk.game.module.lianmengfgyl.battleroom.worldpoint.FGYLHeadQuarter;
import com.hawk.game.module.lianmengtaiboliya.ITBLYWorldPoint;
import com.hawk.game.module.lianmengtaiboliya.worldmarch.ITBLYWorldMarch;
import com.hawk.game.protocol.Const.ChatType;
import com.hawk.game.protocol.Const.NoticeCfgId;
import com.hawk.game.protocol.GuildWar.HPGuildWarCountPush;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.World.WorldMarchType;
import com.hawk.game.service.chat.ChatParames;

/**
 * 游戏中
 * 
 * @author lwt
 * @date 2018年11月15日
 */
public class FGYLGameing extends IFGYLBattleRoomState {
	private List<HawkTuple2<Long, Integer>> countMinList = new LinkedList<>();
	private long nextSyncGuildWarCount;
	private int tickCnt;
	private long buildOpenTwo;
	private long buildOpenFour;
	public FGYLGameing(FGYLBattleRoom room) {
		super(room);
		final long startTime = room.getStartTime();
		long endTime = room.getOverTime();
		for (int i = 1; i < 100 && endTime > startTime; i++) {
			endTime -= TimeUnit.MINUTES.toMillis(1);
			if (i == 5) {
				countMinList.add(HawkTuples.tuple(endTime, i));
			}
		}
		Collections.reverse(countMinList);
	}

	@Override
	public boolean onTick() {
		tickCnt++;
		long now = HawkTime.getMillisecond();
		if (!countMinList.isEmpty()) {
			HawkTuple2<Long, Integer> tt = countMinList.get(0);
			if (now > tt.first) {
				countMinList.remove(0);
			}
		}


		if (now > getParent().getOverTime()) {
			getParent().setOverState(3);
			getParent().setState(new FGYLGameOver(getParent()));
			return true;
		}

		List<IFGYLPlayer> plist = getParent().getPlayerList(FGYLState.GAMEING);
		for(IFGYLPlayer player :plist){
			try {
				player.onTick();
			} catch (Exception e) {
				HawkException.catchException(e);
			}
		}
		final int threadNum = HawkTaskManager.getInstance().getThreadNum();
		for (IFGYLWorldPoint point : getParent().getViewPoints()) {
			getParent().getTickAbleTask(point.getHashThread(threadNum)).addPoint(point);
		}

		for (IFGYLWorldMarch march : getParent().getWorldMarchList()) {
			System.out.println(march.getHashThread());
			getParent().getTickAbleTask(march.getHashThread()).addMarch(march);
		}

		if (now > nextSyncGuildWarCount) {
			nextSyncGuildWarCount = now + 2000;
			HPGuildWarCountPush.Builder builder = HPGuildWarCountPush.newBuilder();
			int campAGuildWarCount = getParent().getGuildWarMarch(getParent().getBaseInfoA().getGuildId()).size();
			int campBGuildWarCount = getParent().getGuildWarMarch(getParent().getBaseInfoB().getGuildId()).size();
			getParent().setCampAGuildWarCount(campAGuildWarCount);
			getParent().setCampBGuildWarCount(campBGuildWarCount);
			builder.setCount(getParent().getCsGuildWarCount());
			getParent().broadcastCrossProtocol(HawkProtocol.valueOf(HP.code.PUSH_GUILD_WAR_COUNT_VALUE, builder));
			for (IFGYLPlayer player : plist) {
				if (!player.isCsPlayer()) {
					player.getPush().syncGuildWarCount();
				}
			}
		}

		return true;
	}

}
