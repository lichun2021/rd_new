package com.hawk.game.module.armour.cfg;

import com.hawk.game.cfgElement.EffectObject;
import com.hawk.game.util.WeightAble;
import com.hawk.serialize.string.SerializeHelper;
import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 装备不朽共鸣
 * @author zhy
 *
 */
@HawkConfigManager.XmlResource(file = "xml/armour_immort_echo.xml")
@HawkConfigBase.CombineId(fields = {"type", "echotype"})
public class ArmourImmortEchoCfg extends HawkConfigBase implements WeightAble {
    /**
     * id
     */
    @Id
    protected final int id;
    protected final String echoAttribute1;
    protected final String echoAttribute2;
    protected final String echoAttribute3;
    protected final int weight;
    protected final int type;
    protected final int echotype;
    private List<EffectObject> echoAttribute1List = new ArrayList<>();
    private List<EffectObject> echoAttribute2List = new ArrayList<>();
    private List<EffectObject> echoAttribute3List = new ArrayList<>();
    private static final Set<Integer> echoPool = new HashSet<>();
    public ArmourImmortEchoCfg() {
        id = 0;
        weight = 0;
        type = 0;
        echotype = 0;
        echoAttribute1 = "";
        echoAttribute2 = "";
        echoAttribute3 = "";
    }

    @Override
    protected boolean assemble() {
        echoAttribute1List = SerializeHelper.stringToList(EffectObject.class, echoAttribute1, SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT, new ArrayList<>());
        echoAttribute2List = SerializeHelper.stringToList(EffectObject.class, echoAttribute2, SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT, new ArrayList<>());
        echoAttribute3List = SerializeHelper.stringToList(EffectObject.class, echoAttribute3, SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT, new ArrayList<>());
        echoPool.add(echotype);
        return super.assemble();
    }

    public int getId() {
        return id;
    }

    @Override
    protected boolean checkValid() {
        return super.checkValid();
    }

    public List<EffectObject> getEchoAttribute1() {
       return echoAttribute1List;
    }

    public List<EffectObject> getEchoAttribute2() {
        return echoAttribute2List;
    }

    public List<EffectObject> getEchoAttribute3() {
        return echoAttribute3List;
    }

    public int getWeight() {
        return weight;
    }

    public int getType() {
        return type;
    }

    public int getEchoType() {
        return echotype;
    }
    public static Set<Integer> getEchoPool(){
        return echoPool;
    }
}
