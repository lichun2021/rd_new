package com.hawk.game.guild.activestat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.tuple.HawkTuple2;
import com.hawk.game.config.TiberiumSeasonTimeCfg;
import com.hawk.game.config.TiberiumTimeCfg;
import com.hawk.game.protocol.GuildManager.ActiveStatType;
import com.hawk.game.service.tblyTeam.TBLYSeasonService;

/**
 * 泰伯利亚、泰伯利亚联赛相关配置
 * 
 * @author lating
 *
 */
public class TBLYConfigInfo {
	
	private TiberiumSeasonTimeCfg seasonTimeCfg;
	
	private TiberiumTimeCfg timeCfg;
	
	private static List<TBLYConfigInfo> configList = new ArrayList<>();
	
	public TBLYConfigInfo(TiberiumSeasonTimeCfg seasonTimeCfg) {
		this.seasonTimeCfg = seasonTimeCfg;
	}
	
	public TBLYConfigInfo(TiberiumTimeCfg timeCfg) {
		this.timeCfg = timeCfg;
	}

	public long getEndTime() {
		if (timeCfg != null) {
			return timeCfg.getWarEndTimeValue();
		}
		
		return seasonTimeCfg.getWarEndTimeValue();
	}
	
	/**
	 * 只加入 1763568000000（2025-11-20 00:00:00）之后得数据，之前的数据没必要加了，前面功能还没上呢
	 */
	public static void initConfigList() {
		configList.clear();
		ConfigIterator<TiberiumSeasonTimeCfg> iterator = HawkConfigManager.getInstance().getConfigIterator(TiberiumSeasonTimeCfg.class);
		while (iterator.hasNext()) {
			TiberiumSeasonTimeCfg cfg = iterator.next();
			if (cfg.getWarEndTimeValue() > 1763568000000L) {
				configList.add(new TBLYConfigInfo(cfg));
			}
		}
		ConfigIterator<TiberiumTimeCfg> iterator1 = HawkConfigManager.getInstance().getConfigIterator(TiberiumTimeCfg.class);
		while (iterator1.hasNext()) {
			TiberiumTimeCfg cfg = iterator1.next();
			if (cfg.getWarEndTimeValue() > 1763568000000L) {
				configList.add(new TBLYConfigInfo(cfg));
			}
		}
		
		//对所有泰伯利亚、泰伯联赛配置期数数据统一按时间进行排序（正序）
		configList.sort(new Comparator<TBLYConfigInfo>() {
			@Override
			public int compare(TBLYConfigInfo info1, TBLYConfigInfo info2) {
				long endTime1 = info1.getEndTime();
				long endTime2 = info2.getEndTime();
				return (int) ((endTime1 - endTime2) / 1000);
			}
		});
	}
	
	/**
	 * 选取时间最近的两期数据
	 * @param statInfoList
	 * @param timeNow
	 * @return
	 */
	protected static HawkTuple2<ActiveStatInfo, ActiveStatInfo> selectStatInfo(List<ActiveStatInfo> statInfoList, long timeNow) {
		if (statInfoList.isEmpty()) {
			return new HawkTuple2<>(null, null);
		}
		//先对传进来的 ActiveStatInfo 数据按期数时间进行排序（倒序）
		statInfoList.sort(new Comparator<ActiveStatInfo>() {
			@Override
			public int compare(ActiveStatInfo info1, ActiveStatInfo info2) {
				long endTime1 = getTermEndTime(info1.getStatType(), info1.getRealTerm());
				long endTime2 = getTermEndTime(info2.getStatType(), info2.getRealTerm());
				return (int) ((endTime2 - endTime1) / 1000); // 这里按倒序排，时间越晚的放在前面
			}
		});
		
		//从排序后的数据中取时间最近的两期数据
		ActiveStatInfo lastTermInfo = statInfoList.get(0);
		ActiveStatInfo preTermInfo = statInfoList.size() > 1 ? statInfoList.get(1) : null;
		long endTime = getTermEndTime(lastTermInfo.getStatType(), lastTermInfo.getRealTerm());
		int i = 0;
		for (;i<configList.size();i++) {
			long cfgEndTime = configList.get(i).getEndTime();
			if (cfgEndTime > endTime) {
				//到此时，i位置对应的已经是 lastTermInfo 的下一期了，因此 i-1 才对应 lastTermInfo 这一期
				break;
			}
		}
		long nextTermEndTime = 0;
		if (i < configList.size()) {
			nextTermEndTime = configList.get(i).getEndTime();
		}
		//nextTermEndTime > timeNow 表明下一期时间还没到，那已经参与过的都有效 
		if (nextTermEndTime == 0 || nextTermEndTime > timeNow) {
			if (preTermInfo != null) {
				long endTime1 = getTermEndTime(preTermInfo.getStatType(), preTermInfo.getRealTerm());
				int index = i - 2; //i-1对应lastTermInfo，i-2对应preTermInfo
				if (index >= 0 && index < configList.size() && endTime1 != configList.get(index).getEndTime()) {
					preTermInfo = null; 
				}
			}
			return new HawkTuple2<>(lastTermInfo, preTermInfo);
		}
		
		//下一期已经过了，看下下期，决定上上期的数据
		long nextTerm2EndTime = 0;
		if (i + 1 < configList.size()) {
			nextTerm2EndTime = configList.get(i+1).getEndTime();
		}
		//nextTerm2EndTime > timeNow 表明下下期时间还没到，那最后参与的那次可以作为上上期数据
		return new HawkTuple2<>(null, nextTerm2EndTime >= timeNow ? lastTermInfo : null);
	}
	
	private static long getTermEndTime(int type, int termId) {
		if (type == ActiveStatType.TYPE_TBLY_VALUE) {
			TiberiumTimeCfg timeCfg = HawkConfigManager.getInstance().getConfigByKey(TiberiumTimeCfg.class, termId);
			if (timeCfg != null) {
				return timeCfg.getWarEndTimeValue();
			}
		} else {
			TiberiumSeasonTimeCfg timeCfg = TBLYSeasonService.getInstance().getTimeCfgBySeasonAndTermId(termId/1000, termId%1000);
			if (timeCfg != null) {
				return timeCfg.getWarEndTimeValue();
			}
		}
		return 0;
	}
}
