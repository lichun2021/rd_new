package com.hawk.game.player.item.impl;

import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkTime;

import com.hawk.game.config.BuffCfg;
import com.hawk.game.config.DressCfg;
import com.hawk.game.config.DressToolCfg;
import com.hawk.game.config.EffectCfg;
import com.hawk.game.config.ItemCfg;
import com.hawk.game.entity.DressEntity;
import com.hawk.game.entity.StatusDataEntity;
import com.hawk.game.entity.item.DressItem;
import com.hawk.game.item.AwardItems;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.module.dayazhizhan.playerteam.cfg.DYZZSeasonCfg;
import com.hawk.game.player.Player;
import com.hawk.game.player.item.AbstractItemUseEffect;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.Status;
import com.hawk.game.protocol.Const.ToolType;
import com.hawk.game.util.GsConst;
import com.hawk.log.Action;

public class BoxRewardItemUseEffect extends AbstractItemUseEffect {

	@Override
	public int itemType() {
		return Const.ToolType.BOX_CHOOSE_REWARD_VALUE;
	}

	@Override
	public boolean useItemCheck(Player player, ItemCfg itemCfg, int itemId, int itemCount, int protoType, String targetId) {
		int index = Integer.parseInt(targetId);
		index = Math.max(0, index - 1);
		ItemInfo itemInfo = itemCfg.getChooseAward(index);
		if (itemInfo == null) {
			player.sendError(protoType, Status.SysError.PARAMS_INVALID_VALUE, 0);
			return false;
		}
		
		DYZZSeasonCfg dyzzSeasonCfg = HawkConfigManager.getInstance().getKVInstance(DYZZSeasonCfg.class);
		if(dyzzSeasonCfg != null && dyzzSeasonCfg.getItemSpecialCheck().contains(itemCfg.getId()) && hasDressAndFrame(player, itemInfo.getItemId())){
			player.sendError(protoType, Status.Error.DYZZ_CHOOSE_BOX_SAME_VALUE, 0);
			return false;
		}
		return true;
	}

	@Override
	public boolean useEffect(Player player, ItemCfg itemCfg, int itemCount, String targetId) {
		int index = Integer.parseInt(targetId);
		index = Math.max(0, index - 1);
		ItemInfo itemInfo = itemCfg.getChooseAward(index);
		itemInfo.setCount(itemInfo.getCount() * itemCount);
		AwardItems awardItem = AwardItems.valueOf();
		awardItem.addItem(itemInfo);
		awardItem.rewardTakeAffectAndPush(player, Action.USE_CHOOSE_AWARD_ITEM, true);
		return true;
	}

	private boolean hasDressAndFrame(Player player, int itemId){
		ItemCfg itemCfg = HawkConfigManager.getInstance().getConfigByKey(ItemCfg.class, itemId);
		switch (itemCfg.getItemType()){
			case ToolType.STATUS_VALUE:{
				BuffCfg buffCfg = HawkConfigManager.getInstance().getConfigByKey(BuffCfg.class, itemCfg.getBuffId());
				if (buffCfg != null) {
					EffectCfg effCfg = HawkConfigManager.getInstance().getConfigByKey(EffectCfg.class, buffCfg.getEffect());
					if (effCfg != null) {
						if (effCfg.getType() == GsConst.EffectType.IMAGE_ITEM.getValue()) {
							if(player.getData().getItemNumByItemId(itemId) > 0){
								return true;
							}
							StatusDataEntity entity = player.getData().getStatusById(effCfg.getId());
							if(entity != null && entity.getEndTime() > HawkTime.getMillisecond()){
								return true;
							}
						}
					}
				}
			}
			break;
			case Const.ToolType.DRESS_VALUE:{
				if(player.getData().getItemNumByItemId(itemId) > 0){
					return true;
				}
				DressToolCfg dressToolCfg = HawkConfigManager.getInstance().getConfigByKey(DressToolCfg.class, itemCfg.getDressId());
				if (dressToolCfg == null) {
					return false;
				}
				DressCfg dressCfg = HawkConfigManager.getInstance().getConfigByKey(DressCfg.class, dressToolCfg.getDressId());
				if (dressCfg == null) {
					return false;
				}
				DressEntity dressEntity = player.getData().getDressEntity();
				DressItem dressInfo = dressEntity.getDressInfo(dressCfg.getDressType(), dressCfg.getModelType());
				if(dressInfo!=null){
					return true;
				}
			}
			break;
		}
		return false;
	}
}
