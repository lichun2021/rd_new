package com.hawk.game.config;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

/**
 *
 *
 * @author XmlToJavaConverter Tool
 * @since 2025-09-24
 */
@HawkConfigManager.XmlResource(file = "xml/battle2025Stage.xml")
public class Battle2025StageCfg extends HawkConfigBase {
    @Id
    private final int id;
    private final String heroList;
    private final String soldierList;
    private final int combatPower;
    private final int superSoldier;


    public Battle2025StageCfg() {
        this.id = 0;
            
        this.heroList = "";    
        this.soldierList = "";    
        this.combatPower = 0;
            
        this.superSoldier = 0;
            
    }
    
    public int getId() {
        return id;
    }
    
    public String getHeroList() {
        return heroList;
    }
    
    public String getSoldierList() {
        return soldierList;
    }
    
    public int getCombatPower() {
        return combatPower;
    }
    
    public int getSuperSoldier() {
        return superSoldier;
    }
    

    @Override
    protected boolean assemble() {
        return super.assemble();
    }

    @Override
    protected boolean checkValid() {
        return super.checkValid();
    }

}