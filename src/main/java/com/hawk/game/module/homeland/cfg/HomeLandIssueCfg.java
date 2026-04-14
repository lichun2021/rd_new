package com.hawk.game.module.homeland.cfg;

import com.google.common.collect.ImmutableList;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.util.WeightAble;
import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;

import java.util.ArrayList;
import java.util.List;

/**
 * 家园Npc配置
 *
 * @author zhy
 */
@HawkConfigManager.XmlResource(file = "xml/homeland_issue.xml")
public class HomeLandIssueCfg extends HawkConfigBase {
    @Id
    protected final int issueId;
    protected final int reward;
    protected final int isMove;

    public HomeLandIssueCfg() {
        issueId = 0;
        reward = 0;
        isMove = 0;
    }

    @Override
    protected boolean assemble() {
        return true;
    }


    @Override
    protected boolean checkValid() {
        return true;
    }

    public int getId() {
        return issueId;
    }

    public int getReward() {
        return reward;
    }

    public int getIsMove() {
        return isMove;
    }
}
