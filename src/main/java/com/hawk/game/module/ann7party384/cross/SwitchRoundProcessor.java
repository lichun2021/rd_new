package com.hawk.game.module.ann7party384.cross;

import com.hawk.game.module.ann7party384.Ann7Party384Service;
import com.hawk.game.module.ann7party384.Ann7PartyCrossProcessor;
import com.hawk.game.module.ann7party384.Ann7PartyConst.CrossServerOper;
import com.hawk.game.module.ann7party384.data.Party384Room;
import com.hawk.game.protocol.Act384Ann7Party.PartyServiceReq;

@Ann7PartyCrossProcessor.Declare(proto = CrossServerOper.SWITCH_ROUND)
public class SwitchRoundProcessor extends Ann7PartyCrossProcessor {

	@Override
	public void process(PartyServiceReq req) {
		Party384Room room = Ann7Party384Service.getInstance().getRoomById(req.getRoomId());
		if (room != null) {
			Ann7Party384Service.getInstance().switchPartyRewardRound(room, false);
		}
	}
}
