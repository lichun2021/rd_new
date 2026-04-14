package com.hawk.game.activity.util;

import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.client.api.ContentResponse;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.net.http.HawkHttpUrlService;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.task.HawkTaskManager;
import org.hawk.thread.HawkTask;
import org.hawk.tuple.HawkTuple2;
import org.hawk.tuple.HawkTuple3;

import com.alibaba.fastjson.JSONObject;
import com.hawk.activity.ActivityManager;
import com.hawk.activity.event.impl.HeavenBlessingActiveEvent;
import com.hawk.activity.type.impl.heavenBlessing.cfg.HeavenBlessingKVCfg;
import com.hawk.activity.type.impl.prestressingloss.cfg.PrestressingLossKVCfg;
import com.hawk.game.config.PayPuidCtrl;
import com.hawk.game.global.GlobalData;
import com.hawk.game.global.RedisProxy;
import com.hawk.game.player.Player;
import com.hawk.game.util.GameUtil;
import com.hawk.l5.L5Helper;
import com.hawk.l5.L5Task;

public class DataProxyNetRequestUtil {
	
	public static boolean checkPrestressinLossActivityOpen(String playerId) {
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			return false;
		}
		
		String redisVal = RedisProxy.getInstance().getRedisSession().getString(getPrestressingLossResultKey(playerId));
		if (!HawkOSOperator.isEmptyString(redisVal)) {
			return Integer.parseInt(redisVal) > 0;
		}
		
		HttpClient httpClient = HawkHttpUrlService.getInstance().getHttpClient();
		if (httpClient == null || !httpClient.isRunning()) {
			RedisProxy.getInstance().getRedisSession().setString(getPrestressingLossResultKey(playerId), "0", 120);
			return false;
		}
		
		boolean result = false;
		try {
			PrestressingLossKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(PrestressingLossKVCfg.class);
			String requestResult = cfg.getTestData();
			if (HawkOSOperator.isEmptyString(requestResult)) {
				requestResult = l5Req(player, cfg);
				if (requestResult == null) {
				    RedisProxy.getInstance().getRedisSession().setString(getPrestressingLossResultKey(playerId), "0", 120);
					return false;
				}
			}
			
			JSONObject json = JSONObject.parseObject(requestResult);
			if (json == null || json.getIntValue("error_code") != 0) {
				RedisProxy.getInstance().getRedisSession().setString(getPrestressingLossResultKey(playerId), "0", 120);
				HawkLog.debugPrintln("PrestressinLossActivity fetch data failed, playerId: {}, openid: {}, data: {}", player.getId(), player.getOpenId(), requestResult);
				return false;
			}
			
			/**
			 * 1. i1='0'对照组，i1='1'测试组(实验组)；
			 * 2. v3为流失概率，但是为了避免科学计数法，由原来0-1的小数改为0-10000的数字，使用的时候要除以10000
			 * 3. 只有是否实验组=true且流失概率 > 0.7 才会满足接口触发条件
			 */
			JSONObject dataObj = json.getJSONObject("result").getJSONObject("data");
			int i1Val = dataObj.containsKey("i1") ? dataObj.getIntValue("i1") : 0;
			float v3Val = dataObj.containsKey("v3") ? dataObj.getFloatValue("v3") : 0f;
			String roleid = dataObj.getString("v2");
			
			result = playerId.equals(roleid) && i1Val == 1 && v3Val >= 7000;
			
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		
		RedisProxy.getInstance().getRedisSession().setString(getPrestressingLossResultKey(playerId), result ? "1" : "0", 120);
		return result;
	}
	
	private static String getPrestressingLossResultKey(String playerId) {
		return "prestressingLossResult:" + playerId; 
	}
	
