package com.hawk.game.service.cyborgWar;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.thread.HawkThreadPool;

import com.hawk.game.GsConfig;
import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.entity.GuildMemberObject;
import com.hawk.game.global.GlobalData;
import com.hawk.game.player.Player;
import com.hawk.game.service.GuildService;
import com.hawk.game.util.LogUtil;
import com.hawk.log.LogConst.PushEventType;

public class CyborgWarOpenPushTask extends HawkTask{
	
	private int term;
	private List<CWRoomData> roomList;
	
	
	public CyborgWarOpenPushTask(int term,int timeIndex,List<CWRoomData> rooms) {
		this.term = term;
		if(Objects.nonNull(rooms)){
			this.roomList = rooms.stream().filter(t ->t.getTimeIndex() == timeIndex).collect(Collectors.toList());
		}
		
	}
	
	@Override
	public Object run() {
		if(Objects.isNull(this.roomList) || this.roomList.isEmpty()){
			return null;
		}
		List<String> pushTeamIds = new ArrayList<>();
		for(CWRoomData roomData : this.roomList){
			Map<String,String> teams = roomData.getGtMaps();
			if(Objects.isNull(teams) || teams.isEmpty()){
				continue;
			}
			for(Entry<String, String> entry : teams.entrySet()){
				String guildId = entry.getKey();
				String teamId = entry.getValue();
				//如果是本服联盟
				GuildInfoObject guildObj = GuildService.getInstance().getGuildInfoObject(guildId);
				if(Objects.isNull(guildObj)){
					continue;
				}
				pushTeamIds.add(teamId);
			}
		}
		//加载队伍数据
		Map<String, CWTeamJoinData> teamMap = CyborgWarRedis.getInstance().getCWJoinTeamDatas(pushTeamIds, this.term);
		if(Objects.isNull(teamMap) || teamMap.isEmpty()){
			return null;
		}
		//开始日志
		String serverId = GsConfig.getInstance().getServerId();
		for(CWTeamJoinData joinData : teamMap.values()){
			if(!joinData.getServerId().equals(serverId)){
				continue;
			}
			//取队员
			Set<String> idList = CyborgWarRedis.getInstance().getCWPlayerIds(joinData.getId());
			if(Objects.isNull(idList) || idList.isEmpty()){
				return null;
			}
			for(String memberId : idList){
				GuildMemberObject member = GuildService.getInstance().getGuildMemberObject(memberId);
				// 非本盟玩家
				if (Objects.isNull(member)) {
					continue;
				}
				Player player = GlobalData.getInstance().makesurePlayer(memberId);
				if(Objects.isNull(player)){
					continue;
				}
				LogUtil.logPushEvent(player, PushEventType.DUNGEON_CYBOR, 0);
			}
		}
		return null;
	}
	
	
	/**
	 * 添加推送
	 * @param teamIds
	 */
	public static void addPushEvnet(int term,int timeIndex,List<CWRoomData> rooms){
		if(Objects.isNull(rooms)){
			return;
		}
		if(rooms.isEmpty()){
			return;
		}
		CyborgWarOpenPushTask task = new CyborgWarOpenPushTask(term, timeIndex, rooms);
		HawkThreadPool taskPool = HawkTaskManager.getInstance().getThreadPool("task");
		if (Objects.nonNull(taskPool)) {
			task.setTypeName("CB_OPEN_PUSH");
			taskPool.addTask(task, 0, false);
		}
	}

}
