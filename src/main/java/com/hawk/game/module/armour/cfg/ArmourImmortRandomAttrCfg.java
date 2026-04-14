package com.hawk.game.module.armour.cfg;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import java.util.HashSet;
import java.util.Set;

/**
 * 不朽重塑属性表
 * @author zhy
 *
 */
@HawkConfigManager.XmlResource(file = "xml/armour_immort_random_attribute.xml")
public class ArmourImmortRandomAttrCfg extends HawkConfigBase{
    /**
     * id
     */
    @Id
    protected final int id;
    protected final int chargingLabel;
    protected final int effectid;
    protected final int effectValueStep;
    protected final int effectValueMax;
    protected final int randomweight;
    protected final int upgradeweight;
    protected final int maxPower;
    protected final String soldierType;
    protected final int effectGroup;
    protected long numSteps;

    public ArmourImmortRandomAttrCfg() {
        id = 0;
        chargingLabel = 0;
        effectid = 0;
        randomweight = 0;
        upgradeweight = 0;
        effectValueStep = 1;
        effectValueMax = 0;
        maxPower = 0;
        soldierType = "";
        effectGroup = 0;
        numSteps = 0;
    }

    @Override
    protected boolean assemble() {
        numSteps = effectValueMax / effectValueStep;
        return super.assemble();
    }
    @Override
    protected boolean checkValid() {
        return super.checkValid();
    }

    public int getChargingLabel() {
        return chargingLabel;
    }

    public int getId() {
        return id;
    }

    public int getRandomWeight() {
        return randomweight;
    }

    public long getNumSteps() {
        return numSteps;
    }

    public int getUpgradeWeight() {
        return upgradeweight;
    }

    public int getEffectValueMax() {
        return effectValueMax;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getEffectId() {
        return effectid;
    }
    public int getEffectGroup() {
        return effectGroup;
    }

    public int getEffectValueStep() {
        return effectValueStep;
    }

    public Set<Integer> getSoldierType() {
        Set<Integer> soldierTypeSet = new HashSet<>();
        String [] tmp = soldierType.split("_");
        for(String typeStr : tmp){
            soldierTypeSet.add(Integer.parseInt(typeStr));
        }
        return soldierTypeSet;
    }
}
