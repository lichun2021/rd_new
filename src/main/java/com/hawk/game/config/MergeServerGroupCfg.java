package com.hawk.game.config;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;

import com.hawk.serialize.string.SerializeHelper;

@HawkConfigManager.XmlResource(file = "xml/merge_server_group.xml")
public class MergeServerGroupCfg extends HawkConfigBase {
	@Id
	private final int id;
	/**
	 * 已经合并的区服
	 */
	private final String mergeServerIds;
	/**
	 * 将来要合并的区服.
	 */
	private final String futureMergeServerIds;
	/**
	 * 主服
	 */
	private final String masterServer;
	/**
	 * 从服
	 */
	private final String slaveServers;
	
	/**
	 * 和服后加入联盟时间限定
	 */
	private final String guildJoinTimeLimit;
	
	/**
	 * 已经合并的区服
	 */
	private List<String> mergeServerIdList;
	/**
	 * 从服
	 */
	private List<String> slaveServerIdList;
	/**
	 * 将要合的区服
	 */
	private List<String> futureMergeServerIdList;
	
	/**
	 * 和服前，所在主服
	 */
	private Map<String,String> mainServerBefMerge;
	private long guildJoinTimeLimitValue;
	
	public MergeServerGroupCfg() {
		this.id = 0;
		this.mergeServerIds = "";
		this.futureMergeServerIds = "";
		masterServer = "";
		slaveServers = "";
		guildJoinTimeLimit = "";
	}
	
	public int getId() {
		return id;
	}
	
	public String getMergeServerIds() {
		return mergeServerIds;
	}
	
	public List<String> getMergeServerIdList() {
		return mergeServerIdList;
	}
	public void setMergeServerIdList(List<String> mergeServerIdList) {
		this.mergeServerIdList = mergeServerIdList;
	}

	public String getMasterServer() {
		return masterServer;
	}

	public List<String> getSlaveServerIdList() {
		return slaveServerIdList;
	}
	
	@Override
	public boolean assemble() {
		mergeServerIdList = SerializeHelper.stringToList(String.class, mergeServerIds, SerializeHelper.ATTRIBUTE_SPLIT);
		futureMergeServerIdList = SerializeHelper.stringToList(String.class, futureMergeServerIds, SerializeHelper.ATTRIBUTE_SPLIT);
		if (mergeServerIdList.isEmpty() && futureMergeServerIdList.isEmpty()) {
			HawkLog.errPrintln("MergeServerGroupCfg assemble failed, id: {}, mergeServerIdList: {}, futureMergeServerIdList: {}", id, mergeServerIdList, futureMergeServerIdList);
			return false;
		}
		
		if (!mergeServerIdList.isEmpty() && !mergeServerIdList.get(0).equals(masterServer)) {
			HawkLog.errPrintln("MergeServerGroupCfg assemble failed, id: {}, masterServer: {}, mergeServerList: {}", id, masterServer, mergeServerIdList);
			return false;
		}
		
		if (!futureMergeServerIdList.isEmpty() && !futureMergeServerIdList.get(0).equals(masterServer)) {
			HawkLog.errPrintln("MergeServerGroupCfg assemble failed, id: {}, masterServer: {}, futureMergeServerIdList: {}", id, masterServer, futureMergeServerIdList);
			return false;
		}
		
		slaveServerIdList = SerializeHelper.stringToList(String.class, slaveServers, SerializeHelper.ATTRIBUTE_SPLIT);
		if (slaveServerIdList.isEmpty() || slaveServerIdList.contains(masterServer)) {
			HawkLog.errPrintln("MergeServerGroupCfg assemble failed, id: {}, masterServer: {}, slaveServerIdList: {}", id, masterServer, slaveServerIdList);
			return false;
		}
		
		Map<String,String> mainServerBefMergeTemp = new HashMap<>();
		List<String> serverIds = new ArrayList<>();
		serverIds.addAll(mergeServerIdList);
		serverIds.addAll(futureMergeServerIdList);
		String mainServer = "";
		for(String serverId : serverIds){
			if(serverId.equals(this.masterServer) || this.slaveServerIdList.contains(serverId)){
				mainServer = serverId;
			}
			mainServerBefMergeTemp.put(serverId, mainServer);
		}
		this.mainServerBefMerge = mainServerBefMergeTemp;
		if(!HawkOSOperator.isEmptyString(guildJoinTimeLimit)){
			guildJoinTimeLimitValue =  HawkTime.parseTime(guildJoinTimeLimit);
		}
		return true;
	}
	
	@Override
	public boolean checkValid() {				
		if (!Collections.disjoint(mergeServerIdList, futureMergeServerIdList)) {
			throw new InvalidParameterException("已经合并的区服和将要合并的区服数据有交叉 id:"+id);
		}
		
		return true;
	}

	public List<String> getFutureMergeServerIdList() {
		return futureMergeServerIdList;
	}
	
	public Map<String, String> getMainServerBefMerge() {
		return mainServerBefMerge;
	}
	
	public long getGuildJoinTimeLimitValue() {
		return guildJoinTimeLimitValue;
	}
}
