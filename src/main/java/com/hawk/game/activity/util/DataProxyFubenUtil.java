package com.hawk.game.activity.util;

import java.util.Objects;

import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.tuple.HawkTuple2;

import com.hawk.game.config.CrossSeasonTimeCfg;
import com.hawk.game.config.CyborgSeasonTimeCfg;
import com.hawk.game.config.StarWarsTimeCfg;
import com.hawk.game.config.TiberiumSeasonTimeCfg;
import com.hawk.game.config.XHJZSeasonTimeCfg;
import com.hawk.game.config.XQHXSeasonTimeCfg;
import com.hawk.game.global.GlobalData;
import com.hawk.game.module.PlayerXQHXModule;
import com.hawk.game.module.dayazhizhan.playerteam.cfg.DYZZSeasonTimeCfg;
import com.hawk.game.module.dayazhizhan.playerteam.season.DYZZSeasonBattleInfo;
import com.hawk.game.module.dayazhizhan.playerteam.season.DYZZSeasonRedisData;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZTimeCfg;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Activity;
import com.hawk.game.service.cyborgWar.CWPlayerData;
import com.hawk.game.service.cyborgWar.CyborgWarRedis;
import com.hawk.game.util.GsConst;

public class DataProxyFubenUtil {

	public static HawkTuple2<Long, Long> getSeasonActivityMatchInfo(Activity.SeasonMatchType matchType, int termID) {
		//赛事时间
		HawkTuple2<Long, Long> tuple2;
		switch (matchType){
			//泰伯利亚
			case S_TBLY:{
				long startTime = 0L;
				long endTime = 0L;
				//遍历泰伯活动时间配置
				ConfigIterator<TiberiumSeasonTimeCfg> its = HawkConfigManager.getInstance()
						.getConfigIterator(TiberiumSeasonTimeCfg.class);
				for(TiberiumSeasonTimeCfg cfg : its){
					//只处理配置的对应赛季
					if(cfg.getSeason() == termID){
						//获得开始时间
						if(cfg.getSeasonStartTimeValue() != 0){
							startTime = cfg.getSeasonStartTimeValue();
						}
						//获得结束时间
						if(cfg.getSeasonEndTimeValue() != 0){
							endTime = cfg.getSeasonEndTimeValue();
						}
					}
				}
				tuple2 = new HawkTuple2<>(startTime, endTime);
			}
			break;
			//赛博
			case S_CYBORG:{
				//获得配置期数对应的赛博时间配置
				CyborgSeasonTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(CyborgSeasonTimeCfg.class, termID);
				if(cfg == null){
					//如果没找到配置返回0
					tuple2 = new HawkTuple2<>(0L, 0L);
				}else {
					tuple2 = new HawkTuple2<>(cfg.getShowTimeValue(), cfg.getEndTimeValue());
				}
			}
			break;
			//陨晶，打压之战
			case S_DYZZ:{
				//获得配置期数对应的陨晶，打压之战时间配置
				DYZZSeasonTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(DYZZSeasonTimeCfg.class, termID);
				if(cfg == null){
					//如果没找到配置返回0
					tuple2 = new HawkTuple2<>(0L, 0L);
				}else {
					tuple2 = new HawkTuple2<>(cfg.getShowTimeValue(), cfg.getEndTimeValue());
				}
			}
			break;
			//统帅，大帝战，星球大战
			case S_SW:{
				StarWarsTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(StarWarsTimeCfg.class, termID);
				if(cfg == null){
					//如果没找到配置返回0
					tuple2 = new HawkTuple2<>(0L, 0L);
				}else {
					tuple2 = new HawkTuple2<>(cfg.getSignStartTimeValue(), cfg.getEndTimeValue());
				}
			}
			break;
			//月球之巅
			case S_YQZZ:{
				long startTime = 0L;
				long endTime = 0L;
				ConfigIterator<YQZZTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class);
				for(YQZZTimeCfg cfg : iterator){
					//只处理配置的对应赛季
					if(cfg.getSeason() == termID){
						//获得开始时间
						if(cfg.getSeasonStartTimeValue() != 0){
							startTime = cfg.getSeasonStartTimeValue();
						}
						//获得结束时间
						if(cfg.getSeasonEndTimeValue() != 0){
							endTime = cfg.getSeasonEndTimeValue();
						}
					}
				}
				tuple2 = new HawkTuple2<>(startTime, endTime);
			}
			break;
			//星海激战
			case S_XHJZ:{
				XHJZSeasonTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(XHJZSeasonTimeCfg.class, termID);
				if(cfg == null){
					//如果没找到配置返回0
					tuple2 = new HawkTuple2<>(0L, 0L);
				}else {
					tuple2 = new HawkTuple2<>(cfg.getSeasonStartTimeValue(), cfg.getSeasonEndTimeValue());
				}
			}
			break;
			//星海激战
			case S_XQHX:{
				XQHXSeasonTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(XQHXSeasonTimeCfg.class, termID);
				if(cfg == null){
					//如果没找到配置返回0
					tuple2 = new HawkTuple2<>(0L, 0L);
				}else {
					tuple2 = new HawkTuple2<>(cfg.getSeasonStartTimeValue(), cfg.getSeasonEndTimeValue());
				}
			}
			break;
			//航海赛季
			case S_CROSS:{
				CrossSeasonTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(CrossSeasonTimeCfg.class, termID);
				if(cfg == null){
					//如果没找到配置返回0
					tuple2 = new HawkTuple2<>(0L, 0L);
				}else {
					tuple2 = new HawkTuple2<>(cfg.getShowTimeValue(), cfg.getEndTimeValue());
				}
			}
			break;
			default:{
				//未知类型返回0
				tuple2 = new HawkTuple2<>(0L, 0L);
			}
		}
		//返回赛事时间
		return tuple2;
	}
	
	public static boolean checkCyborgWar(String playerId) {
		//TODO 配合装扮修复数据使用
		//指定12期检查赛博数据 
		CWPlayerData cwPlayerData = CyborgWarRedis.getInstance().getCWPlayerData(playerId, 12);
		if (cwPlayerData == null) {
			return false;
		}
		return cwPlayerData.getEnterTime() > 0?true:false;
	}
	
	public static String getDYZZBattleInfo(String playerId) {
		DYZZSeasonBattleInfo battleInfo = DYZZSeasonRedisData.getInstance().getDYZZSeasonBattle(playerId);
		if(battleInfo == null){
			return null;
		}
		return battleInfo.serializ();
	}

	public static void delDYZZBattleInfo(String playerId) {
		DYZZSeasonRedisData.getInstance().delDYZZSeasonBattle(playerId);
	}
	
	public static void xqhxTalentCheck(String playerId){
		Player player= GlobalData.getInstance().makesurePlayer(playerId);
		if (Objects.isNull(player)) {
			return;
		}
		PlayerXQHXModule module = player.getModule(GsConst.ModuleType.XQHX_WAR_MOUDLE);
		module.checkTalent(player);
	}
}
