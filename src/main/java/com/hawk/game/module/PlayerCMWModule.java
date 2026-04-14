package com.hawk.game.module;

import com.hawk.game.player.Player;
import com.hawk.game.player.PlayerModule;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.PBCommonMatch;
import com.hawk.game.protocol.XQHX;
import com.hawk.game.service.commonMatch.CMWService;
import com.hawk.game.service.commonMatch.manager.ipml.XHJZSeasonManager;
import com.hawk.game.service.xqhxWar.season.XQHXSeasonManager;
import com.hawk.game.service.xqhxWar.season.XQHXSeasonService;
import org.hawk.annotation.ProtocolHandler;
import org.hawk.net.protocol.HawkProtocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PlayerCMWModule extends PlayerModule {
    static Logger logger = LoggerFactory.getLogger("Server");

    /**
     * 构造函数
     *
     * @param player
     */
    public PlayerCMWModule(Player player) {
        super(player);
    }

    @Override
    protected boolean onPlayerLogin() {
        XHJZSeasonManager.getInstance().pageInfo(player, null);
        XQHXSeasonManager.getInstance().pageInfo(player, null);
        return true;
    }

    @ProtocolHandler(code = HP.code2.CMW_PAGE_INFO_REQ_VALUE)
    public void pageInfo(HawkProtocol hawkProtocol) {
        PBCommonMatch.PBCMWPageInfoReq req = hawkProtocol.parseProtocol(PBCommonMatch.PBCMWPageInfoReq.getDefaultInstance());
        switch (req.getMatchType()) {
            case XHJZ_SEASON:
                CMWService.getInstance().pageInfo(player, req);
                break;
            case XQHX_SEASON:
                XQHXSeasonService.getInstance().pageInfo(player, req);
                break;
        }
    }

    @ProtocolHandler(code = HP.code2.CMW_RANK_INFO_REQ_VALUE)
    public void rankInfo(HawkProtocol hawkProtocol) {
        PBCommonMatch.PBCMWRankInfoReq req = hawkProtocol.parseProtocol(PBCommonMatch.PBCMWRankInfoReq.getDefaultInstance());
        switch (req.getMatchType()) {
            case XHJZ_SEASON:
                CMWService.getInstance().rankInfo(player, req);
                break;
            case XQHX_SEASON:
                XQHXSeasonService.getInstance().rankInfo(player, req);
                break;
        }
    }

    @ProtocolHandler(code = HP.code2.CMW_BATTLE_INFO_REQ_VALUE)
    public void battleInfo(HawkProtocol hawkProtocol) {
        PBCommonMatch.PBCMWBattleInfoReq req = hawkProtocol.parseProtocol(PBCommonMatch.PBCMWBattleInfoReq.getDefaultInstance());
        switch (req.getMatchType()) {
            case XHJZ_SEASON:
                CMWService.getInstance().battleInfo(player, req);
                break;
            case XQHX_SEASON:
                XQHXSeasonService.getInstance().battleInfo(player, req);
                break;
        }
    }

    @ProtocolHandler(code = HP.code2.CMW_BATTLE_TIME_REQ_VALUE)
    public void timeInfo(HawkProtocol hawkProtocol) {
        PBCommonMatch.PBCMWBattleTimeReq req = hawkProtocol.parseProtocol(PBCommonMatch.PBCMWBattleTimeReq.getDefaultInstance());
        switch (req.getMatchType()) {
            case XHJZ_SEASON:
                CMWService.getInstance().timeInfo(player, req);
                break;
            case XQHX_SEASON:
                XQHXSeasonService.getInstance().timeInfo(player, req);
                break;
        }
    }

    @ProtocolHandler(code = HP.code2.CMW_BATTLE_TARGET_REQ_VALUE)
    public void targetInfo(HawkProtocol hawkProtocol) {
        PBCommonMatch.PBCMWBattleTargetReq req = hawkProtocol.parseProtocol(PBCommonMatch.PBCMWBattleTargetReq.getDefaultInstance());
        switch (req.getMatchType()) {
            case XHJZ_SEASON:
                CMWService.getInstance().targetInfo(player, req);
                break;
            case XQHX_SEASON:
                XQHXSeasonService.getInstance().targetInfo(player, req);
                break;
        }
    }
}
