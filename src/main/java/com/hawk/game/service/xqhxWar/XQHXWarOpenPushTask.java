package com.hawk.game.service.xqhxWar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.hawk.os.HawkOSOperator;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.thread.HawkThreadPool;

import com.hawk.game.GsConfig;
import com.hawk.game.global.GlobalData;
import com.hawk.game.player.Player;
import com.hawk.game.service.guildTeam.ipml.XQHXGuildTeamManager;
import com.hawk.game.service.guildTeam.model.GuildTeamData;
import com.hawk.game.util.LogUtil;
import com.hawk.log.LogConst.PushEventType;

public class XQHXWarOpenPushTask extends HawkTask{

	private List<XQHXWarRoomData> roomList;

	
	public XQHXWarOpenPushTask(int timeIndex,Set<String> roomIds,Map<String,XQHXWarRoomData> rooms) {
		this.roomList = new ArrayList<>();
		for(String roomId : roomIds){
			XQHXWarRoomData roomData = rooms.get(roomId);
            if(roomData == null || roomData.timeIndex != timeIndex){
                continue;
            }
            this.roomList.add(roomData);
        }
	}
	
	
	@Override
	public Object run() {
		this.warPush();
		return null;
	}

	
	public void warPush(){
		String serverId = GsConfig.getInstance().getServerId();
		Set<String> pushTeamIds = new HashSet<>();
		List<String> tempList = new ArrayList<>();
		for(XQHXWarRoomData roomData : this.roomList){
			tempList.clear();
			String teamA = roomData.campA;
			String teamB = roomData.campB;
			if(!HawkOSOperator.isEmptyString(teamA)){
				tempList.add(teamA);
			}
			if(!HawkOSOperator.isEmptyString(teamB)){
				tempList.add(teamB);
			}
			for(String teamId : tempList){
				//如果是本服队伍
				GuildTeamData data = XQHXGuildTeamManager.getInstance().getTeamData(teamId);
				if(Objects.isNull(data)){
					continue;
				}
				if(!serverId.equals(data.serverId)){
					continue;
				}
				pushTeamIds.add(teamId);
			}
		}
		//开始日志
		for(String teamId : pushTeamIds){
			//取队员
			Set<String> pids = XQHXGuildTeamManager.getInstance().getTeamPlayerIds(teamId);
			if(Objects.isNull(pids) || pids.isEmpty()){
				continue;
			}
			for(String memberId : pids){
				Player player = GlobalData.getInstance().makesurePlayer(memberId);
				if(Objects.isNull(player)){
					continue;
				}
				LogUtil.logPushEvent(player, PushEventType.DUNGEON_XQHX, 0);
			}
		}
	}

	
	public static void addPushEvnet(int timeIndex,Set<String> roomIds,Map<String,XQHXWarRoomData> rooms){
		if(Objects.isNull(rooms)){
			return;
		}
		if(rooms.isEmpty()){
			return;
		}
		XQHXWarOpenPushTask task = new XQHXWarOpenPushTask(timeIndex, roomIds, rooms);
		HawkThreadPool taskPool = HawkTaskManager.getInstance().getThreadPool("task");
		if (Objects.nonNull(taskPool)) {
			task.setTypeName("XHHX_OPEN_PUSH");
			taskPool.addTask(task, 0, false);
		}
	}
	
}
