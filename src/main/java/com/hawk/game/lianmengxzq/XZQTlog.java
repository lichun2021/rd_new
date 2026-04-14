package com.hawk.game.lianmengxzq;

import java.util.List;
import org.hawk.os.HawkOSOperator;
import com.hawk.game.player.Player;
import com.hawk.game.util.LogUtil;
import com.hawk.log.LogConst.LogInfoType;

public class XZQTlog {

	/**
	 * 小站区参与联盟
	 * @param termId
	 * @param guildId
	 * @param memberCount
	 */
	public static void XZQGuildParticipate(int termId,String guildId,int memberCount,int type){
		LogUtil.recordNonPersonalLog(LogInfoType.xzq_guild_participate, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("memberCount", memberCount)
			.put("type", type);
		});
	}
	
	
	/**
	 * 小站区报名
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */
	public static void XZQLogSignup(Player player,int termId,String guildId,int buildId){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_guild_signup, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId);
		});
	}
	
	
	/**
	 * 小站区NPC守军被打败
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */
	public static void XZQNPCAttacked(Player player,int termId,String guildId,int buildId){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_npc_attacked, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId);
		});
	}
	
	
	/**
	 * 小站区控制
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */	
	public static void XZQControl(Player player,int termId,String guildId,int buildId,int type,int count){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_control, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId)
			.put("type", buildId)
			.put("count", buildId);
		});
	}
	
	
	/**
	 * 小站区刻字(伤害)
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 * @param damage
	 */
	public static void XZQRecordDamage(Player player,int termId,String guildId,int buildId,long damage,int damageIndex){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_record_damage, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId)
			.put("damage", damage)
			.put("damageIndex", damageIndex);
		});
	}
	
	
	/**
	 * 小站区刻字首先攻占
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */
	public static void XZQRecordFistOccupy(Player player,int termId,String guildId,int buildId){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_record_fist_occupy, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId);
		});
	}
	
	
	/**
	 * 小站区攻占
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */
	public static void XZQRecordOccupy(Player player,int termId,String guildId,int buildId){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_record_occupy, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId);
		});
	}
	
	
	
	/**
	 * 小站区玩家参与
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */
	public static void XZQPlayerParticipate(Player player,int termId,String guildId,int buildId){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_player_participate, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId);
		});
	}
	
	public static void XZQPlayerParticipate(List<Player> players,int termId,int buildId){
		for(Player player : players){
			XZQPlayerParticipate(player, termId, player.getGuildId(), buildId);
		}
	}
	
	/**
	 * 小站区报名取消
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */
	public static void XZQGuildSignupCancel(Player player,int termId,String guildId,int buildId){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_guild_signup_cancel, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId);
		});
	}
	

	/**
	 * 小站去占领时长
	 * @param termId
	 * @param guildId
	 * @param buildId
	 * @param controlTime
	 */
	public static void XZQSControlTime(int termId,String guildId,int buildId,int controlTime){
		LogUtil.recordNonPersonalLog(LogInfoType.xzq_control_time, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("controlTime", controlTime)
			.put("buildId", buildId);
		});
	}
	
	
	/**
	 * 小站区初始化移除世界点
	 * @param pointType
	 * @param playerId
	 * @param guildId
	 * @param buildId
	 * @param controlTime
	 */
	public static void XZQInitRemove(int pointId,int pointType,String playerId, String guildId, int buildId){
		LogUtil.recordNonPersonalLog(LogInfoType.xzq_init_remove, (logParam)->{
			logParam.put("pointId", pointId)
			.put("pointType", pointType)
			.put("playerId", HawkOSOperator.isEmptyString(playerId) ?"nullContet":playerId)
			.put("guildId", HawkOSOperator.isEmptyString(guildId) ?"nullContet":guildId)
			.put("buildId", buildId);
		});
	}
	
	
	
	/**
	 * 小站区礼包发放
	 * @param player
	 * @param termId
	 * @param guildId
	 * @param buildId
	 */
	public static void XZQGfitSend(Player player,int termId,String guildId,int buildId, String sender,int awardId,int count){
		LogUtil.recordPersonalLog(player, LogInfoType.xzq_gift_send, (logParam)->{
			logParam.put("termId", termId)
			.put("guildId", guildId)
			.put("buildId", buildId)
			.put("sender", sender)
			.put("awardId", awardId)
			.put("count", count);
		});
	}

}
