package com.hawk.game.script;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hawk.app.HawkAppCfg;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import org.hawk.os.HawkRand;
import org.hawk.os.HawkTime;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.thread.HawkThread;
import org.hawk.uuid.HawkUUIDGenerator;

import com.hawk.game.GsConfig;
import com.hawk.game.entity.PlayerGiftEntity;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.module.lianmengyqzz.march.cfg.YQZZTimeCfg;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZJoinGuild;
import com.hawk.game.module.lianmengyqzz.march.data.global.YQZZMatchRoomData;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZMatchService;
import com.hawk.game.module.lianmengyqzz.march.service.season.YQZZSeasonServer;
import com.hawk.game.module.lianmengyqzz.march.service.state.YQZZState200Match;
import com.hawk.game.protocol.MailConst;
import com.hawk.game.service.mail.MailParames;
import com.hawk.game.service.mail.SystemMailService;

public class YQZZGMHandler extends HawkScript {
    private String outputInfo = "";
    @Override
    public String action(Map<String, String> map, HawkScriptHttpInfo hawkScriptHttpInfo) {
        if (!GsConfig.getInstance().isDebug()) {
            return "不是测试环境";
        }
//        String host = hawkScriptHttpInfo.getRequest().getRemoteHost();
//        int port = GsConfig.getInstance().getGmPort();
//        String URL = "http://"+host + ":"+port+"/script/yqzzgm";
        try {
            String opt = map.getOrDefault("opt", "");
            switch (opt){
                case "start":{
                    String termId = map.get("termId");
                    activityStart(Integer.parseInt(termId));
                }
                break;
                case "next":{
                    activityNext();
                }
                break;
                case "clear":{
                    YQZZTimeCfg.setGmTime(0);
                }
                break;
                case "clean":{
                    String termId = map.get("termId");
                    RedisProxy.getInstance().getRedisSession().del("YQZZ_ACTIVITY_ROOM_DATA"+":"+termId);
                    RedisProxy.getInstance().getRedisSession().del("YQZZ_ACTIVITY_JOIN_SERVER"+":"+termId);
                    RedisProxy.getInstance().getRedisSession().del("YQZZ_ACTIVITY_JOIN_SERVER"+":"+termId);
                    RedisProxy.getInstance().getRedisSession().del("YQZZ_ACTIVITY_MATCH_LOCK"+":"+termId);
                }
                break;
                case "sendMail":{
                    int mailId = Integer.parseInt(map.get("mailId"));
                    String params = map.get("param");
                    MailParames.Builder mailParames = MailParames.newBuilder();
                    mailParames.setMailId(MailConst.MailId.valueOf(mailId));
                    if(params != null){
                        for(String param : params.split(",")){
                            mailParames.addContents(param);
                        }
                    }
                    long currTime = HawkTime.getMillisecond();
                    long experiTime = currTime + HawkTime.DAY_MILLI_SECONDS * 7;
                    SystemMailService.getInstance().addGlobalMail(mailParames.build(), currTime, currTime + experiTime);
                }
                break;
                case "kickout":{
                    YQZZSeasonServer server = YQZZSeasonServer.loadByServerId(Integer.parseInt(map.get("season")), map.get("serverId"));
                    server.setAdvance(false);
                    server.saveRedis();
                }
                break;
                case "testMatch":{
                    testMatch();
                }
                break;
                case "gmAction":{
                	 YQZZMatchService.getInstance().getState().gmOp();
                }
                break;
                default:{

                }
            }
            return pageInfo();
        }catch (Exception e){
        	HawkException.catchException(e);
            outputLog(e.getMessage());
            return pageInfo();
        }finally {
            outputInfo = "";
        }

    }

