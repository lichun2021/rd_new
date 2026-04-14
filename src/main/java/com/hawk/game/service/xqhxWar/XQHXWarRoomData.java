package com.hawk.game.service.xqhxWar;

import com.alibaba.fastjson.JSON;
import com.hawk.game.service.guildTeam.model.GuildTeamData;
import org.hawk.os.HawkOSOperator;

public class XQHXWarRoomData {
    public String id;
    public String campA;
    public long scoreA;
    public String campB;
    public long scoreB;
    public String roomServerId = "";
    public int termId;
    public int timeIndex;
    public String winnerId = "";
    public int roomState;
    public int matchType;
    public int groupType;
    public int battleType;
    public int groupId;
    public int winCount;
    public boolean isNew;
    public int group;

    public XQHXWarRoomData(){

    }

    public XQHXWarRoomData(int termId, int timeIndex, GuildTeamData team1, GuildTeamData team2,String roomServer) {
        this.termId = termId;
        this.timeIndex = timeIndex;
        this.roomServerId = roomServer;
        if(roomServer.equals(team1.serverId)){
        	 this.campA = team1.id;
             this.campB = team2.id;
             this.id = team1.id + "_" + team2.id;
        }else{
        	 this.campA = team2.id;
             this.campB = team1.id;
             this.id = team2.id + "_" + team1.id;
        }
    }

    public String serialize() {
        return JSON.toJSONString(this);
    }

    public static XQHXWarRoomData unSerialize(String json) {
        if(HawkOSOperator.isEmptyString(json)){
            return null;
        }
        return JSON.parseObject(json, XQHXWarRoomData.class);
    }
}
