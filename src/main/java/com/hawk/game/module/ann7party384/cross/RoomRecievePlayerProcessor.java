package com.hawk.game.module.ann7party384.cross;

import org.hawk.log.HawkLog;
import org.hawk.os.HawkException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.Ann7PartyConst.CrossServerOper;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.Act384Ann7Party.CrossCommonPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = CrossServerOper.REC_QUEUE_PLAYER)
public class RoomRecievePlayerProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room == null) {
			return;
		}
		
		CrossCommonPB.Builder builder = CrossCommonPB.newBuilder();
		try {
			builder.mergeFrom(req.getReqData());
		} catch (InvalidProtocolBufferException e) {
			HawkException.catchException(e);
		}
		
		HawkLog.logPrintln("ann7party auto rec queue player process: {}", builder.getValue());
		String[] players = builder.getValue().split(",");
		for (String playerId : players) {
			room.recievePlayer(playerId, false);
		}
		room.updateSyncPbObj();
		
		Ann7Party384Service.getInstance().syncPlayer(room.getMasterPlayerId());
		for (String playerId : room.getRoomMemberSet()) {
			Ann7Party384Service.getInstance().syncPlayer(playerId);
		}
	}
}
