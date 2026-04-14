package com.hawk.game.module.ann7party384.cross;

import org.hawk.os.HawkException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;
import com.hawk.game.protocol.Act384Ann7Party.SelectRewardReq;

@Ann7PartyCrossProcessor.Declare(proto = HP.code2.ANN7_PARTY_SELECT_AWARD_C_VALUE)
public class SelectAwardProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		String paramRoomId = req.getRoomId();
		String[] paramArr = paramRoomId.split(",");
		String roomId = paramArr[0], playerId = paramArr[1];
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(roomId);
		if (room != null) {
			SelectRewardReq.Builder builder = SelectRewardReq.newBuilder();
			try {
				builder.mergeFrom(req.getReqData());
			} catch (InvalidProtocolBufferException e) {
				HawkException.catchException(e);
			}
			Ann7Party384Service.getInstance().selectAward(playerId, room, builder.build(), false);
		}
	}
}