    public void activityStart(int termId){
        YQZZConst.YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
        if(state != YQZZConst.YQZZActivityState.HIDDEN){
            outputLog("当前正在活动期间");
            return;
        }

        YQZZTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
        if(cfg == null) {
            outputLog("设置期数不存在，termId:"+termId);
        }
        long now = HawkTime.getMillisecond();
        long showTime = HawkTime.parseTime(cfg.getShowTime());
        long gmTime = now - showTime;
        YQZZTimeCfg.setGmTime(gmTime);
        outputLog("执行成功");
    }
    public void activityNext(){
        YQZZConst.YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
        if(state == YQZZConst.YQZZActivityState.HIDDEN){
            int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId() + 1;
            YQZZTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
            if(cfg == null) {
                outputLog("设置期数不存在，termId:"+termId);
            }
            long now = HawkTime.getMillisecond();
            long showTime = HawkTime.parseTime(cfg.getShowTime());
            long gmTime = now - showTime;
            YQZZTimeCfg.setGmTime(gmTime);
            outputLog("执行成功");
            return;
        }
        int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
        YQZZTimeCfg cfg = HawkConfigManager.getInstance().getConfigByKey(YQZZTimeCfg.class, termId);
        if(cfg == null) {
            outputLog("配置不存在，termId:"+termId);
            return;
        }
        YQZZMatchService.getInstance().getState().gmOp();
        long now = HawkTime.getMillisecond();
        long toTime = 0;
        switch (state){
            case START_SHOW:{
                toTime = HawkTime.parseTime(cfg.getMatchTime());
            }
            break;
            case MATCH:{
                toTime = HawkTime.parseTime(cfg.getBattleTime());
            }
            break;
            case BATTLE:{
                toTime = HawkTime.parseTime(cfg.getRewardTime());
            }
            break;
            case REWARD:{
                toTime = HawkTime.parseTime(cfg.getEndShowTime());
            }
            break;
            case END_SHOW:{
                toTime = HawkTime.parseTime(cfg.getHiddenTime());
            }
            break;
        }
        toTime -= 10000;
        long gmTime = now - toTime;
        YQZZTimeCfg.setGmTime(gmTime);
    }



    public String activityInfo(){
        int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
        YQZZConst.YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
        YQZZConst.YQZZActivityJoinState joinState = YQZZMatchService.getInstance().getDataManger().getStateData().getJoinGame();
        //YQZZSeasonStateData seasonStateData = YQZZMatchService.getInstance().getDataManger().getSeasonStateData();
        return "<p>服务器id:"+GsConfig.getInstance().getServerId()+"</p>" +
                "<p>活动状态:"+state.name()+"</p>" +
                "<p>活动期数:"+termId+"</p>" +
                "<p>参与状态:"+joinState.name()+"</p>";
                //"<p>赛季期数:"+seasonStateData.getSeason()+"</p>"+
                //"<p>赛季状态:"+seasonStateData.getState().name()+"</p>";
    }

    public void outputLog(String msg){
        this.outputInfo = (msg==null?"":msg);
    }

    public String outputInfo(){
        if(outputInfo.equals("")){
            return outputInfo;
        }else {
            return "<p>执行结果:</p>"+"<p>"+ outputInfo +"</p>";
        }
    }

    public String joinGuildInfo(){
        YQZZConst.YQZZActivityState state = YQZZMatchService.getInstance().getDataManger().getStateData().getState();
        if(state == YQZZConst.YQZZActivityState.HIDDEN){
            return "<p>服务器状态:</p>";
        }
        int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
        Map<String, YQZZMatchRoomData> dataMap = YQZZMatchRoomData.loadAllData(termId);
        Map<String,YQZZJoinGuild> map = YQZZJoinGuild.loadAllData(termId);
        String info = "";
        if(dataMap != null && dataMap.size()>0){
            for(YQZZMatchRoomData roomData : dataMap.values()){
                for(String guildId : roomData.getGuilds()){
                	YQZZJoinGuild joinGuild = map.get(guildId);
                    info = info + genGuildInfo(joinGuild, roomData);
                }
            }
        }

        return "<p>联盟信息:</p>" +
               "<table border=\"1\" width=\"1200px\">\n" +
               "<tr>\n" +
               "<td>id</td>\n" +
               "<td>名称</td>\n" +
               "<td>战力</td>\n" +
                "<td>leaderId</td>\n" +
                "<td>leaderName</td>\n" +
                "<td>玩家数</td>\n" +
                "<td>战场服</td>\n" +
                "<td>分数</td>\n" +
                "<td>是否晋级</td>\n" +
               "</tr>\n" +
                info+
               "</table>";
    }

