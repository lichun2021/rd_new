package com.hawk.game.module.armour.cfg;

import com.hawk.game.util.GsConst;
import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

/**
 * 不朽重塑属性计算线性表
 * @author zhy
 *
 */
@HawkConfigManager.XmlResource(file = "xml/armour_immort_random_value.xml")
public class ArmourImmortRandomValueCfg extends HawkConfigBase {
    /**
     * id
     */
    @Id
    protected final int id;
    protected final int cost;
    protected final int peak;
    protected final int left;
    protected final int right;
    protected final int base;
    protected final int down;
    protected final int up;

    public ArmourImmortRandomValueCfg() {
        id = 0;
        peak = 0;
        left = 0;
        right = 0;
        base = 0;
        down = 0;
        up = 0;
        cost = 0;
    }

    @Override
    protected boolean assemble() {
        return super.assemble();
    }

    @Override
    protected boolean checkValid() {
        return super.checkValid();
    }

    public int getId() {
        return id;
    }

    public double getPeak() {
        return peak * GsConst.EFF_PER;
    }

    public double getLeft() {
        return left * GsConst.EFF_PER;
    }

    public double getRight() {
        return right * GsConst.EFF_PER;
    }

    public double getBase() {
        return base * GsConst.EFF_PER;
    }

    public double getDown() {
        return down * GsConst.EFF_PER;
    }

    public double getUp() {
        return up * GsConst.EFF_PER;
    }

    public int getCost() {
        return cost;
    }
}
