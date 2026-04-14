package com.hawk.game.battle.effect.impl.hero1118;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

import org.hawk.os.HawkException;

import com.hawk.game.battle.BattleUnity;
import com.hawk.game.battle.effect.BattleConst;
import com.hawk.game.battle.effect.BattleTupleType;
import com.hawk.game.battle.effect.BattleTupleType.Type;
import com.hawk.game.battle.effect.CheckerKVResult;
import com.hawk.game.battle.effect.CheckerParames;
import com.hawk.game.battle.effect.EffectChecker;
import com.hawk.game.battle.effect.IChecker;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;
import com.hawk.game.util.GsConst;

/**
 *【12781】
- 【万分比】【12781】集结战斗开始前，若自身出征防御坦克数量超过自身出征部队总数 50%(effect12781SelfNumLimit)且不低于集结部队总数 5%(effect12781AllNumLimit)，自身出征数量最多的防御坦克于本场战斗中获取如下效果（多个西格玛同时存在时，至多有 2 (effect12781Maxinum) 个防御坦克单位生效）;（注：需要限制红所的幻象不会触发该英雄所有效果）
- 耀芒矩阵：西格玛激活背部【耀芒矩阵】，令战翼壁垒在 战翼形态【苍翎战翼】 与 壁垒形态【金羽壁垒】 间切换。初始为金羽壁垒，每 10 （effect12781AtkRound）回合结束后自动切换形态
  - 战报相关
  - 于战报中隐藏
  - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本次战斗全程生效
  - 集结战斗开始前，若自身出征防御坦克数量超过自身出征部队总数50%
    - 数量1 = 某玩家出征携带的防御坦克（兵种类型 = 7）数量
      - 真实出征部队数量，在战斗开始前判定，即所罗门、吉迪恩、参谋技 这种战斗中改变部队数量的机制对此无影响
    - 数量2 = 集结部队的出征数量总和
      - 真实出征部队数量，在战斗开始前判定，即所罗门、吉迪恩、参谋技 这种战斗中改变部队数量的机制对此无影响
    - 数量1/数量2 >= 指定数值时生效
      - 指定数值读取const表，字段effect12781SelfNumLimit
        - 配置格式：万分比
  - 且不低于集结部队总数 5%
    - 数量1 = 某玩家出征携带的防御坦克（兵种类型 = 1）数量
      - 真实出征部队数量，在战斗开始前判定，即所罗门、吉迪恩、参谋技 这种战斗中改变部队数量的机制对此无影响
    - 数量2 = 某玩家出征数量
      - 真实出征部队数量，在战斗开始前判定，即所罗门、吉迪恩、参谋技 这种战斗中改变部队数量的机制对此无影响
    - 数量1/数量2 > 指定数量时生效
      - 指定数值读取const表，字段effect12781AllNumLimit
        - 配置格式：万分比
  - 自身出征数量最多的防御坦克在本场战斗中获得如下效果
    - 满足条件后，该作用号效果仅对玩家出征时数量最多的防御坦克（兵种类型 = 1）生效（若存在多个防御坦克数量一样且最高，取等级高的）
    - 注：若存在荣耀所罗门的幻影部队，此作用号对自身幻影部队不生效
  - （多个西格玛同时存在时，至多有 2 个防御坦克单位获得上述效果）
    - 单玩家拥有此作用号时，只能提供 1 层效果；集结中存在多个此作用号时，可以叠加
    - 效果记录在己方部队身上，数值直接叠加；限制该作用号生效层数上限
      - 注：这里限制的是生效层数上限，若集结中层数超出此上限，取作用号数值高的；即这里只能有 2 个玩家携带的作用号生效
      - 层数上限读取const表，字段effect12781Maxinum
        - 配置格式：绝对值
  - 【苍翎战翼】 与 【金羽壁垒】 间切换。初始为金羽壁垒，每 10 （effect12781AtkRound）回合结束后自动切换形态
    - 【苍翎战翼】形态触发作用号12782及关联作用号
    - 【金羽壁垒】形态触发作用号12783及关联作用号
    - 指定回合结束后，下一回合开始前进行形态切换
      - 指定数值读取const表，字段effect12781AtkRound
        - 配置格式：万分比
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.HERO_12781)
public class Checker12781 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {

		if (parames.type != SoldierType.TANK_SOLDIER_1 || !BattleConst.WarEff.MASS.check(parames.troopEffType)) {
			return CheckerKVResult.DefaultVal;
		}
		
		if (parames.unity != parames.getPlayerMaxFreeArmy(parames.unity.getPlayerId(), SoldierType.TANK_SOLDIER_1)) {
			return CheckerKVResult.DefaultVal;
		}

		Map<String, Integer> effPlayerVal = (Map<String, Integer>) parames.getLeaderExtryParam(getSimpleName());
		if (effPlayerVal == null) {
			effPlayerVal = selectPlayer(parames);
			parames.putLeaderExtryParam(getSimpleName(), effPlayerVal);
		}

		if (!effPlayerVal.containsKey(parames.unity.getPlayerId())) {
			return CheckerKVResult.DefaultVal;
		}

		int effPer = effPlayerVal.get(parames.unity.getPlayerId());
		return new CheckerKVResult(effPer, 0);
	}

	/**数值最高的玩家*/
	private Map<String, Integer> selectPlayer(CheckerParames parames) {
		Map<String, Integer> valMap = new LinkedHashMap<>();
		for (BattleUnity unity : parames.unityList) {
			if (valMap.containsKey(unity.getPlayer())) {
				continue;
			}
			int effvalue = effvalue(unity, parames);
			valMap.put(unity.getPlayerId(), effvalue);
		}

		valMap = valMap.entrySet().stream()
				.sorted(((item1, item2) -> {
					int compare = item2.getValue().compareTo(item1.getValue());
					return compare;
				}))
				.filter(ent -> ent.getValue() > 0)
				.limit(ConstProperty.getInstance().effect12781Maxinum)
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
		return valMap;
	}

	private int effvalue(BattleUnity unity, CheckerParames parames) {
		try {
			if (unity.getEffVal(effType()) == 0) {
				return 0;
			}
			String playerId = unity.getPlayerId();
			int effPer = 0;
			// 采矿车数量
			int march8cnt = parames.unitStatic.getPlayerSoldierCountMarch().get(playerId, SoldierType.TANK_SOLDIER_1);
			// 若自身出征携带不低于集结部队总数 5% 的采矿车
			if (march8cnt / parames.unitStatic.getTotalCountMarch() >= ConstProperty.getInstance().effect12781AllNumLimit * GsConst.EFF_PER) {
				// 若自身携带超过自身部队总数 50% 的采矿车
				if (march8cnt * 1D / parames.unitStatic.getPlayerArmyCountMapMarch().get(playerId) > ConstProperty.getInstance().effect12781SelfNumLimit * GsConst.EFF_PER) {
					effPer = unity.getEffVal(effType());
				}
			}
			return effPer;
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return 0;
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}
}
