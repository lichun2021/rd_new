package com.hawk.game.service.xqhxWar.season.state;

public enum XQHXSeasonStateEnum {
    /**
     * 关闭
     */
    CLOSE(1),
    /**
     * 赛前报名
     */
    SIGNUP(2),
    /**
     * 入围赛
     */
    QUALIFIER(3),
    /**
     * 排名赛
     */
    RANKING(4),
    /**
     * 赛后
     */
    END_SHOW(5),
    ;
    private final int index;

    XQHXSeasonStateEnum(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static XQHXSeasonStateEnum valueOf(int index){
        switch (index){
            case 1:return CLOSE;
            case 2:return SIGNUP;
            case 3:return QUALIFIER;
            case 4:return RANKING;
            case 5:return END_SHOW;
            default:return CLOSE;
        }
    }
}
