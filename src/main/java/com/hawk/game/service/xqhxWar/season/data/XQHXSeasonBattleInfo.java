package com.hawk.game.service.xqhxWar.season.data;

import com.alibaba.fastjson.JSON;
import com.hawk.game.service.guildTeam.model.GuildTeamData;
import com.hawk.game.service.xqhxWar.XQHXWarRoomData;
import org.hawk.os.HawkOSOperator;

public class XQHXSeasonBattleInfo {
    public String roomId;
    public int termId;
    public int timeIndex;
    public String teamIdA;
    public String teamNameA;
    public String guildNameA;
    public String guildTagA;
    public String serverIdA;
    public String teamIdB;
    public String teamNameB;
    public String guildNameB;
    public String guildTagB;
    public String serverIdB;
    public String winnerId;
    public int winCount;
    public boolean isNew;
    public int group;

    public XQHXSeasonBattleInfo(){

    }

    public XQHXSeasonBattleInfo(GuildTeamData teamData1, GuildTeamData teamData2, XQHXWarRoomData roomData) {
        this.roomId = roomData.id;
        this.termId = roomData.termId;
        this.timeIndex = roomData.timeIndex;
        this.teamIdA = teamData1.id;
        this.teamNameA = teamData1.name;
        this.guildNameA = teamData1.guildName;
        this.guildTagA = teamData1.guildTag;
        this.serverIdA = teamData1.serverId;
        this.teamIdB = teamData2.id;
        this.teamNameB = teamData2.name;
        this.guildNameB = teamData2.guildName;
        this.guildTagB = teamData2.guildTag;
        this.serverIdB = teamData2.serverId;
        this.winnerId = roomData.winnerId;
        this.winCount = roomData.winCount;
        this.isNew = roomData.isNew;
        this.group = roomData.group;
    }

    public String serialize() {
        return JSON.toJSONString(this);
    }

    public static XQHXSeasonBattleInfo unSerialize(String json) {
        if (HawkOSOperator.isEmptyString(json)) {
            return null;
        }
        return JSON.parseObject(json, XQHXSeasonBattleInfo.class);
    }
}