    public  String genGuildInfo(YQZZJoinGuild joinServer, YQZZMatchRoomData roomData){
        return "<tr>\n" +
                "<td>"+joinServer.getServerId()+"</td>\n" +
                "<td>"+joinServer.getGuildName()+"</td>\n" +
                "<td>"+joinServer.getPower()+"</td>\n" +
                "<td>"+joinServer.getLeaderId()+"</td>\n" +
                "<td>"+joinServer.getLeaderName()+"</td>\n" +
                "<td>"+joinServer.getFreePlayers().size()+"</td>\n" +
                "<td>"+(roomData == null ? "0":roomData.getRoomServerId())+"</td>\n" +
               "</tr>\n";
    }
    public String pageInfo(){
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head> \n" +
                "    <meta charset=\"utf-8\"> \n" +
                "    <title>月球之战GM</title> \n" +
                "    <style>\n" +
                "        #main{ margin:0 auto; width:320px; height:100px;}\n" +
                "    </style>\n" +
                "</head> \n" +
                "<body>\n" +
                "    <div>\n" +
                "        <h1>月球之战GM</h1>\n" +
                "        <button onclick=\"flush();\">刷新</button>\n" +
                "        <button onclick=\"changeState('clean');\">清除当期数据</button>\n" +
                "        <button onclick=\"changeState('start');\">开始活动</button>\n" +
                "        <button onclick=\"changeState('next');\">下一阶段</button>\n" +
                "        <button onclick=\"changeState('clear');\">解除GM控制</button>\n" +
                "        <button onclick=\"changeState('testMatch');\">匹配测试</button>\n" +
                "        </br></br><label>设置期数:</label >\n" +
                "        <input type=\"text\" id=\"termId\" onkeyup=\"value=value.replace(/[^\\d]/g,'')\">\n" +
                activityInfo()+
                joinGuildInfo()+
                outputInfo()+
                "    </div>\n" +
                "</body>\n" +
                "<script type=\"text/javascript\">\n" +
                "    function getQueryVariable(variable)\n" +
                "    {\n" +
                "        var query = window.location.search.substring(1);\n" +
                "        var vars = query.split(\"&\");\n" +
                "        for (var i=0;i<vars.length;i++) {\n" +
                "            var pair = vars[i].split(\"=\");\n" +
                "            if(pair[0] == variable){\n" +
                "                return pair[1];\n" +
                "            }\n" +
                "        }\n" +
                "        return(false);\n" +
                "    }\n" +
                "    var tmp = getQueryVariable(\"termId\");\n" +
                "    const term = document.getElementById(\"termId\");\n" +
                "    console.log(tmp);\n" +
                "    if(tmp == false){\n" +
                "        term.value = 0;\n" +
                "    }else{\n" +
                "        term.value = tmp;\n" +
                "    }   \n" +
                "    function changeState(opt) {\n" +
                "        const term = document.getElementById(\"termId\");\n" +
                "        var id=term.value;\n" +
                "        var origin = window.location.origin\n" +
                "        window.location.href = origin + \"/script/yqzzgm?opt=\"+opt+\"&termId=\"+id;\n" +
                "    }\n" +
                "   function flush() {\n" +
                "        var origin = window.location.origin\n" +
                "        window.location.href = origin + \"/script/yqzzgm\";\n" +
                "    }\n" +
                "</script>\n" +
                "</html>";
    }

