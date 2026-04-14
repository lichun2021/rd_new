package com.hawk.game.battle.effect.impl.hero1118;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.util.concurrent.AtomicLongMap;
import com.hawk.game.battle.BattleSoldier;
import com.hawk.game.battle.BattleSoldier_1;
import com.hawk.game.config.ConstProperty;
import com.hawk.game.protocol.Const.EffType;
import com.hawk.game.protocol.Const.SoldierType;
import com.hawk.game.util.GsConst;

public class Hero1118 {

	final int CYZY = 1; // 苍翎战翼】
	final int JYBL = 2;// 【金羽壁垒】
	int xingTai = JYBL; //

	AtomicLongMap<SoldierType> effect12784Nums = AtomicLongMap.create();
	int effect12784AddFirePoint;
	private final BattleSoldier_1 soldier;

	public Hero1118(BattleSoldier_1 soldier) {
		super();
		this.soldier = soldier;
	}

	public void roundStart() {

	}

	public void roundEnd() {
		int effVal = soldier.getEffVal(EffType.HERO_12781);
		if (effVal == 0 || soldier.getBattleRound() % ConstProperty.getInstance().effect12781AtkRound != 0) {
			return;
		}

		xingTai = xingTai == CYZY ? JYBL : CYZY;
		String name = xingTai == CYZY ? "苍翎战翼】" : "【金羽壁垒】";
		soldier.addDebugLog("【12781】 {} 形态切换 -> {}", soldier.getUUID(), name);

		int round = soldier.getBattleRound();
		Map<SoldierType, Integer> killMap = new HashMap<>();
		for (BattleSoldier enemy : soldier.getTroop().getEnemyTroop().getSoldierList()) {
			if (!enemy.canBeAttack()) {
				continue;
			}
			int start = round - ConstProperty.getInstance().effect12784Round + 1;
			for (int i = start; i < round; i++) {
				int kill = enemy.getRoundKill().getOrDefault(i, 0);
				killMap.merge(enemy.getType(), kill, (v1, v2) -> v1 + v2);
			}
		}
		List<Map.Entry<SoldierType, Integer>> entryList = new ArrayList<>(killMap.entrySet());
		// 3. 按Value升序排序（Comparator.comparing）
		Collections.sort(entryList, Comparator.comparing(Map.Entry::getValue));
		Collections.reverse(entryList);

		entryList.stream().limit(ConstProperty.getInstance().effect12784Nums).forEach(e -> effect12784Nums.incrementAndGet(e.getKey()));
		effect12784AddFirePoint += ConstProperty.getInstance().effect12784AddFirePoint;

		soldier.addDebugLog("【12784】西格玛会将敌方武器数据转化为盾面流纹 {} , {} 层盾面流纹", effect12784Nums, effect12784AddFirePoint);
	}

	public int buff12784reduceHurtValPct(BattleSoldier def) {
		if (!soldier.isAlive() || soldier.getEffVal(EffType.HERO_12784) <= 0) {
			return 0;
		}
		int result = (int) (ConstProperty.getInstance().effect12784BaseVaule + soldier.getEffVal(EffType.HERO_12784) * effect12784Nums.get(def.getType())) + soldier.getEffVal(EffType.HERO_12803);
		soldier.addDebugLog("【12784】西格玛会将敌方武器数据转化为盾面流纹 {} -> 伤害减免 {} ", soldier.getUUID(), result);
		return result;
	}

	boolean triger12782;
	public int buff12782(BattleSoldier tar) {
		int effVal = soldier.getEffVal(EffType.HERO_12782) + soldier.getEffVal(EffType.HERO_12801);
		effVal = (int) (effVal * GsConst.EFF_PER * ConstProperty.getInstance().effect12782SoldierAdjustMap.getOrDefault(tar.getType(), 10000));
		boolean bfalse = xingTai == CYZY || effect12784AddFirePoint >= ConstProperty.getInstance().effect12785AtkThresholdValue4;
		if (soldier.isAlive() && effVal > 0 && bfalse && soldier.getBattleRound() % ConstProperty.getInstance().effect12782AtkRound == 0) {
			soldier.addDebugLog("【12782】{} 己方本回合伤害增加 {} ", soldier.getUUID(), effVal);
			triger12782 = true;
			return effVal;
		}
		triger12782 = false;
		return 0;
	}

