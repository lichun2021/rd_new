package com.hawk.game.module.lianmengfgyl.march.service;

import java.util.Collection;
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

public class FGYLWarOpenPushTask extends HawkTask{

	private String guildId;

	
	public FGYLWarOpenPushTask(String guildId) {
		this.guildId = guildId;
	}
	
	
	@Override
	public Object run() {
		this.warPush();
		return null;
	}

	
	public void warPush(){
		GuildInfoObject guild = GuildService.getInstance().getGuildInfoObject(this.guildId);
		if(Objects.isNull(guild)){
			return;
		}
		Collection<String> col = GuildService.getInstance().getGuildMembers(guildId);
		for(String playerId : col){
			Player player = GlobalData.getInstance().makesurePlayer(playerId);
			if(Objects.isNull(player)){
				continue;
			}
			LogUtil.logPushEvent(player, PushEventType.YURI_EVENT, 0);
		}
	}

	
	public static void addPushEvnet(String guildId){
		FGYLWarOpenPushTask task = new FGYLWarOpenPushTask(guildId);
		HawkThreadPool taskPool = HawkTaskManager.getInstance().getThreadPool("task");
		if (Objects.nonNull(taskPool)) {
			task.setTypeName("FGYL_OPEN_PUSH");
			taskPool.addTask(task, -1, false);
		}
	}
	
}
