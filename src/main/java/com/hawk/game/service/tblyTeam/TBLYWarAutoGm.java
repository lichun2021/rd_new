package com.hawk.game.service.tblyTeam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.thread.HawkThread;

import com.hawk.game.GsConfig;
import com.hawk.game.config.TiberiumConstCfg;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.module.lianmengtaiboliya.TBLYBattleRoom;
import com.hawk.game.module.lianmengtaiboliya.TBLYRoomManager;
import com.hawk.game.module.lianmengtaiboliya.roomstate.TBLYGameOver;
import com.hawk.game.service.GuildService;
import com.hawk.game.service.tblyTeam.state.TBLYWarStateEnum;

public class TBLYWarAutoGm {
	
	//更新时间间隔(10S 最少)
	long updateTimeInt = 10 * 1200;
	
	public String followMain(){
		long curTime = HawkTime.getMillisecond();
		int season = TBLYSeasonService.getInstance().getSeason();
		String targetKey = "TBLY_SEASON_AUTO_GM_UJDIFU_"+season;
		Map<String,String> param = RedisProxy.getInstance().getRedisSession().hGetAll(targetKey);
		//更新时间
		long nextTime = Long.parseLong(param.get("nextTime"));
		//开始时候如果距离
		long lastTime = nextTime - curTime;
		
		if(lastTime <= 0 || lastTime >  nextTime / 3 ){
			return "时间不太够用了，跟不上了！！！";
		}
		
		String serverId = GsConfig.getInstance().getServerId();
		String joinTargetKey = "TBLY_SEASON_AUTO_GM_SERVER_JOIN_INFO_"+season;
		RedisProxy.getInstance().getRedisSession().hSet(joinTargetKey, serverId, String.valueOf("222"),60 * 1000);
		return null;
	}
	
	
	
	
	public String start(int termId,boolean all){
		long curTime = HawkTime.getMillisecond();
		int season = TBLYSeasonService.getInstance().getSeason();
		String targetKey = "TBLY_SEASON_AUTO_GM_UJDIFU_"+season;
		RedisProxy.getInstance().getRedisSession().del(targetKey);
		
		//更新时间
		long nextTime = curTime + this.updateTimeInt;
		Map<String,String> param = new HashMap<>();
		param.put("nextTime", String.valueOf(nextTime));
		if(all){
			param.put("all", String.valueOf(1));
		}else{
			param.put("targetTermId", String.valueOf(termId));
		}
		RedisProxy.getInstance().getRedisSession().hmSet(targetKey, param, 60 * 1000);
		
		String serverId = GsConfig.getInstance().getServerId();
		String joinTargetKey = "TBLY_SEASON_AUTO_GM_SERVER_JOIN_INFO_"+season;
		RedisProxy.getInstance().getRedisSession().hSet(joinTargetKey, serverId, String.valueOf("222"),60 * 1000);
		
		return "OK";
	}
	
	
	public void tick(){
		if (!GsConfig.getInstance().isDebug()) {
           return;
        }
		long curTime = HawkTime.getMillisecond();
		int season = TBLYSeasonService.getInstance().getSeason();
		String targetKey = "TBLY_SEASON_AUTO_GM_UJDIFU_"+season;
		Map<String,String> param = RedisProxy.getInstance().getRedisSession().hGetAll(targetKey);
		
		//期数
		int targetTermId = 0;
		//大状态
		TBLYWarStateEnum targetBigState = null;
		//主状态
		TBLYWarStateEnum targetMainState = null;
		
		boolean all = false;
		if(param.containsKey("all")){
			targetTermId = TBLYSeasonService.getInstance().getFinalTimeCfg().getTermId();
			targetBigState = TBLYWarStateEnum.SEASON_BIG_END_SHOW;
			targetMainState = TBLYWarStateEnum.SEASON_NOT_OPEN;
			all =true;
		}else{
			targetTermId = Integer.parseInt(param.get("targetTermId"));
			if(targetTermId >= TiberiumConstCfg.getInstance().getEliminationStartTermId()){
				targetBigState = TBLYWarStateEnum.SEASON_BIG_GROUP;
	        }
	        if(targetTermId>= TiberiumConstCfg.getInstance().getEliminationFinalTermId()){
	        	targetBigState = TBLYWarStateEnum.SEASON_BIG_FINAL;
	        }
	        targetMainState = TBLYWarStateEnum.SEASON_PEACE;
		}
		
		TBLYWarStateEnum curBigState = TBLYSeasonService.getInstance().getBigState();
		//主状态
		TBLYWarStateEnum curMainState = TBLYSeasonService.getInstance().getState();
		
		//战场只能存在5S 就关闭
		if(curMainState == TBLYWarStateEnum.SEASON_WAR_OPEN){
            List<TBLYBattleRoom> rlist = TBLYRoomManager.getInstance().findAllRoom();
            for(TBLYBattleRoom room : rlist){
            	if(room.getState() instanceof TBLYGameOver){
            		continue;
            	}
            	if(curTime > (room.getCreateTime()+ 3 *1000)){
            		room.setState(new TBLYGameOver(room));
            	}
            }
		}
		//更新时间
		long nextTime = Long.parseLong(param.get("nextTime"));
		if(curTime < nextTime){
			return;
		}
		
		int curTerm = TBLYSeasonService.getInstance().getTermId();
		//如果不需要 
		if(!all && curTerm >= targetTermId){
			return;
		}
		if(targetTermId >  TBLYSeasonService.getInstance().getFinalTimeCfg().getTermId()){
			return;
		}
		if(curTerm != targetTermId || curBigState!= targetBigState || targetMainState!= curMainState){
			String serverId = GsConfig.getInstance().getServerId();
			String actionKey = "TBLY_SEASON_AUTO_GM_SERVER_ACTION_"+season+"_"+nextTime;
			String val = RedisProxy.getInstance().getRedisSession().hGet(actionKey, serverId);
			if(HawkOSOperator.isEmptyString(val)){
				RedisProxy.getInstance().getRedisSession().hSet(actionKey, serverId, "dd",60 * 1000);
				Map<String,String> cmdMap = new HashMap<>();
				cmdMap.put("cmd", "next");
				TBLYSeasonService.getInstance().gm(cmdMap);
			}
			//检查是否都转换了状态  然后进行下一次的配置
			long cnt1 = RedisProxy.getInstance().getRedisSession().hLen(actionKey, false);
			String joinTargetKey = "TBLY_SEASON_AUTO_GM_SERVER_JOIN_INFO_"+season;
			long cnt2 = RedisProxy.getInstance().getRedisSession().hLen(joinTargetKey, false);
			if(cnt1 == cnt2){
				//设置下一次的 更新时间
				param.put("nextTime", String.valueOf(curTime + updateTimeInt));
				RedisProxy.getInstance().getRedisSession().hmSet(targetKey, param, 60 * 1000);
			}
			
		}
		
		
		
		
	}

	
	
	
	