    public String testMatch(){
    	
    	
    	List<YQZZTimeCfg> tlist = HawkConfigManager.getInstance().getConfigIterator(YQZZTimeCfg.class).toList();
    	
    	//准备测试数据
    	int testTermId = tlist.get(tlist.size()-1).getTermId();
    	int serverCnt = HawkRand.randInt(50,150);
    	int guildCnt = 5;
    	
    	//清掉数据
    	String gkey = "YQZZ_ACTIVITY_JOIN_GUILD:" + testTermId;
    	RedisProxy.getInstance().getRedisSession().del(gkey);
    	
    	String rkey = "YQZZ_ACTIVITY_ROOM_DATA:" + testTermId;
    	RedisProxy.getInstance().getRedisSession().del(rkey);
    	
    	
    	
    	Map<String,YQZZJoinGuild> guilds = new HashMap<>();
    	for(int i =1;i<= serverCnt;i++){
    		String serverId =String.valueOf(100000+i);
    		long powerbase = HawkRand.randInt(10000000, 15000000);
    		int openDays = HawkRand.randInt(1, 500);
    		
    		for(int g=1;g<=guildCnt;g++){
    			int add = HawkRand.randInt(1000000, 2000000);
    			powerbase -= add;
    			String guildId = serverId+"#G"+g;
    			
    			YQZZJoinGuild joinGuild = new YQZZJoinGuild();
				joinGuild.setTermId(testTermId);
				joinGuild.setServerId(serverId);
				joinGuild.setGuildId(guildId);
				joinGuild.setGuildName(serverId);
				joinGuild.setGuildTag("s");
				joinGuild.setGuildFlag(1);
				joinGuild.setLeaderId("ss");
				joinGuild.setLeaderName("ss");
				joinGuild.setGuildRank(g);
				joinGuild.setPower(powerbase);
				joinGuild.setOpenDays(openDays);
    			
				guilds.put(joinGuild.getGuildId(), joinGuild);
				
    		}
    		
    	}
    	
    	YQZZJoinGuild.saveAllData(testTermId, guilds);
    	
    	
    	
    	int threadIndex = YQZZMatchService.getInstance().getXid().getHashThread(GsConfig.getInstance().getThreadNum());
    	HawkTaskManager.getInstance().postTask(new HawkTask() {
			@Override
			public Object run() {
				int termId = YQZZMatchService.getInstance().getDataManger().getStateData().getTermId();
				YQZZMatchService.getInstance().getDataManger().getStateData().setTermId(testTermId);
				YQZZState200Match state = new YQZZState200Match(YQZZMatchService.getInstance());
				state.doNormalMatch();
				YQZZMatchService.getInstance().getDataManger().getStateData().setTermId(termId);
				return null;
			}
    	}, threadIndex);
    	
    	try {
			HawkThread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
    	
    	YQZZState200Match state = new YQZZState200Match(YQZZMatchService.getInstance());
    	Map<Integer, List<YQZZJoinGuild>> guildDayMap = new HashMap<>();
    	
    	for (YQZZJoinGuild guildData : guilds.values()){
        	int openDayW = state.getOpenDayW(guildData.getOpenDays());
            if(!guildDayMap.containsKey(openDayW)){
            	guildDayMap.put(openDayW, new ArrayList<>());
            }
            guildDayMap.get(openDayW).add(guildData);
        }
        
        List<Integer> dayList = new ArrayList<>(guildDayMap.keySet());
        dayList.sort((o1, o2) -> o2 - o1);
        
       
        StringBuilder info = new StringBuilder();
    	info.append("</br>**************************************联盟数据***********************************************");
    	for(int day : dayList){
    		List<YQZZJoinGuild> matchGuildList = new ArrayList<>();
	    	matchGuildList.addAll(guildDayMap.get(day));
	    	Collections.sort(matchGuildList,new Comparator<YQZZJoinGuild>() {
				@Override
				public int compare(YQZZJoinGuild o1, YQZZJoinGuild o2) {
					if(o1.getOpenDays() != o2.getOpenDays()){
						return o1.getOpenDays() > o2.getOpenDays()?-1 :1;
					}
					if(o1.getPower() != o2.getPower()){
						return o1.getPower() > o2.getPower()?-1 :1;
					}
					return o1.getGuildId().compareTo(o2.getGuildId());
				}
			});
	       
	    	for(YQZZJoinGuild guild : matchGuildList){
	    		info.append("</br>").append("服务器ID").append(guild.getServerId()).append(",联盟ID:").append(guild.getGuildId()).append(",实力:")
				.append(guild.getPower()).append(",开服天数:").append(guild.getOpenDays()).append(",排行:").append(guild.getGuildRank());
	    	}
    	}
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	
    	Map<String,Integer> ver1 = new HashMap<>();
    	Map<String,Integer> ver2 = new HashMap<>();
    	
    	Map<String,YQZZMatchRoomData> map = YQZZMatchRoomData.loadAllData(testTermId);
    	List<YQZZMatchRoomData> rlist = new ArrayList<>(); 
    	rlist.addAll(map.values());
    	rlist.sort((o1, o2) -> HawkUUIDGenerator.strUUID2Long(o1.getRoomId()) - HawkUUIDGenerator.strUUID2Long(o2.getRoomId())>0?1:-1);
    	
    	
    	info.append("**************************************匹配数据***********************************************");
    	
    	for(YQZZMatchRoomData data : rlist){
    		info.append("</br>");
    		info.append(data.getRoomId()).append(">>");
    		for(String gid : data.getGuilds()){
    			info.append("{").append(gid).append("}");
    			
    			int  cnt1 = ver1.getOrDefault(gid, 0);
    			ver1.put(gid, cnt1 +1);
    		}
    		
    		int  cnt2 = ver2.getOrDefault(data.getRoomServerId(), 0);
			ver1.put(data.getRoomServerId(), cnt2 +1);
    	}
    	
    	
    	info.append("</br>**************************************检测数据-匹配联盟重复检查***********************************************");
    	for(String key : ver1.keySet()){
    		int val = ver1.get(key);
    		if(val >= 2){
    			info.append("</br>");
    			info.append(key).append(">").append(val);
    		}
    	}
    	
    	info.append("</br>**************************************检测数据-战场服重复检查***********************************************");
    	for(String key : ver2.keySet()){
    		int val = ver2.get(key);
    		if(val >= 2){
    			info.append("</br>");
    			info.append(key).append(">").append(val);
    		}
    	}
    	
    	this.outputInfo =  info.toString();
    	return null;
    }



}
