package com.hawk.game.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.log.HawkLog;
import org.hawk.os.HawkOSOperator;
import org.hawk.os.HawkTime;
import org.hawk.tuple.HawkTuple2;
import org.hawk.tuple.HawkTuples;

import com.esotericsoftware.kryo.serializers.DefaultSerializers.StringSerializer;
import com.google.common.collect.ImmutableList;
import com.hawk.game.protocol.Rank.RankInfo;
import com.hawk.game.protocol.Rank.RankType;
import com.hawk.game.rank.RankService;
import com.hawk.serialize.string.SerializeHelper;


/**
 * 跨服活动时间配置
 * @author Jesse
 *
 */
@HawkConfigManager.XmlResource(file = "xml/cross_manual_match.xml")
public class CrossManualMatchCfg extends HawkConfigBase {
	/** 活动期数 */
	@Id
	private final int termId;
	
	private final int id;
	
	private final String matchInfo;

	
	private List<HawkTuple2<String, String>> matchInfoList;

	public CrossManualMatchCfg() {
		termId = 0;
		id = 0;
		matchInfo = "";
	}
	
	public int getTermId() {
		return termId;
	}

	public int getId() {
		return id;
	}
	
	
	
	public List<HawkTuple2<String, String>> getMatchInfoList() {
		return matchInfoList;
	}
	
	@Override
	protected boolean assemble() {
		List<HawkTuple2<String, String>> matchInfoListTemp = new ArrayList<>();
		Set<String> matchServers = new HashSet<>();
		if (!HawkOSOperator.isEmptyString(matchInfo)) {
			String[] arr = this.matchInfo.trim().split(SerializeHelper.BETWEEN_ITEMS);
			for(String groupIdStr :  arr){
				String[] pair = groupIdStr.split(SerializeHelper.ATTRIBUTE_SPLIT);
				if(pair.length != 2){
					return false;
				}
				
				String s1 = pair[0];
				String s2 =  pair[1];
				if(matchServers.contains(s1)){
					return false;
				}
				if(matchServers.contains(s2)){
					return false;
				}
				matchInfoListTemp.add(HawkTuples.tuple(s1, s2));
				matchServers.add(s1);
				matchServers.add(s2);
			}
		}
		this.matchInfoList = ImmutableList.copyOf(matchInfoListTemp);
		return true;
	}

}