	public double soulLink(BattleSoldier atkSoldier, double hurtVal) {
		boolean bfalse = xingTai == JYBL || effect12784AddFirePoint >= ConstProperty.getInstance().effect12785AtkThresholdValue4;
		if(!bfalse){
			return hurtVal;
		}
		int val = (int) (soldier.getEffVal(EffType.HERO_12783) * GsConst.EFF_PER
				* ConstProperty.getInstance().effect12783SoldierAdjustMap.getOrDefault(atkSoldier.getType(), 10000));
		if (val <= 0 || !soldier.isAlive()) {
			return hurtVal;
		}
		if (soldier.getEffVal(EffType.EFF_12081) > 0) {
			val += ConstProperty.getInstance().effect12783ExtraVaule;
		}

		int freeTank = soldier.getFreeCnt();

		final double xishou = hurtVal * GsConst.EFF_PER * val;
		
		double soulHurt = 0;
		soulHurt = xishou * (1 - GsConst.EFF_PER * ConstProperty.getInstance().effect12783BaseVaule);
		soulHurt = soldier.forceField(atkSoldier, soulHurt);
		int curCnt = soldier.getFreeCnt();
		int maxKillCnt = (int) Math.ceil(4.0f * soulHurt / soldier.getHpVal());
		maxKillCnt = Math.max(1, maxKillCnt);
		int killCnt = Math.min(maxKillCnt, curCnt);

		soldier.addDeadCnt(killCnt);
		atkSoldier.addKillCnt(soldier, killCnt);

		soldier.addDebugLog("### 【12783】金羽壁垒 {}   坦克{} 分担伤害{}->{} 死亡{}", soldier.getUUID(), freeTank,xishou, soulHurt, killCnt);
		return hurtVal - xishou;
	}

	public void attackOver(BattleSoldier defSoldier) {
		if (soldier.getEffVal(EffType.HERO_12781) > 0 && triger12782 && effect12784AddFirePoint >= ConstProperty.getInstance().effect12785AtkThresholdValue1) {
			// 狙击形态：视野转为冷色调红外成像，标记敌人热能轮廓，攻击能精准命中敌方弱点部位使其晕眩，自身攻击增加+XX.XX【12723】%，且受到攻击的敌方部队中XX.XX%数量的部队下一回合无法进行攻击(->针对敌方兵种留个内置系数effect12724SoldierAdjust)
			Debuff12785 debuff = new Debuff12785();
			debuff.round = soldier.getBattleRound() + 1;
			debuff.eff12785 = soldier.getEffVal(EffType.HERO_12785);
			defSoldier.debuff12785.put(debuff.round, debuff);
			soldier.addDebugLog("12785 战翼形态时，鹰隼虚影附带眩晕效果，触发精准打击时，{} 所有 {}部队下个回个无法攻击。", defSoldier.getUUID(), debuff.eff12785);
		}
	}

	public int debuff12786(BattleSoldier defSoldier) {
		if (xingTai == JYBL && effect12784AddFirePoint >= ConstProperty.getInstance().effect12785AtkThresholdValue2) {
			double result = (soldier.getEffVal(EffType.HERO_12786) + soldier.getEffVal(EffType.HERO_12802)) 
					* GsConst.EFF_PER
					* ConstProperty.getInstance().effect12786SoldierAdjustMap.getOrDefault(defSoldier.getType(), 10000);
			soldier.addDebugLog("12786 无视目标攻击加成 {}", result);
			return (int) result;
		}
		return 0;
	}

	public int buff12787(BattleSoldier tar) {
		if (soldier.getEffVal(EffType.HERO_12781) <= 0 || !soldier.isAlive() || effect12784AddFirePoint < ConstProperty.getInstance().effect12785AtkThresholdValue3) {
			return 0;
		}

		double result = (ConstProperty.getInstance().effect12787BaseVaule + soldier.getEffVal(EffType.HERO_12787) * effect12784Nums.get(tar.getType())) * GsConst.EFF_PER
				* ConstProperty.getInstance().effect12787SoldierAdjustMap.getOrDefault(tar.getType(), 10000);
		soldier.addDebugLog("12787 释放流纹中蕴含的武器数据，己方部队各兵种攻击、防御、生命增加 {}", result);
		return (int) result;
	}

}
