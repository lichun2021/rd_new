package com.hawk.game.module.lianmengyqzz.battleroom.extra;

import com.google.common.base.MoreObjects;
import com.google.common.collect.HashBiMap;

public class YQZZExtraParam {
	private String battleId = "";

	private boolean debug;
	/** 服务器所在地块 1-6*/
	private HashBiMap<String, YQZZGuild> guildCamp = HashBiMap.create(6);

	@Override
	public String toString() {
		return MoreObjects.toStringHelper(this)
				.add("battleId", battleId)
				.add("serverCamp", guildCamp)
				.toString();
	}

	public String getBattleId() {
		return battleId;
	}

	public void setBattleId(String battleId) {
		this.battleId = battleId;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public HashBiMap<String, YQZZGuild> getGuildCamp() {
		return guildCamp;
	}

	public void setGuildCamp(HashBiMap<String, YQZZGuild> guildCamp) {
		this.guildCamp = guildCamp;
	}

}
