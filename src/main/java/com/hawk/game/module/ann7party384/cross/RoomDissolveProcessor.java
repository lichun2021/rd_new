package com.hawk.game.module.ann7party384.cross;

import org.hawk.os.HawkException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.Ann7PartyConst.CrossServerOper;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.Act384Ann7Party.CrossCommonPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = CrossServerOper.ROOM_DISSOLVE)
public class RoomDissolveProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room != null) {
			CrossCommonPB.Builder builder = CrossCommonPB.newBuilder();
			try {
				builder.mergeFrom(req.getReqData());
			} catch (InvalidProtocolBufferException e) {
				HawkException.catchException(e);
			}
			boolean end = builder.getValue().equals("1");
			Ann7Party384Service.getInstance().roomDissove(room, end, false, 0);
		}
	}
}
