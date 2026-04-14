package com.hawk.game.module.ann7party384.cross;

import java.util.HashSet;
import java.util.Set;

import org.hawk.os.HawkException;
import com.google.protobuf.InvalidProtocolBufferException;
import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Act384Ann7Party.CrossCommonPB;
import com.hawk.game.protocol.Act384Ann7Party.PartyRoomSelectInfo;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = HP.code2.ANN7_PARTY_ROOM_SET_C_VALUE)
public class SetRoomProcessor extends Ann7PartyCrossProcessor {

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
			
			String[] vals = builder.getValue().split(",");
			PartyRoomSelectInfo.Builder selectInfo = PartyRoomSelectInfo.newBuilder();
			selectInfo.setAutoStart(Integer.parseInt(vals[0]));
			selectInfo.setJoinApply(Integer.parseInt(vals[1]));
			Set<String> addMembers = new HashSet<>();
			int index = 2;
			while (index < vals.length) {
				addMembers.add(vals[index]);
				index++;
			}
			Ann7Party384Service.getInstance().setRoom(room, selectInfo.build(), false, addMembers);
		}
	}
}
