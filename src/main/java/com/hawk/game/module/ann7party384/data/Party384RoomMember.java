package com.hawk.game.module.ann7party384.data;

import java.util.List;

import org.hawk.os.HawkRand;
import org.hawk.uuid.HawkUUIDGenerator;

import com.hawk.activity.type.impl.ann7party384.cfg.Ann7Party384KVCfg;
import com.hawk.game.GsConfig;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyConst;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomMemberInfo;

public class Party384RoomMember {

	private String playerId;
	
	private String playerName;
	
	private int vipLevel;
	/** 头像 */
	private int icon;
	/** 平台头像 */
	private String pfIcon;
	
	private String serverId;
	
	private int robot;
	
	private long power;
	/** 头像框 */
	private int frame;
	
	public String getPlayerId() {
		return playerId;
	}

	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getVipLevel() {
		return vipLevel;
	}

	public void setVipLevel(int vipLevel) {
		this.vipLevel = vipLevel;
	}

	public int getIcon() {
		return icon;
	}

	public void setIcon(int iconId) {
		this.icon = iconId;
	}

	public String getPfIcon() {
		return pfIcon;
	}

	public void setPfIcon(String pfIcon) {
		this.pfIcon = pfIcon;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public int getRobot() {
		return robot;
	}

	public void setRobot(int robot) {
		this.robot = robot;
	}
	
	public boolean isRobot() {
		return this.robot > 0;
	}

	public long getPower() {
		return power;
	}

	public void setPower(long power) {
		this.power = power;
	}
	
	public PartyRoomMemberInfo.Builder toBuilder() {
		PartyRoomMemberInfo.Builder builder = PartyRoomMemberInfo.newBuilder();
		builder.setPlayerId(playerId);
		builder.setPlayerName(playerName);
		builder.setVipLevel(vipLevel);
		builder.setIconId(icon);
		builder.setPfIcon(pfIcon == null ? "" : pfIcon);
		builder.setServerId(serverId);
		builder.setRobot(robot);
		builder.setPower(power);
		builder.setFrame(frame);
		return builder;
	}
	
	public void mergeFrom(PartyRoomMemberInfo builder) {
		this.playerId = builder.getPlayerId();
		this.playerName = builder.getPlayerName();
		this.vipLevel = builder.getVipLevel();
		this.icon = builder.getIconId();
		this.pfIcon = builder.getPfIcon();
		this.serverId = builder.getServerId();
		this.robot = builder.getRobot();
		this.power = builder.getPower();
		this.frame = builder.getFrame();
	}

	public static Party384RoomMember createRobot() {
		List<Integer> frameList = Ann7Party384KVCfg.getInstance().getPortraitFrameList();
		List<Integer> imageList = Ann7Party384KVCfg.getInstance().getPortraitImgList();
		String pfIcon = "2_" + HawkRand.randomObject(imageList) + "_" + HawkRand.randomObject(frameList);
		//vip其实是不需要的， 名字其实是隐藏的【指****】，头像和头像框 我配两个数组吧，战力也是没必要，玩家根本搜不到
		Party384RoomMember robot = new Party384RoomMember();
		robot.setRobot(1);
		robot.setPlayerId(Ann7PartyConst.ROBOT_PREFIX + HawkUUIDGenerator.genUUID());
		robot.setPlayerName(Ann7Party384Service.getInstance().randRobotName());
		robot.setServerId(GsConfig.getInstance().getServerId());
		robot.setPfIcon(pfIcon);
		//robot.setIcon(HawkRand.randomObject(imageList));
		//robot.setFrame(HawkRand.randomObject(frameList));
		//robot.setVipLevel(10);
		//robot.setPower(15000000);
		return robot;
	}
	
	public static Party384RoomMember createPlayerMember(Player player) {
		Party384RoomMember member = new Party384RoomMember();
		member.setPlayerId(player.getId());
		member.setPlayerName(player.getName());
		member.setVipLevel(player.getVipLevel());
		member.setIcon(player.getIcon());
		member.setPfIcon(player.getPfIcon());
		member.setServerId(GsConfig.getInstance().getServerId());
		member.setPower(player.getPower());
		return member;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}
	
}
