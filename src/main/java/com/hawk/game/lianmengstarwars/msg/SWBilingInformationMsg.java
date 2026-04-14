package com.hawk.game.lianmengstarwars.msg;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hawk.msg.HawkMsg;
import org.hawk.os.HawkOSOperator;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Objects;
import com.googlecode.protobuf.format.JsonFormat;
import com.googlecode.protobuf.format.JsonFormat.ParseException;
import com.hawk.game.config.StarWarsConstCfg;
import com.hawk.game.lianmengstarwars.SWConst.SWOverType;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.SW.PBSWGameInfoSync;
import com.hawk.game.protocol.SW.PBSWGuildInfo;
import com.hawk.game.protocol.SW.PBSWPlayerInfo;
import com.hawk.game.service.starwars.StarWarsConst.SWWarType;
import com.hawk.serialize.string.SerializeHelper;

import org.hawk.tuple.HawkTuple3;

/***
 * 结算信息
 */
public class SWBilingInformationMsg extends HawkMsg implements SerializJsonStrAble {
	private SWOverType overType;
	private List<String> winGuild;
	private String roomId;
	private SWWarType warType;
	private PBSWGameInfoSync lastSyncpb;

	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		obj.put("overType", overType);
		obj.put("winGuild", SerializeHelper.collectionToString(this.winGuild, SerializeHelper.ELEMENT_DELIMITER));
		obj.put("roomId", roomId);
		obj.put("warType", warType);
		obj.put("lastSyncpb", JsonFormat.printToString(lastSyncpb));
		return obj.toJSONString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		JSONObject obj = JSONObject.parseObject(serialiedStr);
		overType = SWOverType.valueOf(obj.getString("overType"));
		winGuild = SerializeHelper.stringToList(String.class, obj.getString("winGuild"));
		roomId = obj.getString("roomId");
		warType = SWWarType.valueOf(obj.getString("warType"));
		try {
			PBSWGameInfoSync.Builder lastSyncpbBul = PBSWGameInfoSync.newBuilder();
			JsonFormat.merge(obj.getString("lastSyncpb"), lastSyncpbBul);
			lastSyncpb = lastSyncpbBul.build();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public SWOverType getOverType() {
		return overType;
	}

	public void setOverType(SWOverType overType) {
		this.overType = overType;
	}

	public List<String> getWinGuild() {
		return winGuild;
	}

	public void setWinGuild(List<String> winGuild) {
		this.winGuild = winGuild;
	}

	public String getRoomId() {
		return roomId;
	}

	public void setRoomId(String roomId) {
		this.roomId = roomId;
	}

	public SWWarType getWarType() {
		return warType;
	}

	public void setWarType(SWWarType warType) {
		this.warType = warType;
	}

	public PBSWGameInfoSync getLastSyncpb() {
		return lastSyncpb;
	}

	public void setLastSyncpb(PBSWGameInfoSync lastSyncpb) {
		this.lastSyncpb = lastSyncpb;
	}

	/**
	 * 联盟总分
	 */
	public long getGuildHonor(String guildid) {
		List<PBSWGuildInfo> guildList = lastSyncpb.getGuildInfoList();
		for (PBSWGuildInfo ginfo : guildList) {
			if (Objects.equal(guildid, ginfo.getGuildId())) {
				return ginfo.getHonor();
			}
		}
		return 0;
	}

	public HawkTuple3<Integer, Integer, Integer> getKillHonor(String playerId) {
		if (lastSyncpb == null) {
			return new HawkTuple3<>(0,0,0);
		}
		for (PBSWPlayerInfo pinfo : lastSyncpb.getPlayerInfoList()) {
			if (Objects.equal(pinfo.getPlayerId(), playerId)) {
				return new HawkTuple3<>(pinfo.getKillHonor(), pinfo.getKillPower(), pinfo.getDeadPower());
			}
		}
		return new HawkTuple3<>(0,0,0);
	}

	
	public List<String> calUpGuilds(String winner){
		StarWarsConstCfg starWarsConstCfg = StarWarsConstCfg.getInstance();
		int upCnt = starWarsConstCfg.getUpGuildCnt();
		List<String> upGuilds = new ArrayList<>();
		if(!HawkOSOperator.isEmptyString(winner)){
			upGuilds.add(winner);
		}
		
		List<PBSWGuildInfo> guilds = this.lastSyncpb.getGuildInfoList();
		List<PBSWGuildInfo> fightGuilds = new ArrayList<>();
		fightGuilds.addAll(guilds);
		Collections.sort(fightGuilds,new Comparator<PBSWGuildInfo>() {
			@Override
			public int compare(PBSWGuildInfo o1, PBSWGuildInfo o2) {
				if(o1.getHonorRank() != o2.getHonorRank()){
					return o1.getHonorRank() > o2.getHonorRank()? 1:-1;
				}
				return o1.getGuildId().compareTo(o2.getGuildId());
			}
		});
		
		for(PBSWGuildInfo guild : fightGuilds){
			if(upGuilds.size() >= upCnt){
				break;
			}
			if(upGuilds.contains(guild.getGuildId())){
				continue;
			}
			upGuilds.add(guild.getGuildId());
		}
		return upGuilds;
	}
}
