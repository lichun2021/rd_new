package com.hawk.game.service.guildtask.impl;

import com.hawk.game.service.guildtask.GuildTask;
import com.hawk.game.service.guildtask.GuildTaskCfgItem;
import com.hawk.game.service.guildtask.GuildTaskEvent;
import com.hawk.game.service.guildtask.GuildTaskItem;
import com.hawk.game.service.guildtask.GuildTaskType;
import com.hawk.game.service.guildtask.event.GuildHelpTaskEvent;

/**
 * 联盟成员完成联盟帮助次数任务
 * 
 * @author jesse
 *
 */
@GuildTask(taskType = GuildTaskType.guild_help)
public class GuildHelpNumTask implements IGuildTask {

	@Override
	public <T extends GuildTaskEvent> boolean refreshTask(String guildId, T taskEvent, GuildTaskItem taskItem, GuildTaskCfgItem cfg) {
		int multiply = 1;
		if (taskEvent instanceof GuildHelpTaskEvent) {
			GuildHelpTaskEvent event = (GuildHelpTaskEvent) taskEvent;
			if (event.getMultiply() > 0) {
				multiply = event.getMultiply();
			}
		}
		int value = Math.min(taskItem.getValue() + multiply, cfg.getValue());
		taskItem.setValue(value);
		return true;
	}

}
