package com.hawk.game.module.lianmengyqzz.march.service.state;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkTime;
import org.hawk.tuple.HawkTuple2;
import org.hawk.tuple.HawkTuples;

import com.google.common.collect.HashBiMap;
import com.hawk.game.GsConfig;
import com.hawk.game.entity.GuildInfoObject;
import com.hawk.game.entity.GuildMemberObject;
import com.hawk.game.module.lianmengyqzz.battleroom.YQZZBattleRoom;
import com.hawk.game.module.lianmengyqzz.battleroom.YQZZRoomManager;
import com.hawk.game.module.lianmengyqzz.battleroom.YQZZ_CAMP;
import com.hawk.game.module.lianmengyqzz.battleroom.extra.YQZZExtraParam;
import com.hawk.game.module.lianmengyqzz.battleroom.extra.YQZZGuild;
import com.hawk.game.module.lianmengyqzz.battleroom.roomstate.YQZZGameOver;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZTimeCfg;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZWarConstCfg;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZBattleData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZGameData;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinGuild;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchRoomData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityJoinState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZMatchService;
import com.hawk.game.protocol.YQZZ.PBYQZZGameInfoSync;
import com.hawk.game.service.GuildService;

public class YQZZState300Battle  extends IYQZZServiceState {

	private long lastTickTime = 0;
	public YQZZState300Battle(YQZZMatchService parent) {
		super(parent);
	}

	@Override
	public void init() {
		this.getDataManager().getStateData().setState(YQZZActivityState.BATTLE);
		this.getDataManager().getStateData().saveRedis();
		//如果不参与战斗
		YQZZActivityJoinState joinState = this.getDataManager()
				.getStateData().getJoinGame();
		if(joinState == YQZZActivityJoinState.OUT){
			return;
		}
		//创建房间
		this.createBattleRoom();
		//本服随意进入人员 这个先不要
		this.calFreeJoinPlayers();
		HawkLog.logPrintln("YQZZState300Battle-init:term:{}",this.getDataManager().getStateData().getTermId());
	}
	
	
	@Override
	public void tick() {
		//如果不参与战斗
		YQZZActivityJoinState joinState = this.getDataManager()
				.getStateData().getJoinGame();
		if(joinState == YQZZActivityJoinState.OUT){
			return;
		}
		long curTime = HawkTime.getMillisecond();
		if(curTime > this.lastTickTime + HawkTime.MINUTE_MILLI_SECONDS * 1){
			this.lastTickTime = curTime;
			//存数据
			this.saveBattleRoomData();
			//重新加载玩家战场数据
			this.loadBattleRoomData();
		}
	}

	@Override
	public void gmOp() {
		//存数据
		this.saveBattleRoomData();
		//重新加载玩家战场数据
		this.loadBattleRoomData();
		for(YQZZBattleRoom room :YQZZRoomManager.getInstance().findAllRoom()){
			room.setState(new YQZZGameOver(room));
			room.getState().onTick();
		}
	}

	
	/**
	 * 重新加载战斗数据
	 */
	private void loadBattleRoomData(){
		//重新加载玩家战场数据
		this.getDataManager().loadToCacheYQZZBattleData();
		Map<String, YQZZBattleData> battles = this.getDataManager().getBattleDatas();
		for(YQZZBattleData data : battles.values()){
			HawkLog.logPrintln("YQZZState300Battle-loadBattleRoomData-roomId:{}",data.getRoomId());
		}
	}
	
	
	/**
	 * 保存当前服的战斗房间数据
	 */
	private void saveBattleRoomData(){
		Map<String, YQZZMatchRoomData> rooms = this.getDataManager().getMatchRoomDatas();
		if(Objects.isNull(rooms) || rooms.isEmpty()){
			return;
		}
		String serverId = GsConfig.getInstance().getServerId();
		for(YQZZMatchRoomData room : rooms.values()){
			String roomServer = room.getRoomServerId();
			if(!serverId.equals(roomServer)){
				continue;
			}
			String roomId = room.getRoomId();
			PBYQZZGameInfoSync gameInfo = YQZZRoomManager.getInstance().getLastSyncpb(roomId);
			if(Objects.isNull(gameInfo)){
				continue;
			}
			YQZZBattleData.saveSourceData(gameInfo, null, roomId);
			HawkLog.logPrintln("YQZZState300Battle-saveBattleRoomData-{},roomId:{}",room.getRoomServerId(),room.getRoomId());
		}
	}
	
	
	
