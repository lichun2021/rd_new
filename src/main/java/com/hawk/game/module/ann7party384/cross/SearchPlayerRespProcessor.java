package com.hawk.game.module.ann7party384.cross;

import org.hawk.os.HawkException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.Ann7PartyConst.CrossServerOper;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomMemberInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = CrossServerOper.SEARCH_ROLE_RESP)
public class SearchPlayerRespProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		String playerId = req.getRoomId();
		PartyRoomMemberInfo.Builder reqInfo = PartyRoomMemberInfo.newBuilder();
		try {
			reqInfo.mergeFrom(req.getReqData());
		} catch (InvalidProtocolBufferException e) {
			HawkException.catchException(e);
		}
		Ann7Party384Service.getInstance().searchPlayerResp(playerId, reqInfo.build());
	}
}
