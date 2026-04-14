package com.hawk.game.service.mssion.type;

import com.hawk.game.item.mission.MissionCfgItem;
import com.hawk.game.item.mission.MissionEntityItem;
import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerData;
import com.hawk.game.service.mssion.Mission;
import com.hawk.game.service.mssion.MissionEvent;
import com.hawk.game.service.mssion.MissionType;
import com.hawk.game.service.mssion.event.EventGuildHelp;

/**
 * 联盟帮助任务
 * 
 * @author golden
 *
 */
@Mission(missionType = MissionType.MISSION_GUILD_HELP)
public class GuildHelpMission implements IMission {

	@Override
	public <T extends MissionEvent> void refreshMission(PlayerData playerData, T missionEvent, MissionEntityItem entityItem, MissionCfgItem cfg) {
		int multiply = 1;
		if (missionEvent instanceof EventGuildHelp) {
			EventGuildHelp event = (EventGuildHelp) missionEvent;
			if (event.getMultiply() > 0) {
				multiply = event.getMultiply();
			}
		}

		entityItem.addValue(multiply);
		checkMissionFinish(entityItem, cfg);
	}

	@Override
	public <T extends MissionEvent> void refreshGeneralMission(Player player, T missionEvent) {
		// TODO Auto-generated method stub
		
	}
}
