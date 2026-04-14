package com.hawk.game.script;

import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;

import java.util.Map;

import com.hawk.activity.ActivityBase;
import com.hawk.activity.ActivityManager;
import com.hawk.activity.type.impl.alliancerush.AllianceRushActivity;
import com.hawk.activity.type.impl.alliancerush.entity.AllianceRushEntity;
import com.hawk.game.protocol.Activity;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.Reward;
import com.hawk.log.Action;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.hawk.activity.type.impl.alliancerush.cfg.AllianceRushKVCfg;
import org.hawk.config.HawkConfigManager;

public class AllianceRushAddPointHandler extends HawkScript {

	@Override
	public String action(Map<String, String> params, HawkScriptHttpInfo arg1) {
		
		String playerId = params.get("playerId");
		String score = params.get("score");

		Optional<ActivityBase> activityOp = ActivityManager.getInstance().getActivity(Activity.ActivityType.ALLIANCE_RUSH_VALUE);

		AllianceRushActivity activity = (AllianceRushActivity) activityOp.get();
		List<Reward.RewardItem.Builder> reweardList = new ArrayList<>();
		Reward.RewardItem.Builder rewardItemBuilder = Reward.RewardItem.newBuilder();
		rewardItemBuilder.setItemType(Const.ItemType.TOOL_VALUE);
		AllianceRushKVCfg rushKVCfg = HawkConfigManager.getInstance().getKVInstance(AllianceRushKVCfg.class);
		rewardItemBuilder.setItemId(rushKVCfg.getPointItemId());
		rewardItemBuilder.setItemCount(Integer.parseInt(score));
		reweardList.add(rewardItemBuilder);
		activity.getDataGeter().takeReward(playerId, reweardList, Action.ALLIANCE_RUSH_POINT_REWARD, true);
		activity.onTakeRewardSuccessAfter(playerId, reweardList, 0);
		activity.syncActivityDataInfo(playerId);

		return HawkScript.successResponse(playerId+":"+score);
	}

}
