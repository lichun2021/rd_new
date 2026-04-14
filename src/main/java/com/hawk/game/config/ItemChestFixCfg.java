package com.hawk.game.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.log.HawkLog;

import com.hawk.serialize.string.SerializeHelper;

/**
 * 【1016版本】【卡维利臻藏】保底功能优化：支持数组
 * 
 * @author lating
 *
 */
@HawkConfigManager.XmlResource(file = "xml/itemChestFix.xml")
public class ItemChestFixCfg extends HawkConfigBase {

	@Id
	private final int id;
	/**
	 * 被使用的宝箱id
	 */
	private final int item;
	
	/**
	 * 监听的大奖id（获得的大奖id）
	 */
	private final String listeningItem;
	/**
	 * 保底次数（连续不中次数）
	 */
	private final int failedNum;
	
	/**
	 * 保底award
	 */
	private final int fixAward;
	
	private List<Integer> listenItemList = new ArrayList<>();
	private static Map<Integer, ItemChestFixCfg> useItemConfigMap = new HashMap<>();
	
	public ItemChestFixCfg() {
		id = 0;
		item = 0;
		listeningItem = "";
		failedNum = 0;
		fixAward = 0;
	}
	
	public boolean assemble() {
		if (useItemConfigMap.containsKey(item)) {
			HawkLog.errPrintln("itemChestFix.xml item repeated {}", item);
			return false;
		}
		useItemConfigMap.put(item, this);
		listenItemList = SerializeHelper.stringToList(Integer.class, listeningItem, "_");
		return true;
	}
	
	public static boolean isConfigItem(int itemId) {
		return useItemConfigMap.containsKey(itemId);
	} 
	
	public static ItemChestFixCfg getConfig(int itemId) {
		return useItemConfigMap.get(itemId);
	}
	
	public boolean isObtainListenItem(int itemId) {
		return listenItemList.contains(itemId);
	}
	
	public List<Integer> getObtainListenItemIds() {
		return listenItemList;
	}

	public int getId() {
		return id;
	}

	public int getItem() {
		return item;
	}

	public String getListeningItem() {
		return listeningItem;
	}

	public int getFailedNum() {
		return failedNum;
	}

	public int getFixAward() {
		return fixAward;
	}

}