	public static String addHTML(String ipAddress,int port){
		String info = "";
		info +="-----------------------------自动跑起来--------------------------------------------------------------------------------------- <br/>";
		info +=">>>本服联盟数量:"+GuildService.getInstance().getGuildCount();
		info +="<br>>>>本服玩家数量:"+GlobalData.getInstance().getAllPlayerIds().size();
		
		
		info +="<br>";
		info +="<br>";
		info +="<br>>>>需求联盟数量:"+TiberiumConstCfg.getInstance().getGuildPickCnt();
		
		
		
		info +="<br>";
		info +="<br>";
		info +="<br>";
		if(TBLYSeasonService.getInstance().inAutoGm()){
			info +="<a href=\"http://"+ipAddress+":"+port+"/script/whcgm?opt=TBLYSEASONGM&cmd=autoRunStop\">>>急停<<<</a>           ";
		}else{
			if(TBLYSeasonService.getInstance().getBigState() != TBLYWarStateEnum.SEASON_BIG_NOT_OPEN){
	            int termCnt1 = TiberiumConstCfg.getInstance().getEliminationStartTermId();
	            int termCnt2 = TiberiumConstCfg.getInstance().getEliminationFinalTermId();
	            boolean s1 = false;
	            boolean s2 = false;
	            for(int i=1;i<=termCnt2;i++){
	            	if(i<termCnt1){
	            		if(!s1){
	            			info +="<br>";
	            			s1 = true;
	            		}
	            		 info +="<a href=\"http://"+ipAddress+":"+port+"/script/whcgm?opt=TBLYSEASONGM&cmd=autoRunMain&targetTermId="+i+"\">小组赛("+i+"期)</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;      ";
	            	}else{
	            		if(!s2){
	            			info +="<br>";
	            			s2 = true;
	            		}
	            		if(i==termCnt2){
	            			info +="<a href=\"http://"+ipAddress+":"+port+"/script/whcgm?opt=TBLYSEASONGM&cmd=autoRunMain&targetTermId="+i+"\">淘汰赛("+i+"期-决赛)</a> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;           ";
	            		}else{
	            			info +="<a href=\"http://"+ipAddress+":"+port+"/script/whcgm?opt=TBLYSEASONGM&cmd=autoRunMain&targetTermId="+i+"\">淘汰赛("+i+"期)</a>   &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;         ";
	            		}
	            	}
	            }
	            info +="<br/>";
	            info +="<a href=\"http://"+ipAddress+":"+port+"/script/whcgm?opt=TBLYSEASONGM&cmd=autoRunMain&all=1\">直到最后</a>           ";
	            info +="<br/>";
	            info +="<br/>";
	            info +="<a href=\"http://"+ipAddress+":"+port+"/script/whcgm?opt=TBLYSEASONGM&cmd=autoRunFollow\">跟随主机</a>           ";
	            
	        }else{
	        	return "";
	        }
			
		}
		
		info +="<br/>";
		info +="---------------------------------------------------------------------------------------------------------------------------------- ";
        return info;
	}
}
