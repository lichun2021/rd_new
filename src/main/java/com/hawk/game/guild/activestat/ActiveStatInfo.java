package com.hawk.game.guild.activestat;

import java.util.StringJoiner;

import org.hawk.os.HawkTime;

import com.hawk.game.protocol.GuildManager.ActiveStatItemPB;
import com.hawk.game.protocol.GuildManager.ActiveStatType;

/**
 * 活跃统计任务对象（个人每日活跃任务、战区、跨服、各类副本等）参与情况的统计信息
 * 
 * @author lating
 *
 */
public class ActiveStatInfo {
	/**
	 * 统计类型（个人每日活跃任务、战区、跨服、各类副本等）
	 */
	private int statType;
	/**
	 * 期数（标识是否是最新一期数据，0标识当前期，往后依次加1标识之前的期数）
	 */
	private int term;
	/**
	 * 获得积分
	 */
	private long score;
	/**
	 * 副本的真实期数
	 */
	private int realTerm;
	/**
	 * 创建时间
	 */
	private long createTime;
	
	public static ActiveStatInfo valueOf(int type, int term, long score) {
		ActiveStatInfo info = new ActiveStatInfo();
		info.statType = type;
		info.term = term;
		info.score = score;
		info.createTime = HawkTime.getMillisecond();
		return info;
	}

	public int getStatType() {
		return statType;
	}

	public void setStatType(int missionType) {
		this.statType = missionType;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}
	
	public long getScore() {
		return score;
	}

	public void setScore(long score) {
		this.score = score;
	}
	
	public void addScore(long score) {
		this.score += score;
	}
	
	public int getRealTerm() {
		return realTerm;
	}

	public void setRealTerm(int realTerm) {
		this.realTerm = realTerm;
	}
	
	public long getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}

	public String serialize() {
		StringJoiner sj = new StringJoiner("_");
		sj.add(statType+"");
		sj.add(term+"");
		sj.add(score+"");
		sj.add(realTerm+"");
		sj.add(createTime+"");
		return sj.toString();
	}
	
	public static ActiveStatInfo parseObj(String strInfo) {
		String[] splits = strInfo.split("_");
		ActiveStatInfo info = new ActiveStatInfo();
		info.statType = Integer.parseInt(splits[0]);
		info.term = Integer.parseInt(splits[1]);
		info.score = Long.parseLong(splits[2]);
		info.realTerm = Integer.parseInt(splits[3]);
		info.createTime = Long.parseLong(splits[4]);
		return info;
	}

	public ActiveStatItemPB.Builder toBuilder() {
		ActiveStatItemPB.Builder builder = ActiveStatItemPB.newBuilder();
		if (statType == ActiveStatType.TYPE_TBLY_SEASON_VALUE) {
			builder.setStatType(ActiveStatType.TYPE_TBLY_VALUE);
		} else {
			builder.setStatType(statType);
		}
		builder.setScore(score >= Integer.MAX_VALUE ? Integer.MAX_VALUE-1 : (int)score);
		return builder;
	}
}
