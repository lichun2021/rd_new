package com.hawk.game.module.ann7party384.cross;

import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.crossproxy.CrossProxy;
import com.hawk.game.global.GlobalData;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.Ann7PartyConst.CrossServerOper;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomMemberInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;
import com.hawk.game.queryentity.AccountInfo;

@Ann7PartyCrossProcessor.Declare(proto = CrossServerOper.SEARCH_ROLE_REQ)
public class SearchPlayerReqProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		String[] fromParams = req.getRoomId().split(":");
		String fromServer = fromParams[0];
		String fromPlayer = fromParams[1];
		PartyRoomMemberInfo.Builder reqInfo = PartyRoomMemberInfo.newBuilder();
		try {
			reqInfo.mergeFrom(req.getReqData());
		} catch (InvalidProtocolBufferException e) {
			HawkException.catchException(e);
		}
		String tarPlayerId = reqInfo.getPlayerId();
		
		PartyRoomMemberInfo.Builder builder = PartyRoomMemberInfo.newBuilder();
		AccountInfo accountInfo = GlobalData.getInstance().getAccountInfoByPlayerId(tarPlayerId);
		if (accountInfo != null) {
			Player tarPlayer = GlobalData.getInstance().makesurePlayer(tarPlayerId);
			builder.setPlayerId(tarPlayerId);
			builder.setPlayerName(tarPlayer.getName());
			builder.setVipLevel(tarPlayer.getVipLevel());
			builder.setIconId(tarPlayer.getIcon());
			builder.setPfIcon(tarPlayer.getPfIcon());
			builder.setServerId(tarPlayer.getMainServerId());
			builder.setPower(tarPlayer.getPower());
		} else {
			builder.setPlayerId("");
		}
		
		PartyServiceReq.Builder csReq = PartyServiceReq.newBuilder();
		csReq.setReqType(CrossServerOper.SEARCH_ROLE_RESP);
		csReq.setRoomId(fromPlayer);
		csReq.setReqData(builder.build().toByteString());
		HawkProtocol hawkProtocol = HawkProtocol.valueOf(HP.code2.ANN7_PARTY_SERVICE_REQ_VALUE, csReq);
		CrossProxy.getInstance().sendNotify(hawkProtocol, fromServer, null);
	}
	
}