	private static String l5Req(Player player, PrestressingLossKVCfg cfg) {
		if (GameUtil.isWin32Platform(player)) {
			return null;
		}
		
		String time = HawkTime.formatTime(HawkTime.getMillisecond() - HawkTime.DAY_MILLI_SECONDS, "yyyyMMdd");
		if (!cfg.isL5()) {
			try {
				String url = cfg.getAddr().replace("{0}", time).replace("{1}", player.getOpenId());
				ContentResponse resp = HawkHttpUrlService.getInstance().doGet(url, 500);
				if (resp != null) {
					return resp.getContentAsString();
				}
			} catch (Exception e) {
				HawkException.catchException(e);
			}
			
			HawkLog.errPrintln("PrestressinLossActivity fetch data failed, playerId: {}, openid: {}", player.getId(), player.getOpenId());
			return null;
		}
		
		HawkTuple3<Integer, String, Object> retInfo = L5Helper.l5Task(cfg.getL5_modId(), cfg.getL5_cmdId(), 500, new L5Task() {
			@Override
			public HawkTuple2<Integer, Object> run(String host) {
				try {
					String subAttr = cfg.getSubAttr().replace("{0}", time).replace("{1}", player.getOpenId());
					if (!host.endsWith("/")) {
						host += "/";
					}
					
					String url = String.format("http://%s%s", host, subAttr);
					ContentResponse response = HawkHttpUrlService.getInstance().doGet(url, 500);
					return new HawkTuple2<Integer, Object>(0, response);
				} catch (Exception e) {
					HawkException.catchException(e);
				}
				
				return new HawkTuple2<Integer, Object>(-1, null);
			}
		});
		
		if (retInfo.third != null) {
			ContentResponse resp = (ContentResponse)retInfo.third;
			return resp.getContentAsString();
		}
		
		HawkLog.errPrintln("PrestressinLossActivity fetch data by l5 failed, playerId: {}, openid: {}, host: {}", player.getId(), player.getOpenId(), retInfo != null ? retInfo.second : "empty");
		return null;
	}
	
	/**
	 * 天降鸿福（302）活动触发判断
	 * @param playerId
	 */
	public static void checkHeavenBlessingActivityOpen(String playerId) {
		HawkLog.logPrintln("HeavenBlessingActivity check come in, playerId: {}", playerId);
		Player player = GlobalData.getInstance().makesurePlayer(playerId);
		if (player == null) {
			HawkLog.errPrintln("HeavenBlessingActivity player is null, playerId: {}", playerId);
			return;
		}
		long now = HawkTime.getMillisecond();
		//活动数据基础配置
		HeavenBlessingKVCfg cfg = HawkConfigManager.getInstance().getKVInstance(HeavenBlessingKVCfg.class);
		if(now - player.getCreateTime() < cfg.getRegisterDays()){
			HawkLog.errPrintln("HeavenBlessingActivity player is too new, openid: {}, playerId: {}", player.getOpenId(), playerId);
			return;
		}
		if(player.getCityLevel() < cfg.getBuildingLevel()){
			HawkLog.errPrintln("HeavenBlessingActivity player is low, openid: {}, playerId: {}", player.getOpenId(), playerId);
			return;
		}
		
		//防止阻塞异步请求腾讯l5
		HawkTaskManager.getInstance().postExtraTask(new HawkTask() {
			@Override
			public Object run() {
				checkActivity302Open(player, cfg);
				return null;
			}
		});
	}
	
