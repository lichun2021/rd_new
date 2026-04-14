package com.hawk.game.module.ann7party384.cross;

import org.hawk.os.HawkException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Act384Ann7Party.CreateRoomCSPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = HP.code2.ANN7_PARTY_CREATE_ROOM_C_VALUE)
public class CreateRoomProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		CreateRoomCSPB.Builder builder = CreateRoomCSPB.newBuilder();
		try {
			builder.mergeFrom(req.getReqData());
		} catch (InvalidProtocolBufferException e) {
			HawkException.catchException(e);
		}
		Party384Room room = new Party384Room();
		room.mergeFrom(builder.getPartyRoom());
		Ann7Party384Service.getInstance().addRoom(room);
		if (!room.isRobotRoom()) {
			Ann7Party384Service.getInstance().updateApplyAndInvite(room.getMasterPlayerId(), builder.getApplyJoinRoomList(), builder.getInviteMeRoomList());
		}
	}
}
