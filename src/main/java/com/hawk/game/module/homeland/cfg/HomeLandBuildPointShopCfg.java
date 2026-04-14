package com.hawk.game.module.homeland.cfg;

import com.hawk.game.item.ItemInfo;
import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkException;

import java.util.List;


/**
 * 家园建筑兑换商城
 *
 * @author zhy
 */
@HawkConfigManager.XmlResource(file = "xml/homeland_recovery_shop.xml")
public class HomeLandBuildPointShopCfg extends HawkConfigBase {

    /**
     *
     */
    @Id
    private final int id;
    private final int times;
    private final int refreshType;
    private final String needItem;
    private final String gainItem;
    private List<ItemInfo> needItemInfo;
    private List<ItemInfo> gainItemInfo;

    public HomeLandBuildPointShopCfg() {
        id = 0;
        times = 0;
        refreshType = 0;
        needItem = "";
        gainItem = "";
    }

    @Override
    protected boolean assemble() {
        try {
            needItemInfo = ItemInfo.valueListOf(needItem);
            gainItemInfo = ItemInfo.valueListOf(gainItem);
        } catch (Exception e) {
            HawkException.catchException(e);
            return false;
        }
        return true;
    }

    @Override
    protected final boolean checkValid() {
        return super.checkValid();
    }

    public int getId() {
        return id;
    }


    public int getTimes() {
        return times;
    }

    public int getRefreshType() {
        return refreshType;
    }

    public List<ItemInfo> getNeedItemInfo() {
        return needItemInfo;
    }

    public List<ItemInfo> getGainItemInfo() {
        return gainItemInfo;
    }

    public String getNeedItem() {
        return needItem;
    }

    public String getGainItem() {
        return gainItem;
    }
}