	/**
	 * 创建战斗房间
	 */
	public void createBattleRoom(){
		String serverId = GsConfig.getInstance().getServerId();
		Map<String, YQZZMatchRoomData> roomMap = this.getDataManager().getMatchRoomDatas();
		if(roomMap == null){
			return;
		}
		
		Map<String, YQZZJoinGuild> battleGuilds = this.getDataManager().getBattleGuilds();
		for(YQZZMatchRoomData room : roomMap.values()){
			if(serverId.equals(room.getRoomServerId())){
				YQZZTimeCfg timeCfg = this.getTimeCfg();
				List<String> guilds = room.getGuilds();
				YQZZExtraParam param = new YQZZExtraParam();
				HashBiMap<String, YQZZGuild> guildCamp = HashBiMap.create(6);
				param.setBattleId(room.getRoomId());
				param.setGuildCamp(guildCamp);
				for(int i=0;i<guilds.size();i++ ){
					String guildId = guilds.get(i);
					YQZZJoinGuild joinGuild = battleGuilds.get(guildId);
					YQZZGuild nation = new YQZZGuild();
					YQZZ_CAMP camp = this.getParent().yqzzCamp(i+1);
					nation.setServerId(joinGuild.getServerId());
					nation.setGuildId(guildId);
					nation.setCamp(camp);
					nation.setNationLevel(joinGuild.getNationLevel());
					guildCamp.put(guildId, nation);
				}
				boolean rlt = YQZZRoomManager.getInstance().creatNewBattle(timeCfg.getBattleTimeValue(),
						timeCfg.getRewardTimeValue(), param);
				if(rlt){
					YQZZGameData gameData = new YQZZGameData();
					gameData.setTermId(room.getTermId());
					gameData.setRoomId(room.getRoomId());
					gameData.setRoomServerId(room.getRoomServerId());
					gameData.addGuild(guilds);
					gameData.setLastActiveTime(HawkTime.getMillisecond());
					gameData.setFinishTime(0);
					gameData.saveRedis();
				}
			}
		}
	}
	
	
	/**
	 * 计算自由出入人员
	 */
	public void calFreeJoinPlayers(){
		String serverId = GsConfig.getInstance().getServerId();
		YQZZWarConstCfg cfg = HawkConfigManager.getInstance().getKVInstance(YQZZWarConstCfg.class);
		int size = cfg.getPlayerJoinFreeCount();
		
		Map<String, YQZZJoinGuild> battleGuilds = this.getDataManager().getBattleGuilds();
		for(YQZZJoinGuild guild : battleGuilds.values()){
			if(!serverId.equals(guild.getServerId())){
				continue;
			}
			Map<String,Integer> prank = this.getGuildMemberByPower(size, guild.getGuildId());
			if(Objects.isNull(prank)){
				continue;
			}
			guild.setFreePlayers(prank);
			guild.saveRedis();
		}
		
	}
	
	
	/**
	 * 获取联盟成员战斗力前X
	 * @param size
	 * @param guildId
	 * @return
	 */
	public Map<String,Integer> getGuildMemberByPower(int size,String guildId){
		//联盟对象
		GuildInfoObject guildObj = GuildService.getInstance().getGuildInfoObject(guildId);
		if(Objects.isNull(guildObj)){
			return null;
		}
		Collection<String> midCol = GuildService.getInstance().getGuildMembers(guildId);
		if(Objects.isNull(midCol)){
			return null;
		}
		try {
			//排序
			Map<String,Integer> frees = new HashMap<>();
			List<HawkTuple2<String, Long>> memberPowers = new ArrayList<>();
			for(String mid : midCol){
				GuildMemberObject member = GuildService.getInstance().getGuildMemberObject(mid);
				if(Objects.nonNull(member)){
					memberPowers.add(HawkTuples.tuple(member.getPlayerId(), member.getPower()));
				}
			}
			Collections.sort(memberPowers, new Comparator<HawkTuple2<String, Long>>() {
				@Override
				public int compare(HawkTuple2<String, Long> o1, HawkTuple2<String, Long> o2) {
					if(o1.second != o2.second){
						return o1.second > o2.second?-1:1;
					}else{
						return o1.first.compareTo(o2.first);
					}
				}
			});
			
			for(int i=0;i<size;i++){
				if(i >= memberPowers.size()){
					break;
				}
				HawkTuple2<String, Long> power = memberPowers.get(i);
				frees.put(power.first, i+1);
			}
			return frees;
		} catch (Exception e) {
			HawkException.catchException(e);
			return null;
		}
		
	}


}
