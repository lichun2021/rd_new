package com.hawk.game.module.armour;

import com.hawk.game.cfgElement.ArmourImmortEffObject;
import com.hawk.game.cfgElement.EffectObject;
import com.hawk.game.config.ArmourCfg;
import com.hawk.game.config.ArmourConstCfg;
import com.hawk.game.entity.ArmourEntity;
import com.hawk.game.entity.CommanderEntity;
import com.hawk.game.item.ConsumeItems;
import com.hawk.game.item.ItemInfo;
import com.hawk.game.module.armour.cfg.ArmourImmortEchoCfg;
import com.hawk.game.module.armour.cfg.ArmourImmortLevelCfg;
import com.hawk.game.module.armour.cfg.ArmourImmortRandomAttrCfg;
import com.hawk.game.module.armour.cfg.ArmourImmortRandomValueCfg;
import com.hawk.game.module.armour.entity.ArmourImmortCompEntity;
import com.hawk.game.module.armour.entity.ArmourImmortPerfectPity;
import com.hawk.game.module.armour.entity.ArmourImmortReshapeLevel;
import com.hawk.game.player.Player;
import com.hawk.game.player.equip.CommanderObject;
import com.hawk.game.protocol.Armour;
import com.hawk.game.protocol.Armour.ArmourImmortCoreAward;
import com.hawk.game.protocol.Armour.ArmourImmortReshapeResp;
import com.hawk.game.protocol.Const;
import com.hawk.game.protocol.HP;
import com.hawk.game.protocol.Status;
import com.hawk.game.util.BuilderUtil;
import com.hawk.game.util.GameUtil;
import com.hawk.game.util.LogUtil;
import com.hawk.game.world.service.WorldPointService;
import com.hawk.log.Action;
import com.hawk.log.LogConst;
import com.hawk.serialize.string.SerializeHelper;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.log.HawkLog;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkRand;
import org.hawk.os.HawkWeightFactor;
import org.hawk.uuid.HawkUUIDGenerator;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 不朽组件
 *
 * @author zhy
 *
 */
public class ArmourImmortComp {
    private final ArmourImmortCompEntity armourImmortEntity = new ArmourImmortCompEntity();
    private ArmourEntity armourEntity;

    public ArmourImmortCompEntity getArmourImmortEntity() {
        return armourImmortEntity;
    }

    /**
     * 序列化
     *
     * @param str
     */
    public String serialize() {
        return armourImmortEntity.serializ();
    }

    public ArmourEntity getArmourEntity() {
        return armourEntity;
    }

    public void setArmourEntity(ArmourEntity armourEntity) {
        this.armourEntity = armourEntity;
    }

    /**
     * 反序列化
     */
    public static ArmourImmortComp create(ArmourEntity entity) {
        ArmourImmortComp immortComp = new ArmourImmortComp();
        immortComp.setArmourEntity(entity);
        immortComp.getArmourImmortEntity().mergeFrom(entity.getImmortInfo());
        return immortComp;
    }