	private static void checkActivity302Open(Player player, HeavenBlessingKVCfg cfg) {
		PayPuidCtrl payPuidCtrl = HawkConfigManager.getInstance().getConfigByKey(PayPuidCtrl.class, player.getOpenId());
		if(payPuidCtrl != null){
			ActivityManager.getInstance().postEvent(new HeavenBlessingActiveEvent(player.getId(), payPuidCtrl.getVip(), payPuidCtrl.getMoney()));
			return;
		}
		//测试数据,测试环境下使用
		String requestResult = cfg.getTestData();
		//如果配有测试数据就不请求l5了
		if (HawkOSOperator.isEmptyString(requestResult)) {
			requestResult = heavenBlessingL5Req(player, cfg);
			if (requestResult == null) {
				return;
			}
		}else {
			String tmp = RedisProxy.getInstance().getRedisSession().hGet("HEAVEN_BLESSING_TEST", player.getId());
			if(!HawkOSOperator.isEmptyString(tmp)){
				requestResult = tmp;
			}
		}

		JSONObject json = JSONObject.parseObject(requestResult);
		if (json == null || json.getIntValue("error_code") != 0) {
			/**
			 * 输出错误日志，102：模版配置错误；103：模版匹配错误；104：找不到对应的reidis数据源信息； 105：访问redis，返回结果为NULL；106：访问redis异常(超时等)； 500：其他错误
			 */
			HawkLog.errPrintln("HeavenBlessingActivity fetch data failed, openid: {}, playerId: {}, data: {}", player.getOpenId(), player.getId(), requestResult);
			return;
		}
		
		JSONObject dataObj = json.getJSONObject("result").getJSONObject("data");
		int ifEffect = dataObj.getIntValue("if_effect");
		if(ifEffect != 1){
			HawkLog.errPrintln("HeavenBlessingActivity fetch data effect failed, openid: {}, playerId: {}, data: {}", player.getOpenId(), player.getId(), requestResult);
			return;
		}
		int vip = dataObj.getIntValue("viplevel");
		int money = dataObj.getIntValue("imoney");
		ActivityManager.getInstance().postEvent(new HeavenBlessingActiveEvent(player.getId(), vip, money));
	}
	
	/**
	 * 洪福天降腾讯l5相关请求
	 * @param player
	 * @param cfg
	 * @return
	 */
	private static String heavenBlessingL5Req(Player player, HeavenBlessingKVCfg cfg){
		HawkLog.logPrintln("HeavenBlessingActivity l5req come in, openid: {}, playerId: {}", player.getOpenId(), player.getId());
		if (GameUtil.isWin32Platform(player)) {
			HawkLog.logPrintln("HeavenBlessingActivity player is win32, openid: {}, playerId: {}", player.getOpenId(), player.getId());
			return null;
		}
		
		//直接请求外部链接的两种情况，1，没有配l5,2,内网本机windows环境
		if (!cfg.isL5Req()) {
			try {
				String url = cfg.getAddr().replace("{0}", player.getId());
				//外部链接目前只在品管服有效果，所以内部测试暂时使用策划配置测试数据的方式
				ContentResponse resp = HawkHttpUrlService.getInstance().doGet(url, 500);
				if (resp != null) {
					return resp.getContentAsString();
				}
			} catch (Exception e) {
				HawkException.catchException(e);
			}
			HawkLog.errPrintln("HeavenBlessingActivity fetch data failed, openid: {}, playerId: {}", player.getOpenId(), player.getId());
			return null;
		}
		
		//请求l5，通过modid,cmdid获得内部链接请求超时500ms
		HawkTuple3<Integer, String, Object> retInfo = L5Helper.l5Task(cfg.getL5_modId(), cfg.getL5_cmdId(), 500, new L5Task() {
			@Override
			public HawkTuple2<Integer, Object> run(String host) {
				try {
					String subAttr = cfg.getSubAddr().replace("{0}", player.getId());
					if (!host.endsWith("/")) {
						host += "/";
					}
					String url = String.format("http://%s%s", host, subAttr);
					HawkLog.logPrintln("HeavenBlessingActivity l5task callback, openid: {}, playerId: {}, url: {}", player.getOpenId(), player.getId(), url);
					ContentResponse response = HawkHttpUrlService.getInstance().doGet(url, 500);
					return new HawkTuple2<Integer, Object>(0, response);
				} catch (Exception e) {
					HawkException.catchException(e);
				}
				HawkLog.errPrintln("HeavenBlessingActivity l5 fetch data failed, openid: {}, playerId: {}", player.getOpenId(), player.getId());
				return new HawkTuple2<Integer, Object>(-1, null);
			}
		});

		if (retInfo.third != null) {
			ContentResponse resp = (ContentResponse)retInfo.third;
			return resp.getContentAsString();
		}
		HawkLog.errPrintln("HeavenBlessingActivity fetch data by l5 failed, openid: {}, playerId: {}, host: {}", player.getOpenId(), player.getId(), retInfo != null ? retInfo.second : "empty");
		return null;
	}
}
