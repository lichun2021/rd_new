package com.hawk.game.service.xqhxWar.season.state;


import com.alibaba.fastjson.JSONObject;
import com.hawk.game.GsConfig;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.service.xqhxWar.season.XQHXSeasonManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
/**
 * 先驱回响赛季状态数据
 *
 * @author sjs
 */
public class XQHXSeasonStateData {
    private int season;
    private XQHXSeasonManager manager;
    private XQHXSeasonStateEnum state = XQHXSeasonStateEnum.CLOSE;
    private boolean isNext = false;

    public void load(XQHXSeasonManager manager){
        this.manager = manager;
        String value = RedisProxy.getInstance().getRedisSession().hGet(manager.getStateKey() , GsConfig.getInstance().getServerId());
        if(HawkOSOperator.isEmptyString(value)){
            return;
        }
        JSONObject obj = JSONObject.parseObject(value);
        season = obj.getIntValue("season");
        state = XQHXSeasonStateEnum.valueOf(obj.getIntValue("state"));
    }

    public void update(){
        JSONObject obj = new JSONObject();
        obj.put("season", season);
        obj.put("state", state.getIndex());
        RedisProxy.getInstance().getRedisSession().hSet(manager.getStateKey(), GsConfig.getInstance().getServerId(), obj.toJSONString());
    }

    @Override
    public String toString() {
        return "XQHXSeasonStateData{" +
                "season=" + season +
                ", state=" + state +
                '}';
    }

    public void tick(){
        long now = HawkTime.getMillisecond();
        long endTime = getEndTime();
        if(now >= endTime){
            toNext();
        }
        if(isNext){
            try {
                next();
            }catch (Exception e){
                HawkException.catchException(e);
            }
            isNext = false;
        }
    }

    public void next(){
        leave();
        changeState();
        enter();
        update();
        HawkLog.logPrintln(toString());
        syncAllPLayer();
    }

    public void enter(){
        try {
            switch (state){
                case QUALIFIER:{
                    manager.enterQualifier();
                }
                case RANKING:{
                    manager.enterRanking();
                }
                break;
                case END_SHOW:{
                    manager.enterEndShow();
                }
                break;
                case CLOSE:{
                    manager.enterClose();
                }
                break;
            }
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void changeState(){
        try {
            switch (state){
                case CLOSE:{
                    state = XQHXSeasonStateEnum.QUALIFIER;
                }
                break;
                case QUALIFIER:{
                    state = XQHXSeasonStateEnum.RANKING;
                }
                break;
                case RANKING:{
                    state = XQHXSeasonStateEnum.END_SHOW;
                }
                break;
                case END_SHOW:{
                    state = XQHXSeasonStateEnum.CLOSE;
                }
            }
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void leave(){
        try {

        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    public void syncAllPLayer(){
        manager.syncAllPLayer();
    }

    public long getEndTime(){
        try {
            return manager.getEndTime();
        } catch (Exception e) {
            HawkException.catchException(e);
            return Long.MAX_VALUE;
        }
    }

    public void toNext(){
        isNext = true;
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public XQHXSeasonStateEnum getState() {
        return state;
    }
}
