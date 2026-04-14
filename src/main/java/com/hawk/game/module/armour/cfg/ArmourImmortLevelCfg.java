package com.hawk.game.module.armour.cfg;

import com.hawk.game.cfgElement.EffectObject;
import com.hawk.game.item.ItemInfo;
import com.hawk.serialize.string.SerializeHelper;
import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 装备不朽等级表
 * @author zhy
 *
 */
@HawkConfigManager.XmlResource(file = "xml/armour_immort_level.xml")
@HawkConfigBase.CombineId(fields = {"level", "armourId"})
public class ArmourImmortLevelCfg extends HawkConfigBase {
    @Id
    protected final int id;
    protected final int armourId;
    protected final String consume;
    protected final int level;
    protected final int armourpower;
    protected final int randomAttrNu;
    protected final String normalAttr;
    protected final String resolve;
    /**
     * 基础属性
     */
    private List<EffectObject> baseAttrList;

    List<ItemInfo> consumeItem;

    /**
     * 分解获得
     */
    protected List<ItemInfo> resolveAwards;
    protected static int maxLevel = 5;

    public ArmourImmortLevelCfg() {
        id = 0;
        armourId = 0;
        consume = "";
        level = 0;
        armourpower = 0;
        randomAttrNu = 0;
        normalAttr = "";
        resolve = "";
    }

    @Override
    protected boolean assemble() {
        baseAttrList = SerializeHelper.stringToList(EffectObject.class, normalAttr, SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT, new ArrayList<>());
        consumeItem = ItemInfo.valueListOf(consume);
        this.resolveAwards = ItemInfo.valueListOf(resolve);
        maxLevel = Math.max(maxLevel,randomAttrNu);
        return super.assemble();
    }

    @Override
    protected boolean checkValid() {
        return super.checkValid();
    }

    public int getArmourId() {
        return armourId;
    }

    public List<ItemInfo> getConsume() {
        return consumeItem;
    }

    public static int getMaxAttr() {
        return maxLevel;
    }
    public int getLevel() {
        return level;
    }

    public int getArmourCombat() {
        return armourpower;
    }

    public int getRandomAttrNu() {
        return randomAttrNu;
    }

    public List<EffectObject> getNormalAttr() {
        return baseAttrList;
    }

    public String getResolve() {
        return resolve;
    }

    public List<ItemInfo> getResolveItem() {
        return resolveAwards;
    }
}
