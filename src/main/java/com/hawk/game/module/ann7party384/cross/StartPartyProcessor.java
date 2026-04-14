package com.hawk.game.module.ann7party384.cross;

import org.hawk.os.HawkException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;
import com.hawk.game.protocol.Act384Ann7Party.StartPartyCSPB;

@Ann7PartyCrossProcessor.Declare(proto = HP.code2.ANN7_PARTY_START_PARTY_C_VALUE)
public class StartPartyProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room != null) {
			StartPartyCSPB.Builder reqInfo = StartPartyCSPB.newBuilder();
			try {
				reqInfo.mergeFrom(req.getReqData());
			} catch (InvalidProtocolBufferException e) {
				HawkException.catchException(e);
			}
			Ann7Party384Service.getInstance().startParty(room, reqInfo.build(), false);
		}
	}
}
