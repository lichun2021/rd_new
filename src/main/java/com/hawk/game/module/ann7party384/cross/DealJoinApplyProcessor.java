package com.hawk.game.module.ann7party384.cross;

import org.hawk.os.HawkException;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Act384Ann7Party.DealJoinApplyReq;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = HP.code2.ANN7_PARTY_DEAL_APPLY_C_VALUE)
public class DealJoinApplyProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room != null) {
			DealJoinApplyReq.Builder reqInfo = DealJoinApplyReq.newBuilder();
			try {
				reqInfo.mergeFrom(req.getReqData());
			} catch (InvalidProtocolBufferException e) {
				HawkException.catchException(e);
			}
			Ann7Party384Service.getInstance().dealJoinApply(room, reqInfo.build(), false);
		}
	}
}