    /**
     * 检查玩家是否在所有7个部位上都拥有可超纬重铸的装备
     */
    public boolean checkPosArmourAll(Player player) {
        int foundMask = 0;
        final int targetCount = ArmourCfg.POS7; // 7
        for (ArmourEntity entity : player.getData().getArmourEntityList()) {
            if (!entity.getImmort().canImmortRedLevel()) {
                continue;
            }
            ArmourCfg cfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class, entity.getArmourId());
            if (cfg != null) {
                foundMask |= (1 << cfg.getPos());
                if (Integer.bitCount(foundMask) >= targetCount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * clone装备
     *
     * @param source
     * @return
     */
    private ArmourEntity createArmourFrom(ArmourEntity source) {
        ArmourEntity newArmour = new ArmourEntity();
        newArmour.setId(HawkUUIDGenerator.genUUID());
        newArmour.setPlayerId(source.getPlayerId());
        newArmour.setArmourId(source.getArmourId());
        newArmour.setQuality(ArmourConstCfg.getInstance().getImmortQualityLimit() - 1);
        newArmour.setLevel(ArmourConstCfg.getInstance().getQuantumRedLevel());
        newArmour.setStar(source.getStar());
        newArmour.setQuantum(source.getQuantum());
        newArmour.getExtraAttrEff().addAll(source.getExtraAttrEff());
        newArmour.getStarEff().addAll(source.getStarEff());
        newArmour.getSkillEff().addAll(source.getSkillEff());
        newArmour.setLock(false);
        return newArmour;
    }

    /**
     * 根据源装备实体, 创建一件新的不朽核心装备.
     * 该方法只负责创建对象, 不处理持久化和客户端同步.
     *
     * @param sourceArmour 用于生成核心的源装备.
     * @return 新创建的不朽核心 ArmourEntity.
     */
    public ArmourEntity createCoreFrom(ArmourEntity sourceArmour) {
        ArmourEntity coreArmour = createArmourFrom(sourceArmour);
        ArmourImmortComp coreImmortComp = new ArmourImmortComp();
        coreImmortComp.setArmourEntity(coreArmour);
        coreImmortComp.getArmourImmortEntity().setImmortCore(true);
        coreArmour.setImmort(coreImmortComp);
        return coreArmour;
    }

    /**
     * 获取战力最高的不朽装备
     *
     * @param player
     * @return
     */
    private ArmourEntity getMaxPowerArmour(Player player) {
        List<ArmourEntity> armourEntities = player.getData().getArmourEntityList();
        List<ArmourEntity> bestArmour = armourEntities.stream()
                .filter(v -> v.getImmort().canImmortRedLevel() && v.getArmourId() == armourEntity.getArmourId())
                .collect(Collectors.toList());
        return bestArmour.stream()
                .max(Comparator.comparingInt(GameUtil::getArmourPower))
                .orElse(this.armourEntity);
    }

    /**
     * 返还不朽核心
     * 如果有相同armourId，不返还
     *
     * @param player
     * @return
     */
    public ArmourEntity createInfinityCore(Player player) {
        List<ArmourEntity> armourEntities = player.getData().getArmourEntityList();
        Optional<ArmourEntity> coreArmour = armourEntities.stream()
                .filter(v -> v.getArmourId() == armourEntity.getArmourId() && v.getImmort().isImmortCore()).findAny();
        if (coreArmour.isPresent()) {
            HawkLog.logPrintln("createInfinityCore same armourId and soldierType already have  playerId:{},armour:{}",
                    player.getId(), coreArmour.get().getId());
            return null;
        }
        List<ArmourEntity> bestArmour = armourEntities.stream()
                .filter(v -> v.getImmort().canImmortRedLevel() && v.getArmourId() == armourEntity.getArmourId())
                .collect(Collectors.toList());
        ArmourEntity sourceArmour = bestArmour.stream()
                .max(Comparator.comparingInt(GameUtil::getArmourPower))
                .orElse(this.armourEntity);
        HawkLog.logPrintln("createInfinityCore playerId:{},source:{}", player.getId(), sourceArmour.getId());
        return createCoreFrom(sourceArmour);
    }

    /**
     *
     */
    private void confirmAttrRand(Player player) {
        Map<Integer, ArmourImmortEffObject> infinityEffMap = armourImmortEntity.getInfinityEffMap();
        Map<Integer, ArmourImmortEffObject> pendingInfinityEffMap = armourImmortEntity.getPendingInfinityEffMap();

        for (ArmourImmortEffObject pendingEff : pendingInfinityEffMap.values()) {
            if (!pendingEff.isLock()) {
                continue;
            }
            ArmourImmortEffObject effObject = infinityEffMap.get(pendingEff.getAttrId());
            if (effObject != null && pendingEff.getEffectValue() < effObject.getEffectValue()) {
                pendingEff.setEffectValue(effObject.getEffectValue());
            }
        }
        infinityEffMap.clear();
        infinityEffMap.putAll(pendingInfinityEffMap);
        pendingInfinityEffMap.clear();

        logArmourImmortRand(player, 0, 0, 0, 0, true);
        notifyUpdate(player);
    }

    /**
     * 不朽重塑
     * 1. 锁定的符文属性种类不会被重塑，加成数值会被重塑，需要额外消耗【锁定道具】，锁定的符文属性越多消耗的【锁定道具】越多，最多可锁定两条符文属性
     * 2. 完美品质的符文属性加成数值不会被重塑
     * 3. 多次重塑会提升重塑等级，重塑等级越高重塑后的属性值越好
     *
     * @param player
     */
    public void attrRandom(Player player, int protocolType, boolean confirm, boolean clear) {
        if (!canImmortRedLevel()) {
            player.sendError(protocolType, Status.ArmourError.ARMOUR_IMMORT_PRE_LEVEL_ERROR, 0);
            return;
        }
        if (clear) {
            armourImmortEntity.getPendingInfinityEffMap().clear();
            notifyUpdate(player);
            return;
        }
        if (confirm && !armourImmortEntity.getPendingInfinityEffMap().isEmpty()) {
            confirmAttrRand(player);
            return;
        }
        ArmourCfg armourCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class,
                this.armourEntity.getArmourId());
        if (armourCfg == null) {
            player.sendError(protocolType, Status.ArmourError.ARMOUR_CONFIG_NOT_FOUND_VALUE, 0);
            return;
        }
        int pos = armourCfg.getPos();
        CommanderEntity commanderEntity = player.getData().getCommanderEntity();
        CommanderObject commanderObject = commanderEntity.getCommanderObject();
        ArmourImmortReshapeLevel immortReshapeLevel = commanderObject.getReshapeLevel(pos);
        int reshapeLevel = immortReshapeLevel.getLevel() + 1;

        ArmourImmortRandomValueCfg currValueCfg = HawkConfigManager.getInstance()
                .getConfigByKey(ArmourImmortRandomValueCfg.class, reshapeLevel);
        if (currValueCfg == null) {
            player.sendError(protocolType, Status.ArmourError.ARMOUR_CONFIG_NOT_FOUND_VALUE, 0);
            return;
        }
        List<Integer> attrForPerfect = new ArrayList<>();
        int lockNum = 0;
        int allPerfect = 0;
        for (ArmourImmortEffObject immortEffObject : armourImmortEntity.getInfinityEffMap().values()) {
            if (immortEffObject.isLock()) {
                lockNum++;
            }
            ArmourImmortRandomAttrCfg randomCfg = HawkConfigManager.getInstance()
                    .getConfigByKey(ArmourImmortRandomAttrCfg.class, immortEffObject.getAttrId());
            if (immortEffObject.getEffectValue() < randomCfg.getEffectValueMax()) {
                attrForPerfect.add(immortEffObject.getAttrId());
                continue;
            }
            allPerfect++;
        }
        List<ItemInfo> lockAttrItem = ArmourConstCfg.getInstance().getLockRandomAttr1Item();
        List<ItemInfo> resItems = new ArrayList<>(ArmourConstCfg.getInstance().getRandomConsumeItem());
        if (lockNum >= 1 && !lockAttrItem.isEmpty()) {
            resItems.add(lockAttrItem.get(lockNum - 1));
        }
        ConsumeItems consume = ConsumeItems.valueOf();
        consume.addConsumeInfo(resItems);
        if (!consume.checkConsume(player)) {
            return;
        }
        consume.consumeAndPush(player, Action.ARMOUR_IMMORT_RAND_ATTR);
        int afterLevel = reshapeLevel + 1;
        ArmourImmortRandomValueCfg valueCfg = HawkConfigManager.getInstance()
                .getConfigByKey(ArmourImmortRandomValueCfg.class, afterLevel);
        if (valueCfg != null) {
            immortReshapeLevel.immortLevelUp(reshapeLevel, valueCfg.getCost());
        }
        ArmourImmortPerfectPity pityState = commanderObject.getPerfectPity(pos);
        boolean isPerfectTriggered = pityState.checkPerfect(allPerfect == ArmourImmortLevelCfg.getMaxAttr());

        Map<Integer, ArmourImmortEffObject> pendingMap = armourImmortEntity.getPendingInfinityEffMap();
        pendingMap.clear();
        int randEff = HawkRand.randInt(0, attrForPerfect.isEmpty() ? 0 : attrForPerfect.size() - 1);
        int perfectAttrIndex = isPerfectTriggered ? attrForPerfect.get(randEff) : -1;
        for (ArmourImmortEffObject effObject : armourImmortEntity.getInfinityEffMap().values()) {
            ArmourImmortRandomAttrCfg randomCfg = HawkConfigManager.getInstance()
                    .getConfigByKey(ArmourImmortRandomAttrCfg.class, effObject.getAttrId());
            if (randomCfg == null) {
                continue;
            }
            int finalValue;
            boolean isPerfectItem = perfectAttrIndex == effObject.getAttrId();
            if (isPerfectItem || effObject.getEffectValue() >= randomCfg.getEffectValueMax()) {
                finalValue = randomCfg.getEffectValueMax();
                effObject.setEffectValue(finalValue);
            } else {
                finalValue = getAttribute(randomCfg, reshapeLevel).getEffectValue();
            }
            if (effObject.isLock()) {
                ArmourImmortEffObject lockEffObj = new ArmourImmortEffObject();
                lockEffObj.setAttrId(effObject.getAttrId());
                lockEffObj.setEffectType(effObject.getEffectType());
                lockEffObj.setLock(effObject.isLock());
                lockEffObj.setEffectValue(finalValue);
                pendingMap.put(lockEffObj.getAttrId(), lockEffObj);
            } else {
                if (effObject.getEffectValue() >= randomCfg.getEffectValueMax()) {
                    isPerfectItem = true;
                }
                Set<Integer> pending = pendingMap.values().stream().map(ArmourImmortEffObject::getAttrId)
                        .collect(Collectors.toSet());
                Optional<ArmourImmortEffObject> randEffOpt = addExtraRand(armourImmortEntity.getInfinityEffMap(),
                        pending, reshapeLevel, false, isPerfectItem);
                randEffOpt.ifPresent(eff -> pendingMap.put(eff.getAttrId(), eff));
            }
        }
        commanderEntity.notifyUpdate();
        ArmourImmortReshapeResp.Builder builder = ArmourImmortReshapeResp.newBuilder();
        builder.addReshapeInfo(immortReshapeLevel.build());
        player.sendProtocol(HawkProtocol.valueOf(HP.code2.EUQUIP_IMMORT_RESHAPE_RESP, builder));
        HawkLog.logPrintln("attrRandom playerId:{},armourId:{},pityState:{},reshapeLevel:{},perfectAttrIndex:{}",
                player.getId(), armourEntity.getId(), pityState, immortReshapeLevel, perfectAttrIndex);
        // 打点
        logArmourImmortRand(player, immortReshapeLevel.getPos(), immortReshapeLevel.getLevel(),
                immortReshapeLevel.getPos(), lockNum, false);
        notifyUpdate(player);
    }

    /**
     * 不朽装备生成额外属性包括洗炼
     *
     * @param infinityEffMap 额外属性
     * @param pendingSet     心怡属性attrId
     * @param level          洗炼等级
     * @param isUpgrade      是否升级获得额外属性
     * @param isPerfect      是否是完美
     * @return 返回optional
     */
    private Optional<ArmourImmortEffObject> addExtraRand(Map<Integer, ArmourImmortEffObject> infinityEffMap,
                                                         Set<Integer> pendingSet, int level, boolean isUpgrade, boolean isPerfect) {
        Set<Integer> alreadyChargeType = new HashSet<>();
        for (ArmourImmortEffObject effObject : infinityEffMap.values()) {
            ArmourImmortRandomAttrCfg attrCfg = HawkConfigManager.getInstance()
                    .getConfigByKey(ArmourImmortRandomAttrCfg.class, effObject.getAttrId());
            if (attrCfg == null) {
                continue;
            }
            pendingSet.add(attrCfg.getId());
            alreadyChargeType.add(attrCfg.getChargingLabel());
        }

        HawkWeightFactor<ArmourImmortRandomAttrCfg> upgradePool = new HawkWeightFactor<>();
        ConfigIterator<ArmourImmortRandomAttrCfg> attrCfgList = HawkConfigManager.getInstance()
                .getConfigIterator(ArmourImmortRandomAttrCfg.class);
        for (ArmourImmortRandomAttrCfg randomCfg : attrCfgList) {
            if (alreadyChargeType.contains(randomCfg.getChargingLabel())) {
                continue;
            }
            if (pendingSet.contains(randomCfg.getId())) {
                continue;
            }
            upgradePool.addWeightObj(isUpgrade ? randomCfg.getUpgradeWeight() : randomCfg.getRandomWeight(), randomCfg);
        }
        try {
            ArmourImmortRandomAttrCfg randAttrCfg = upgradePool.randomObj();
            ArmourImmortEffObject randEff = new ArmourImmortEffObject(randAttrCfg.getId(), randAttrCfg.getEffectId());
            if (isPerfect) {
                randEff.setEffectValue(randAttrCfg.getEffectValueMax());
            } else {
                randEff.setEffectValue(getAttribute(randAttrCfg, level).getEffectValue());
            }
            return Optional.of(randEff);
        } catch (Exception e) {
            HawkLog.errPrintln("addExtraRand failed upgradePool empty isUpgrade:{} level:{} isPerfect:{} chargeType:{} pending:{}", isUpgrade, level, isPerfect, SerializeHelper.collectionToString(alreadyChargeType), SerializeHelper.collectionToString(pendingSet));
            HawkException.catchException(e);
        }
        return Optional.empty();
    }

    private EffectObject getAttribute(ArmourImmortRandomAttrCfg randomAttrCfg, int reshapeLevel) {
        ArmourImmortRandomValueCfg randomValueCfg = HawkConfigManager.getInstance()
                .getConfigByKey(ArmourImmortRandomValueCfg.class, reshapeLevel);
        double l = randomValueCfg != null ? randomValueCfg.getLeft() : 2.0; // e.g., 2.0
        double r = randomValueCfg != null ? randomValueCfg.getRight() : 2.0; // e.g., 2.0
        double b = randomValueCfg != null ? randomValueCfg.getBase() : 0.1; // e.g., 0.1

        double p = randomValueCfg != null ? randomValueCfg.getPeak() : 0.5; // Default P
        double d = randomValueCfg != null ? randomValueCfg.getDown() : 0.0; // Default D
        double u = randomValueCfg != null ? randomValueCfg.getUp() : 1.0; // Default U

        double washEffectValue = generatePeakedRandom(p, l, r, b, d, u);
        int valueIndex = (int) Math.floor(randomAttrCfg.getNumSteps() * washEffectValue);
        int finalValue = valueIndex * randomAttrCfg.getEffectValueStep();

        finalValue = Math.max(randomAttrCfg.getEffectValueStep(), finalValue);

        return new EffectObject(randomAttrCfg.getEffectId(), finalValue);
    }

    /**
     * Generates a non-uniform random number biased towards a peak.
     *
     * @param p Peak position [0,1]
     * @param l Left curve strength [1,∞)
     * @param r Right curve strength [1,∞)
     * @param b Base noise factor [0,1)
     * @param d Target range minimum
     * @param u Target range maximum
     * @return A random number within [d, u]
     */
    private double generatePeakedRandom(double p, double l, double r, double b, double d, double u) {
        double uniformU = 0;
        try {
            uniformU = HawkRand.randFloat(0f, 1f);
            double x;
            if (uniformU < p) {
                x = (p > 0) ? p * Math.pow(uniformU / p, 1 / l) : 0;
            } else {
                x = (p < 1) ? 1.0 - (1.0 - p) * Math.pow((1.0 - uniformU) / (1.0 - p), 1 / r) : 1;
            }
            double y = (HawkRand.randFloat(0f, 1f) < b) ? HawkRand.randFloat(0f, 1f) : x;

            double out = d + (u - d) * y;
            return Math.max(d, Math.min(u, out));
        } catch (HawkException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 洗炼日志
     *
     * @param player
     */
    private void logArmourImmortRand(Player player, int pos, int level, int exp, int lockNum, boolean confirm) {
        try {
            LogUtil.logArmourImmortRand(player,
                    armourEntity.getArmourId(),
                    pos,
                    level,
                    exp,
                    lockNum,
                    SerializeHelper.collectionToString(armourImmortEntity.getInfinityEffMap().values().stream()
                                    .map(ArmourImmortEffObject::getAttrId).collect(Collectors.toSet()),
                            SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT),
                    SerializeHelper.collectionToString(armourImmortEntity.getPendingInfinityEffMap().values().stream()
                                    .map(ArmourImmortEffObject::getAttrId).collect(Collectors.toSet()),
                            SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT),
                    confirm,
                    armourEntity.getId());
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    /**
     * 锻造
     *
     * @param player
     */
    public void logArmourImmortIntensify(Player player, int echoId) {
        try {
            LogUtil.logArmourImmortChange(player,
                    armourEntity.getArmourId(),
                    armourImmortEntity.isImmortCore(),
                    armourImmortEntity.getInfinityLevel(),
                    echoId,
                    SerializeHelper.collectionToString(armourImmortEntity.getInfinityEffMap().values().stream()
                                    .map(ArmourImmortEffObject::getAttrId).collect(Collectors.toSet()),
                            SerializeHelper.BETWEEN_ITEMS, SerializeHelper.ATTRIBUTE_SPLIT),
                    armourEntity.getId());
        } catch (Exception e) {
            HawkException.catchException(e);
        }
    }

    /**
     * @return Map<Const.EffType, Integer>
     */
    public Map<Const.EffType, Integer> mergeEffect() {
        Map<Const.EffType, Integer> immortEffect = new HashMap<>();
        ArmourImmortLevelCfg levelCfg = HawkConfigManager.getInstance().getCombineConfig(ArmourImmortLevelCfg.class,
                armourImmortEntity.getInfinityLevel(), armourEntity.getArmourId());
        if (levelCfg == null) {
            return immortEffect;
        }
        for (EffectObject effectObject : levelCfg.getNormalAttr()) {
            immortEffect.merge(effectObject.getType(), effectObject.getEffectValue(), Integer::sum);
        }
        for (Map.Entry<Integer, ArmourImmortEffObject> armourEffObjectEntry : armourImmortEntity.getInfinityEffMap()
                .entrySet()) {
            ArmourImmortEffObject armourEffObject = armourEffObjectEntry.getValue();
            immortEffect.merge(armourEffObject.getType(), armourEffObject.getEffectValue(), Integer::sum);
        }
        return immortEffect;
    }

    public int power() {
        int power = 0;
        if (armourImmortEntity.isImmortCore()) {
            return power;
        }
        // 不朽战力
        for (ArmourImmortEffObject immortEffObject : armourImmortEntity.getInfinityEffMap().values()) {
            ArmourImmortRandomAttrCfg randomCfg = HawkConfigManager.getInstance()
                    .getConfigByKey(ArmourImmortRandomAttrCfg.class, immortEffObject.getAttrId());
            power += (int) (Math.floor((double) immortEffObject.getEffectValue() / randomCfg.getEffectValueMax()
                    * randomCfg.getMaxPower()));
        }
        ArmourImmortLevelCfg armourImmortLevelCfg = HawkConfigManager.getInstance().getCombineConfig(
                ArmourImmortLevelCfg.class, armourImmortEntity.getInfinityLevel(), armourEntity.getArmourId());
        if (armourImmortLevelCfg != null) {
            power += armourImmortLevelCfg.getArmourCombat();
        }
        return power;
    }

    public void notifyUpdate(Player player) {
        armourEntity.notifyUpdate();
        // 推单条装备信息
        player.getPush().syncArmourInfo(armourEntity);
        // 刷新作用号
        player.getEffect().resetEffectArmour(player);
        // 刷新战力
        player.refreshPowerElectric(LogConst.PowerChangeReason.ARMOUR_CHANGE);
    }

    private ArmourImmortCoreAward.Builder awardImmortCore(ArmourEntity armour, ArmourEntity newArmour, int echoId) {
        ArmourImmortCoreAward.Builder builder = ArmourImmortCoreAward.newBuilder();
        Armour.ArmourInfo.Builder armourInfo = BuilderUtil.genArmourInfoBuilder(armour);
        builder.addArmourInfo(armourInfo);
        if (newArmour != null) {
            Armour.ArmourInfo.Builder newArmourBuilder = BuilderUtil.genArmourInfoBuilder(newArmour);
            builder.addArmourInfo(newArmourBuilder);
        }
        builder.setEchoId(echoId);
        return builder;
    }

    /**
     * 返还不朽装备
     *
     * @param source
     * @param echoId
     */
    private ArmourEntity cloneArmour(ArmourEntity source, int echoId) {
        ArmourEntity newArmour = new ArmourEntity();
        newArmour.setId(HawkUUIDGenerator.genUUID());
        newArmour.setPlayerId(source.getPlayerId());
        newArmour.setArmourId(source.getArmourId());
        newArmour.setQuality(source.getQuality());
        newArmour.setLevel(source.getLevel());
        newArmour.setStar(source.getStar());
        newArmour.setQuantum(source.getQuantum());
        newArmour.getExtraAttrEff().clear();
        newArmour.getExtraAttrEff().addAll(source.getExtraAttrEff());
        newArmour.getStarEff().clear();
        newArmour.getStarEff().addAll(source.getStarEff());
        newArmour.getSkillEff().clear();
        newArmour.getSkillEff().addAll(source.getSkillEff());
        newArmour.getImmort().getArmourImmortEntity()
                .setInfinityLevel(source.getImmort().getArmourImmortEntity().getInfinityLevel());
        newArmour.getImmort().getArmourImmortEntity().setEchoId(echoId);
        newArmour.getImmort().getArmourImmortEntity().getPendingInfinityEffMap().clear();
        newArmour.getImmort().getArmourImmortEntity().getInfinityEffMap().clear();
        for (ArmourImmortEffObject effObject : source.getImmort().getArmourImmortEntity().getInfinityEffMap()
                .values()) {
            ArmourImmortEffObject tempEff = new ArmourImmortEffObject(effObject.getAttrId(), effObject.getEffectType(),
                    effObject.getEffectValue());
            newArmour.getImmort().getArmourImmortEntity().getInfinityEffMap().put(tempEff.getAttrId(), tempEff);
        }
        return newArmour;
    }

    /**
     * 判断新增装备是否超过装备库容量
     *
     * @return
     */
    public boolean checkCreateNewArmour(Player player, int level) {
        if (level < ArmourConstCfg.getInstance().getImmortRedLevel()) {
            return false;
        }

        List<ArmourEntity> armourEntities = player.getData().getArmourEntityList();
        boolean isMax = armourEntities.size() + 1 > ArmourConstCfg.getInstance().getArmourMaxCount();
        if (armourImmortEntity.isImmortCore()) {
            return isMax;
        } else {
            Optional<ArmourEntity> coreArmour = armourEntities.stream()
                    .filter(v -> v.getArmourId() == armourEntity.getArmourId() && v.getImmort().isImmortCore())
                    .findAny();
            if (!coreArmour.isPresent()) {
                return isMax;
            }
        }
        return false;
    }

    /**
     * 超纬重铸生成核心
     * 1.核心返还不朽装备
     * 2.非核心返还相同armourId核心
     *
     * @param player
     */
    public int createImmortCore(Player player) {
        int echoId = 0;
        try {
            if (armourImmortEntity.getInfinityLevel() >= ArmourConstCfg.getInstance().getImmortRedLevel()) {
                ArmourImmortEchoCfg echoCfg = randomEchoBySoldierType(player, armourEntity.getArmourId());
                if (echoCfg != null) {
                    echoId = echoCfg.getId();
                }
                if (armourImmortEntity.isImmortCore()) {
                    ArmourEntity source = getMaxPowerArmour(player);
                    ArmourEntity newArmour = cloneArmour(source, echoId);
                    newArmour.create();
                    player.getData().getArmourEntityList().add(newArmour);
                    player.getPush().syncArmourInfo(newArmour);
                    armourImmortEntity.setInfinityLevel(0);
                    armourImmortEntity.getInfinityEffMap().clear();
                    ArmourImmortCoreAward.Builder builder = awardImmortCore(armourEntity, newArmour, echoId);
                    player.sendProtocol(HawkProtocol.valueOf(HP.code2.ARMOUR_IMMORT_CORE_AWARD_RESP, builder));
                    HawkLog.logPrintln(
                            "createImmortCore from core with newArmour playerId:{},coreId:{},source:{},newArmourId:{}",
                            player.getId(), armourEntity.getId(), source.getId(), newArmour.getId());
                } else {
                    armourEntity.setQuality(ArmourConstCfg.getInstance().getImmortQualityLimit() + 1);
                    ArmourEntity newCore = createInfinityCore(player);
                    if (newCore != null) {
                        newCore.create();
                        player.getData().getArmourEntityList().add(newCore);
                        player.getPush().syncArmourInfo(newCore);
                        ArmourImmortCoreAward.Builder builder = awardImmortCore(newCore, null, echoId);
                        player.sendProtocol(HawkProtocol.valueOf(HP.code2.ARMOUR_IMMORT_CORE_AWARD_RESP, builder));
                        HawkLog.logPrintln("createImmortCore new Core playerId:{} source:{}", player.getId(),
                                newCore.getId());
                    }
                    armourImmortEntity.setEchoId(echoId);
                }
                //更新外显
                WorldPointService.getInstance().updateEquipImmortShow(player);
            }
        } catch (Exception e) {
            HawkLog.errPrintln("createImmortCore failed playerId:{},uuid:{},armourId:{},immortLevel:{}", player.getId(),
                    armourEntity.getId(), armourEntity.getArmourId(),
                    armourEntity.getImmort().getArmourImmortEntity().getInfinityLevel());
            HawkException.catchException(e);
        }
        return echoId;
    }

    private ArmourImmortEchoCfg randomEchoBySoldierType(Player player, int armourId) {
        ArmourCfg armourCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class, armourId);
        if (armourCfg == null) {
            return null;
        }
        int soldierType = armourCfg.getArmourSuitId();
        CommanderEntity commanderEntity = player.getData().getCommanderEntity();
        Map<Integer, List<Integer>> echoPoolsMap = commanderEntity.getCommanderObject().getImmortEchoPoolsMap();
        List<Integer> echoPool = echoPoolsMap.computeIfAbsent(soldierType,
                k -> new ArrayList<>(ArmourConstCfg.getInstance().getEchoPoolList()));
        int resultEchoId;
        if (!echoPool.isEmpty()) {
            int randomIndex = HawkRand.randInt(0, echoPool.size() - 1);
            resultEchoId = echoPool.remove(randomIndex);
        } else {
            Map<Integer, Map<Integer, Integer>> echoFloorsMap = commanderEntity.getCommanderObject()
                    .getImmortEchoFloorsMap();
            Map<Integer, Integer> echoFloorCount = echoFloorsMap.get(soldierType);

            if (echoFloorCount == null) {
                echoFloorCount = new HashMap<>();
                Set<Integer> replacementPoolSet = ArmourImmortEchoCfg.getEchoPool();
                for (int echoId : replacementPoolSet) {
                    echoFloorCount.put(echoId, 0);
                }
                echoFloorsMap.put(soldierType, echoFloorCount);
            }

            int pityTriggerEchoId = -1;
            int k = ArmourConstCfg.getInstance().getEchofloor();

            if (k > 0) {
                for (Map.Entry<Integer, Integer> entry : echoFloorCount.entrySet()) {
                    if (entry.getValue() >= k - 1) {
                        pityTriggerEchoId = entry.getKey();
                        break;
                    }
                }
            }

            if (pityTriggerEchoId != -1) {
                resultEchoId = pityTriggerEchoId;
            } else {
                List<Integer> replacementPool = new ArrayList<>(echoFloorCount.keySet());
                if (replacementPool.isEmpty()) {
                    return null;
                }
                resultEchoId = replacementPool.get(HawkRand.randInt(0, replacementPool.size() - 1));
            }
            for (int echoId : echoFloorCount.keySet()) {
                if (echoId == resultEchoId) {
                    echoFloorCount.put(echoId, 0);
                } else {
                    echoFloorCount.put(echoId, echoFloorCount.get(echoId) + 1);
                }
            }
            HawkLog.logPrintln("randomEchoBySoldierType playerId:{},pityTriggerEchoId:{},echoFloor:{},echoId:{}",
                    player.getId(), pityTriggerEchoId, SerializeHelper.mapToString(echoFloorCount), resultEchoId);
        }
        commanderEntity.notifyUpdate();
        HawkLog.logPrintln("randomEchoBySoldierType playerId:{},soldierType:{},echoPool:{},echoId:{}", player.getId(),
                soldierType, SerializeHelper.collectionToString(echoPool), resultEchoId);
        return HawkConfigManager.getInstance().getCombineConfig(ArmourImmortEchoCfg.class, soldierType, resultEchoId);
    }

    /**
     * 不朽装备添加额外属性
     * 如果是核心则父类不朽装备刷属性
     *
     * @param player
     * @param randomAttrNum
     */
    public void addImmortExtAttr(Player player, int randomAttrNum) {
        Map<Integer, ArmourImmortEffObject> infinityEffMap = armourImmortEntity.getInfinityEffMap();
        if (infinityEffMap.size() >= randomAttrNum) {
            return;
        }
        if (armourImmortEntity.isImmortCore()) {
            ArmourEntity source = getMaxPowerArmour(player);
            armourImmortEntity.getInfinityEffMap().clear();
            List<ArmourImmortEffObject> sourceEffList = source.getImmort().getArmourImmortEntity().getInfinityEffMap()
                    .values().stream().collect(Collectors.toList());
            for (int i = 0; i < randomAttrNum; i++) {
                ArmourImmortEffObject effObject = sourceEffList.get(i);
                armourImmortEntity.getInfinityEffMap().put(effObject.getAttrId(), effObject);
            }
        } else {
            Optional<ArmourImmortEffObject> randEffOpt = addExtraRand(infinityEffMap, new HashSet<>(), 1, true, false);
            randEffOpt.ifPresent(effObject -> infinityEffMap.put(effObject.getAttrId(), effObject));
        }
    }

    /**
     * 不朽重塑属性锁定
     *
     * @param effObject
     */
    public void attrLock(Player player, int attrId, boolean lock, int protocolType) {
        ArmourImmortEffObject effObject = armourImmortEntity.getInfinityEffMap().get(attrId);
        if (effObject == null) {
            player.sendError(protocolType, Status.SysError.PARAMS_INVALID, 0);
            return;
        }
        if (effObject.isLock() == lock) {
            return;
        }
        if (lock) {
            int lockNum = Math.toIntExact(armourImmortEntity.getInfinityEffMap().values().stream()
                    .filter(ArmourImmortEffObject::isLock).count());
            List<ItemInfo> lockAttrItem = ArmourConstCfg.getInstance().getLockRandomAttr1Item();
            if (lockNum >= lockAttrItem.size()) {
                player.sendError(protocolType, Status.ArmourError.ARMOUR_IMMORT_LOCK_MAX_ERROR, 0);
                return;
            }
        }
        effObject.setLock(lock);
        notifyUpdate(player);
    }

    public boolean canResolve(Player player) {
        int armourId = armourEntity.getArmourId();
        List<ArmourEntity> armourEntities = player.getData().getArmourEntityList();
        return armourEntities.stream().filter(v -> v.getArmourId() == armourId && v.getImmort().canImmortRedLevel())
                .count() > 1;
    }

    public boolean checkUnLock() {
        return ArmourConstCfg.getInstance().canLevelImmort(armourEntity.getLevel()) && !armourEntity.isSuper();
    }

    public boolean isImmortCore() {
        return armourImmortEntity.isImmortCore();
    }

    public boolean canImmortRedLevel() {
        return armourImmortEntity.getInfinityLevel() >= ArmourConstCfg.getInstance().getImmortRedLevel();
    }

    /**
     *
     * @param player
     * @return
     */
    public boolean checkCanIntensify(Player player) {
        List<ArmourEntity> armourEntities = player.getData().getArmourEntityList();
        for (ArmourEntity entity : armourEntities) {
            if (!entity.getImmort().checkUnLock()) {
                continue;
            }
            if (entity.getArmourId() != armourEntity.getArmourId()) {
                continue;
            }
            if (entity.getId().equals(armourEntity.getId())) {
                continue;
            }
            if (entity.getImmort().getArmourImmortEntity().isIntensify()) {
                return true;
            }
        }
        return false;
    }
    /**
     * GM身上一件不朽
     */
    public void GmImmortSuit(Player player,int echoType) {
        if (canImmortRedLevel()) {
            return;
        }
        if (checkCanIntensify(player)) {
            return;
        }
        armourEntity.setLevel(ArmourConstCfg.getInstance().getQuantumRedLevel());
        while (armourEntity.getQuantum() < ArmourConstCfg.getInstance().getQuantumRedLevel()){
            armourEntity.addQuantum();
        }
        // 升级
        armourImmortEntity.setInfinityLevel(ArmourConstCfg.getInstance().getImmortRedLevel());
        for (int i = 0; i < 5; i++) {
            addImmortExtAttr(player, 5);
        }
        createImmortCore(player);
        if (echoType != 0) {
            int armourId = armourEntity.getArmourId();
            ArmourCfg armourCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class, armourId);
            String armourEcho = armourCfg.getArmourSuitId() + "0" + echoType;
            armourImmortEntity.setEchoId(Integer.parseInt(armourEcho));
        }
        notifyUpdate(player);
    }
    /**
     * GM一件不朽
     */
    public void GmImmort(Player player) {
        if (!checkUnLock()) {
            return;
        }
        if (canImmortRedLevel()) {
            return;
        }
        if (isImmortCore()) {
            return;
        }
        if (checkCanIntensify(player)) {
            return;
        }
        // 升级
        armourImmortEntity.setInfinityLevel(ArmourConstCfg.getInstance().getImmortRedLevel());
        for (int i = 0; i < 5; i++) {
            addImmortExtAttr(player, 5);
        }
        createImmortCore(player);
        notifyUpdate(player);
    }

    /**
     * GM指定兵种刷词条
     */
    public void GmAttr(Player player, int soldierType, String attrs) {
        if (!canImmortRedLevel()) {
            return;
        }
        ArmourCfg armourCfg = HawkConfigManager.getInstance().getConfigByKey(ArmourCfg.class,
                armourEntity.getArmourId());
        if (armourCfg == null || armourCfg.getArmourSuitId() != soldierType) {
            return;
        }
        List<Integer> attrList = SerializeHelper.stringToList(Integer.class, attrs, SerializeHelper.BETWEEN_ITEMS);
        if (attrList.size() != 5) {
            return;
        }
        armourImmortEntity.getInfinityEffMap().clear();
        for (int attrId : attrList) {
            ArmourImmortRandomAttrCfg attrCfg = HawkConfigManager.getInstance()
                    .getConfigByKey(ArmourImmortRandomAttrCfg.class, attrId);
            if (attrCfg == null) {
                continue;
            }
            ArmourImmortEffObject effObject = new ArmourImmortEffObject(attrId, attrCfg.getEffectId(),
                    attrCfg.getEffectValueMax());
            armourImmortEntity.getInfinityEffMap().put(attrId, effObject);
        }
        notifyUpdate(player);
    }
}
