package com.hawk.game.script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;

import com.hawk.game.GsConfig;
import com.hawk.game.config.MilestoneCfg;
import com.hawk.game.config.MilestoneTaskCfg;
import com.hawk.game.global.GlobalData;
import com.hawk.game.item.mission.MissionEntityItem;
import com.hawk.game.module.rookieMilestone.RookieMilestoneService;
import com.hawk.game.module.rookieMilestone.entity.RookieMilestoneEntity;
import com.hawk.game.player.Player;
import com.hawk.game.util.GsConst.MissionState;
import com.hawk.game.util.LogUtil;
import com.hawk.log.LogConst.ChapterMissionOperType;
import com.hawk.log.LogConst.TaskType;

/**
 * 完成指定章节的剧情任务（包括该章节之前的所有任务）
 * 
 * http://localhost:8080/script/milestoneMissionFinish?playerId=1aau-2ayfd6-1&chapter=9
 * 
 * @author lating
 *
 */
public class MilestoneMissionFinishHandler extends HawkScript {
	
	@Override
	public String action(Map<String, String> params, HawkScriptHttpInfo httpInfo) {
		return doAction(params);
	}

	public static String doAction(Map<String, String> params) {
		if (!GsConfig.getInstance().isDebug()) {
			return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "is not debug");
		}
		
		if (!params.containsKey("chapter")) {
			return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "chapter param need");
		}
		
		try {
			Player player = GlobalData.getInstance().scriptMakesurePlayer(params);
			if (player == null) {
				return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "player not exist");
			}
			
			int chapter = Integer.parseInt(params.get("chapter"));
			if (chapter <= 0) {
				return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "chapter param error");
			}
			
			if (player.isActiveOnline()) {
				int threadIdx = player.getXid().getHashThread(HawkTaskManager.getInstance().getThreadNum());
				HawkTaskManager.getInstance().postTask(new HawkTask() {
					@Override
					public Object run() {
						finishChapterTask(player, chapter, Integer.parseInt(params.getOrDefault("cityControl", "1")));
						return null;
					}
				}, threadIdx);
			} else {
				finishChapterTask(player, chapter, Integer.parseInt(params.getOrDefault("cityControl", "1")));
			}
			
			return HawkScript.successResponse("ok");
		} catch (Exception e) {
			HawkException.catchException(e);
			return HawkException.formatStackMsg(e);
		}
	}
	
	private static void finishChapterTask(Player player, int chapter, int cityControl) {
		RookieMilestoneEntity entity = player.getData().getRookieMilestoneEntity();
		if (entity.getCurrentChapterId() > chapter) {
			HawkLog.logPrintln("MilestoneMissionFinishHandler handler, playerId: {}, chapter {} already finished, latest chapter: {}", player.getId(), chapter, entity.getCurrentChapterId());
			return;
		}
		
		int chapterId = entity.getCurrentChapterId();
		int maxChapterId = HawkConfigManager.getInstance().getConfigSize(MilestoneCfg.class);
		int count = 0;
		while (chapterId <= chapter && count <= maxChapterId) {
			try {
				List<MissionEntityItem> itemList = new ArrayList<>();
				if (entity.getCurrentChapterId()==chapterId) {
					itemList.addAll(entity.getMissionItemList());
				}
				
				for (MissionEntityItem item : itemList) {
					if (item.getState() == MissionState.STATE_BONUS) {
						continue;
					}
					
					RookieMilestoneService.getInstance().receiveAndPushMissionReward(player, chapterId, item.getCfgId());
					entity.changeMissionState(item.getCfgId(), MissionState.STATE_BONUS);
					MilestoneTaskCfg cfg = MilestoneTaskCfg.getMilestoneTaskCfg(chapterId, item.getCfgId());
					RookieMilestoneService.getInstance().logTaskFlow(player, cfg, MissionState.STATE_BONUS);
				}
				
				if (maxChapterId == entity.getCurrentChapterId()) {
					RookieMilestoneService.getInstance().syncMilestoneMissionInfo(player);
					return;
				}

				// 领取章节任务奖励打点记录
				LogUtil.logChapterMissionFlow(player, TaskType.MILESTONE_MISSION, chapterId, ChapterMissionOperType.COMPLETE_AWARD_TAKEN);
				if (entity.getCurrentChapterId()==chapterId) {
					RookieMilestoneService.getInstance().chapterComplete(player, entity);
				}
				

				if (entity.getCurrentChapterId() > chapterId) {
					chapterId = entity.getCurrentChapterId();
				}
			} catch (Exception e) {
				HawkException.catchException(e);
				RookieMilestoneService.getInstance().syncMilestoneMissionInfo(player);
				return;
			}
			
			count++;
		}

		RookieMilestoneService.getInstance().syncMilestoneMissionInfo(player);
	}


}
