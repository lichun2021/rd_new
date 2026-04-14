package com.hawk.game.player.equip;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableList;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.entity.CommanderEntity;
import com.hawk.game.global.GlobalData;
import com.hawk.game.module.armour.entity.ArmourImmortPerfectPity;
import com.hawk.game.module.armour.entity.ArmourImmortReshapeLevel;
import com.hawk.game.player.Player;
import com.hawk.serialize.string.SerializeHelper;
import org.hawk.os.HawkOSOperator;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CommanderObject {
    /** 指挥官实体 */
    private CommanderEntity commanderEntity;

    /** 装备孔位信息 */
    private ImmutableList<EquipSlot> equipSlots;
    /** 不朽装备共鸣池 */
    private final Map<Integer, List<Integer>> immortEchoPoolsMap = new ConcurrentHashMap<>();
    /** 不朽装备共鸣保底 */
    private final Map<Integer, Map<Integer, Integer>> immortEchoFloorsMap =  new ConcurrentHashMap<>();
    /** 不朽装备重塑等级 */
    private final Map<Integer, ArmourImmortReshapeLevel> immortReshapeLevelMap =  new ConcurrentHashMap<>();
    /** 不朽装备重塑保底 */
    private final Map<Integer, ArmourImmortPerfectPity> immortPerfectPityMap =  new ConcurrentHashMap<>();

    public CommanderObject(CommanderEntity commanderEntity) {
        this.commanderEntity = commanderEntity;
    }

    public static CommanderObject create(CommanderEntity commanderEntity) {
        CommanderObject commander = new CommanderObject(commanderEntity);
        commander.init();
        commanderEntity.recordCommanderObj(commander);
        return commander;
    }

    private void loadImmortEcho() {
        String immortEcho = commanderEntity.getImmortEcho();
        JSONObject immortEchoObj = JSONObject.parseObject(immortEcho);
        if (immortEchoObj == null) {
            return;
        }
        if (immortEchoObj.containsKey("echoPool")) {
            String echoPool = immortEchoObj.getString("echoPool");
            String[] pairs = echoPool.split(";");
            for (String pair : pairs) {
                String[] kv = pair.split(":");
                if (kv.length == 2) {
                    Integer soldierType = Integer.parseInt(kv[0]);
                    List<Integer> pool = new ArrayList<>();
                    if (!kv[1].isEmpty()) {
                        String[] values = kv[1].split(",");
                        for (String value : values) {
                            pool.add(Integer.parseInt(value));
                        }
                    }
                    this.immortEchoPoolsMap.put(soldierType, pool);
                }
            }
        }
        if (immortEchoObj.containsKey("echoFloor")) {
            String echoFloor = immortEchoObj.getString("echoFloor");
            String[] pairs = echoFloor.split(";");
            for (String pair : pairs) {
                String[] kv = pair.split(":");
                if (kv.length == 2) {
                    Integer soldierType = Integer.parseInt(kv[0]);
                    Map<Integer, Integer> floorCount = new HashMap<>();
                    if (!kv[1].isEmpty()) {
                        String[] counts = kv[1].split(",");
                        for (String countPair : counts) {
                            String[] ic = countPair.split("-");
                            if (ic.length == 2) {
                                floorCount.put(Integer.parseInt(ic[0]), Integer.parseInt(ic[1]));
                            }
                        }
                    }
                    this.immortEchoFloorsMap.put(soldierType, floorCount);
                }
            }
        }
        if (immortEchoObj.containsKey("immortReshapeLevel")) {
            JSONArray reshapeLevel = immortEchoObj.getJSONArray("immortReshapeLevel");
            if (reshapeLevel != null) {
                reshapeLevel.forEach(str -> {
                    ArmourImmortReshapeLevel item = new ArmourImmortReshapeLevel();
                    item.mergeFrom(str.toString());
                    this.immortReshapeLevelMap.put(item.getPos(), item);
                });
            }
        }
        if (immortEchoObj.containsKey("immortPerfectPity")) {
            JSONArray reshapeLevel = immortEchoObj.getJSONArray("immortPerfectPity");
            if (reshapeLevel != null) {
                reshapeLevel.forEach(str -> {
                    ArmourImmortPerfectPity item = new ArmourImmortPerfectPity();
                    item.mergeFrom(str.toString());
                    this.immortPerfectPityMap.put(item.getPos(), item);
                });
            }
        }
    }

    public void init() {
        this.equipSlots = ImmutableList.copyOf(loadEquips());
        loadImmortEcho();
    }

    /**
     * 加载指挥官装备位信息
     * @return
     */
    private List<EquipSlot> loadEquips() {
        List<EquipSlot> list = new ArrayList<>();
        int slotSize = ConstProperty.getInstance().getCommanderEquipSlotSize();
        if (!HawkOSOperator.isEmptyString(commanderEntity.getEquipInfo())) {
            JSONArray arr = JSONArray.parseArray(commanderEntity.getEquipInfo());
            arr.forEach(str -> {
                EquipSlot slot = new EquipSlot(this.commanderEntity.getPlayerId());
                slot.mergeFrom((String) str);
                list.add(slot);
            });
        } else {
            for (int i = 1; i <= slotSize; i++) {
                EquipSlot slot = new EquipSlot(this.commanderEntity.getPlayerId());
                slot.setPos(i);
                slot.setEquipId("");
                list.add(slot);
            }
            commanderEntity.notifyChanged(true);
        }
        int currSize = list.size();
        // 新加孔位上限
        if (currSize < slotSize) {
            for (int i = currSize + 1; i <= slotSize; i++) {
                EquipSlot slot = new EquipSlot(this.commanderEntity.getPlayerId());
                slot.setPos(i);
                slot.setEquipId("");
                list.add(slot);
            }
            commanderEntity.notifyChanged(true);
        }
        return list;
    }

    public Player getPlayer() {
        return GlobalData.getInstance().makesurePlayer(this.commanderEntity.getPlayerId());
    }

    public ImmutableList<EquipSlot> getEquipSlots() {
        return equipSlots;
    }

    public Optional<EquipSlot> getEquipSlot(int pos) {
        return equipSlots.stream().filter(e -> e.getPos() == pos).findAny();
    }

    public Optional<EquipSlot> getEquipSlot(String equipId) {
        return equipSlots.stream().filter(e -> e.getEquipId().equals(equipId)).findAny();
    }

    public void notifyChange() {
        commanderEntity.notifyChanged(true);
    }

    public String serializEquip() {
        JSONArray equips = new JSONArray();
        equipSlots.stream().map(EquipSlot::serializ).forEach(equips::add);
        return equips.toJSONString();
    }

    public String serializeImmort() {
        JSONObject jsonObject = new JSONObject();
        String immortEchoPools = "";
        StringBuilder poolsSb = new StringBuilder();
        for (Map.Entry<Integer, List<Integer>> entry : immortEchoPoolsMap.entrySet()) {
            poolsSb.append(entry.getKey()).append(":");
            poolsSb.append(SerializeHelper.collectionToString(entry.getValue(), ","));
            poolsSb.append(";");
        }
        if (poolsSb.length() > 0) {
            immortEchoPools = poolsSb.substring(0, poolsSb.length() - 1);
        }
        jsonObject.put("echoPool", immortEchoPools);
        String immortEchoFloors = "";
        StringBuilder floorsSb = new StringBuilder();
        for (Map.Entry<Integer, Map<Integer, Integer>> entry : immortEchoFloorsMap.entrySet()) {
            floorsSb.append(entry.getKey()).append(":");
            StringBuilder innerSb = new StringBuilder();
            for (Map.Entry<Integer, Integer> innerEntry : entry.getValue().entrySet()) {
                innerSb.append(innerEntry.getKey()).append("-").append(innerEntry.getValue()).append(",");
            }
            if (innerSb.length() > 0) {
                floorsSb.append(innerSb.substring(0, innerSb.length() - 1));
            }
            floorsSb.append(";");
        }
        if (floorsSb.length() > 0) {
            immortEchoFloors = floorsSb.substring(0, floorsSb.length() - 1);
        }
        jsonObject.put("echoFloor", immortEchoFloors);
        JSONArray reshapeJson = new JSONArray();
        this.immortReshapeLevelMap.values().stream().map(ArmourImmortReshapeLevel::serializ).forEach(reshapeJson::add);
        jsonObject.put("immortReshapeLevel", reshapeJson);
        JSONArray perfectPityJson = new JSONArray();
        this.immortPerfectPityMap.values().stream().map(ArmourImmortPerfectPity::serializ).forEach(perfectPityJson::add);
        jsonObject.put("immortPerfectPity", perfectPityJson);
        return jsonObject.toString();
    }

    public boolean hasEmptySlot() {
        Optional<EquipSlot> opSlot = equipSlots.stream()
                .filter(e -> e.getEquipCfgId() == 0)
                .findFirst();
        return opSlot.isPresent();
    }

    public Map<Integer, List<Integer>> getImmortEchoPoolsMap() {
        return immortEchoPoolsMap;
    }

    public Map<Integer, Map<Integer, Integer>> getImmortEchoFloorsMap() {
        return immortEchoFloorsMap;
    }

    public Map<Integer, ArmourImmortReshapeLevel> getImmortReshapeLevelMap() {
        return immortReshapeLevelMap;
    }

    public Map<Integer, ArmourImmortPerfectPity> getImmortPerfectPityMap() {
        return immortPerfectPityMap;
    }

    public ArmourImmortReshapeLevel getReshapeLevel(int pos) {
        return immortReshapeLevelMap.computeIfAbsent(pos, k -> new ArmourImmortReshapeLevel(pos));
    }

    public ArmourImmortPerfectPity getPerfectPity(int pos) {
        return immortPerfectPityMap.computeIfAbsent(pos, k -> new ArmourImmortPerfectPity(pos));
    }

}
