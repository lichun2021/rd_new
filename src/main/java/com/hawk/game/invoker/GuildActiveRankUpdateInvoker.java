package com.hawk.game.invoker;

import com.hawk.game.rank.RankService;
import org.hawk.app.HawkAppObj;
import org.hawk.msg.HawkMsg;
import org.hawk.msg.invoker.HawkMsgInvoker;

public class GuildActiveRankUpdateInvoker extends HawkMsgInvoker {
    String guildId;
    long incrementScore;

    public GuildActiveRankUpdateInvoker(String guildId, long incrementScore) {
        this.guildId = guildId;
        this.incrementScore = incrementScore;
    }

    @Override
    public boolean onMessage(HawkAppObj arg0, HawkMsg arg1) {
        RankService.getInstance().getGuildActiveRankObject().updateRank(guildId, incrementScore);
        return true;
    }

}
