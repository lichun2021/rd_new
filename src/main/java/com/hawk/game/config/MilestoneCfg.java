package com.hawk.game.config;

import java.security.InvalidParameterException;
import java.util.List;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import com.hawk.game.item.ItemInfo;

/**
 * @author SJS
 * @description rookie milestone 配置
 * @date 2025/9/25
 */
@HawkConfigManager.XmlResource(file = "xml/milestone.xml")
public class MilestoneCfg extends HawkConfigBase {
    @Id
    private final int id;
    private final String reward;
    private final int buff;
    private final int buffTime;

    public MilestoneCfg() {
        this.id = 0;
        this.reward = "";
        this.buff = 0;
        this.buffTime = 0;
    }

    public int getId() {
        return id;
    }

    public List<ItemInfo> getRewardItem() {
        return ItemInfo.valueListOf(reward);
    }


    public int getBuff() {
        return buff;
    }

    public int getBuffTime() {
        return buffTime;
    }

    @Override
    protected boolean assemble() {
        return super.assemble();
    }

    @Override
    protected boolean checkValid() {
        if (id != 1) {
            MilestoneCfg configByIndex = HawkConfigManager.getInstance().getConfigByIndex(MilestoneCfg.class, id - 1);
            if (configByIndex == null) {
                logger.error("MilestoneCfg checkValid error, id:{}", id);
                throw new InvalidParameterException(String.format("MilestoneCfg checkValid error, id:%s", id));
            }
        }
        return super.checkValid();
    }
}
