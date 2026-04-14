package com.hawk.game.battle.effect.impl.hero1120;

import com.hawk.game.battle.BattleUnity;
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
 - 【万分比】【12831~12832】集群轰炸
  - 战报相关
    - 于战报中隐藏
    - 不合并至精简战报中
  - 在战斗开始前判定，满足条件后本场战斗全程生效
  - 完全同老作用号【12051~12052】【230817】【SSS】【空军双将】【伊娜莉莎】 【1088】 ，直接复制并将作用号id改为【12831~12832】
  - 下列常量中12051变为12831即可，12052变为12832
    - effect12051SelfNumLimit
    - effect12051AtkRound
    - effect12051AtkTimesForMass
    - effect12051AtkTimesForPerson
  - 此前老作用号【12051~12052】触发尤利娅所有作用号【241024】【SSS】【军事】【轰炸】【尤利娅】【Yulia】【1104】【12411~12414】
    - 注：新作用号【12831~12832】也触发尤利娅技能【12411~12414】
[图片]
    - 注：新作用号【12831~12832】不与伊娜莉莎技能【12051~12052】一同触发
[图片]
    - 注：新作用号【12831~12832】会受到尤利娅【12414】作用号加成（下图）
[图片]
 */
@BattleTupleType(tuple = Type.SOLDIER_SKILL)
@EffectChecker(effType = EffType.HERO_12831)
public class Checker12831 implements IChecker {
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		if (!check(parames,ConstProperty.getInstance().effect12831SelfNumLimit)) {
			return CheckerKVResult.DefaultVal;
		}

		return new CheckerKVResult(parames.unity.getEffVal(effType()), 0);
	}

	public static boolean check(CheckerParames parames,int selfNumLimit) {
		if (parames.type != SoldierType.PLANE_SOLDIER_3) {
			return false;
		}
		String playerId = parames.unity.getPlayerId();
		BattleUnity maxUnity = parames.getPlayerMaxFreeArmy(playerId, SoldierType.PLANE_SOLDIER_3);
		if (maxUnity != parames.unity) {
			return false;
		}

		int march3cnt = parames.unitStatic.getPlayerSoldierCountMarch().get(playerId, SoldierType.PLANE_SOLDIER_3);
		// 若自身出征轰炸机（兵种类型 = 3）数量超过自身出征部队总数 50%，自身出征数量最多的轰炸机单位在本场战斗中获得如下效果: 【迂回轰炸】每第 5 回合，额外向敌方远程随机 1 个单位进行 18 轮攻击（伤害率: XX.XX%+轮次数*YY.YY%；每轮攻击独立选择目标，选取目标优先遍历所有远程兵种类型）
		if (march3cnt * 1D / parames.unitStatic.getPlayerArmyCountMapMarch().get(playerId) <= selfNumLimit * GsConst.EFF_PER) {
			return false;
		}
		return true;
	}

	@Override
	public boolean tarTypeSensitive() {
		return false;
	}

}
