package com.hawk.game.module.homeland.cfg;

import com.hawk.serialize.string.SerializeHelper;

import java.util.HashMap;
import java.util.Map;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.os.HawkOSOperator;

/**
 * 家园建筑类型配置
 *
 * @author zhy
 */
@HawkConfigManager.XmlResource(file = "xml/homeland_build_type.xml")
public class HomeLandBuildingTypeCfg extends HawkConfigBase {
    @Id
    protected final int buildType;

    protected final String cover;

    protected final int quality;

    protected final int maxNumber;

    protected final int maxSetNumber;
    
    /** 家园装扮攻击加成 */
	protected final String atkAttr;
	/** 家园装扮生命加成 */
	protected final String hpAttr;
	/** 家园装扮星穹护盾值 */
	protected final String shdAttr;

    protected int[] coverArea;
    // 建筑作用号
    
    private Map<Integer, Integer> atkAttrMap = new HashMap<>();
	private Map<Integer, Integer> hpAttrMap = new HashMap<>();
	private Map<Integer, Integer> shdAttrMap = new HashMap<>();

    public HomeLandBuildingTypeCfg() {
        quality = 0;
        maxNumber = 0;
        maxSetNumber = 0;
        cover = "";
        buildType = 0;
        atkAttr = "";
		hpAttr = "";
		shdAttr = "";
        coverArea = new int[2];
    }

    public int getBuildType() {
        return buildType;
    }

    @Override
    protected boolean assemble() {
        if (!HawkOSOperator.isEmptyString(cover)) {
            String[] covers = cover.split(",");
            coverArea[0] = Integer.parseInt(covers[0]);
            coverArea[1] = Integer.parseInt(covers[1]);
        }
        
        atkAttrMap = SerializeHelper.cfgStr2Map(atkAttr);
        hpAttrMap = SerializeHelper.cfgStr2Map(hpAttr);
        shdAttrMap = SerializeHelper.cfgStr2Map(shdAttr);
        return true;
    }

    public int getAtkAttr(int type) {
		return atkAttrMap.getOrDefault(type, 0);
	}

    public int getHpAttr(int type) {
    	return hpAttrMap.getOrDefault(type, 0);
    }
    
    public int getShdAttr(int type) {
    	return shdAttrMap.getOrDefault(type, 0);
    }

    public int getWidth() {
        return coverArea[0];
    }

    public int getHeight() {
        return coverArea[1];
    }

    @Override
    protected boolean checkValid() {
        return true;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public int getMaxSetNumber() {
        return maxSetNumber;
    }

    public int getQuality() {
        return quality;
    }
}
