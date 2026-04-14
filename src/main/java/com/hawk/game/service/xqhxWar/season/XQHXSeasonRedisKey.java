package com.hawk.game.service.xqhxWar.season;

public class XQHXSeasonRedisKey {
    public static final String XQHX_STATE = "%s:STATE";
    public static final String XQHX_LOCK = "%s:BIG_LOCK:%d";
    public static final String XQHX_DATA = "%s:DATA:%d";
    public static final String XQHX_PLAYER_DATA = "%s:PLAYER:DATA:%d";

    public static final String XQHX_RANK = "%s:RANK:%d";
    public static final String XQHX_JOIN = "%s:JOIN:%d";
    public static final String XQHX_BATTLE_QUALIFIER_SELF = "%s:BATTLE:QUALIFIER:%d:%s";
    public static final String XQHX_BATTLE_RANKING_SELF = "%s:BATTLE:RANKING:%d:%s";
    public static final String XQHX_BATTLE_GROUP = "%s:BATTLE:%d:%d:%d";

    public static final String XQHX_RANK_NEW = "%s:RANK:NEW:%d";
    public static final String XQHX_JOIN_NEW = "%s:JOIN:NEW:%d";
    public static final String XQHX_BATTLE_QUALIFIER_SELF_NEW = "%s:BATTLE:QUALIFIER:NEW:%d:%s";
    public static final String XQHX_BATTLE_RANKING_SELF_NEW = "%s:BATTLE:RANKING:NEW:%d:%s";
    public static final String XQHX_BATTLE_GROUP_NEW = "%s:BATTLE:NEW:%d:%d:%d";

}
