package com.hawk.game.service.starwars;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.thread.HawkThreadPool;

import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.global.GlobalData;
import com.hawk.game.player.Player;
import com.hawk.game.service.GuildService;
import com.hawk.game.util.LogUtil;
import com.hawk.log.LogConst.PushEventType;

public class StarWarOpenPushTask extends HawkTask{

	private List<SWRoomData> roomList;

	
	public StarWarOpenPushTask(List<SWRoomData> rooms) {
		this.roomList = new ArrayList<>();
		this.roomList.addAll(rooms);
	}
	
	
	@Override
	public Object run() {
		this.warPush();
		return null;
	}

	
	public void warPush(){
		for(SWRoomData roomData : this.roomList){
			List<String> guilds = roomData.getGuildList();
			if(Objects.isNull(guilds) || guilds.isEmpty()){
				continue;
			}
			for(String guild : guilds){
				//如果是本服队伍
				GuildInfoObject guildData = GuildService.getInstance().getGuildInfoObject(guild);
				if(Objects.isNull(guildData)){
					continue;
				}
				Collection<String> members = GuildService.getInstance().getGuildMembers(guild);
				for(String playerId : members){
					Player player = GlobalData.getInstance().makesurePlayer(playerId);
					
					if(Objects.isNull(player)){
						continue;
					}
					if(player.isCsPlayer()){
						continue;
					}
					LogUtil.logPushEvent(player, PushEventType.STAR_WARS, 0);
				}
			}
		}
	}

	
	public static void addPushEvnet(List<SWRoomData> rooms){
		if(Objects.isNull(rooms)){
			return;
		}
		if(rooms.isEmpty()){
			return;
		}
		StarWarOpenPushTask task = new StarWarOpenPushTask(rooms);
		HawkThreadPool taskPool = HawkTaskManager.getInstance().getThreadPool("task");
		if (Objects.nonNull(taskPool)) {
			task.setTypeName("SW_OPEN_PUSH");
			taskPool.addTask(task, -1, false);
		}
	}
	
}
