package com.hawk.game.module.armour.cfg;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import java.util.HashMap;
import java.util.Map;

/**
 * 不朽重塑属性计算保底表
 * @author zhy
 *
 */
@HawkConfigManager.XmlResource(file = "xml/armour_immort_random_valuemax.xml")
public class ArmourImmortRandomMaxCfg extends HawkConfigBase {
    /**
     * id
     */
    @Id
    protected final int id;
    protected final int number;
    protected final int controlDown;
    protected final int controlUp;
    private static Map<Integer, ArmourImmortRandomMaxCfg> numberRandMap = new HashMap<>();
    public ArmourImmortRandomMaxCfg() {
        id = 0;
        number = 0;
        controlDown = 0;
        controlUp = 0;
    }

    @Override
    protected boolean assemble() {
        numberRandMap.put(number,this);
        return super.assemble();
    }


    @Override
    protected boolean checkValid() {
        return super.checkValid();
    }

    public int getId() {
        return id;
    }

    public int getNumber() {
        return number;
    }

    public int getControlDown() {
        return controlDown;
    }

    public int getControlUp() {
        return controlUp;
    }
    public static ArmourImmortRandomMaxCfg getPerfect(int perfectCount){
        if (numberRandMap.containsKey(perfectCount)) {
            return numberRandMap.get(perfectCount);
        }
        return numberRandMap.values().stream().reduce((first,second)-> second).orElse(null);
    }
}
