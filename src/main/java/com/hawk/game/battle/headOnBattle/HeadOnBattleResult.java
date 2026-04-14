package com.hawk.game.battle.headOnBattle;

/**
 * @author SJS
 * @description pve模拟战斗 战斗结果
 * @date 2025/9/25
 */
public class HeadOnBattleResult {

    /**
     * 战斗是否胜利 攻击方胜利为true 防守方胜利为false
     */
    private boolean atkWin;
    /**
     * 士兵强度
     */
    private long soldierStrength;
    /**
     * 机甲强度
     */
    private long superSoldierStrength;
    /**
     * 英雄强度
     */
    private long heroStrength;

    private long atkRealStrength;


    public HeadOnBattleResult(boolean atkWin, long soldierStrength, long superSoldierStrength, long heroStrength, long atkRealStrength) {
        this.atkWin = atkWin;
        this.soldierStrength = soldierStrength;
        this.superSoldierStrength = superSoldierStrength;
        this.heroStrength = heroStrength;
        this.atkRealStrength = atkRealStrength;
    }

    public HeadOnBattleResult(boolean atkWin) {
        this.atkWin = atkWin;
    }

    public boolean isAtkWin() {
        return atkWin;
    }

    public long getSoldierStrength() {
        return soldierStrength;
    }

    public long getSuperSoldierStrength() {
        return superSoldierStrength;
    }

    public long getHeroStrength() {
        return heroStrength;
    }

    public long getAtkRealStrength() {
        return atkRealStrength;
    }
}
