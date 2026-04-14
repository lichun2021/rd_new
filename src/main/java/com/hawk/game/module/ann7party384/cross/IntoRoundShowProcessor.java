package com.hawk.game.module.ann7party384.cross;

import org.hawk.os.HawkException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.Ann7PartyConst.CrossServerOper;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoundCSPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = CrossServerOper.INTO_ROUND_SHOW)
public class IntoRoundShowProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room != null) {
			PartyRoundCSPB.Builder builder = PartyRoundCSPB.newBuilder();
			try {
				builder.mergeFrom(req.getReqData());
			} catch (InvalidProtocolBufferException e) {
				HawkException.catchException(e);
			}
			Ann7Party384Service.getInstance().intoRoundRewardShow(room, builder.build(), false);
		}
	}
}
