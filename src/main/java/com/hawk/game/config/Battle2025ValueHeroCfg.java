package com.hawk.game.config;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

/**
 *
 *
 * @author XmlToJavaConverter Tool
 * @since 2025-09-24
 */
@HawkConfigManager.XmlResource(file = "xml/battle2025ValueHero.xml")
public class Battle2025ValueHeroCfg extends HawkConfigBase {
     // auto generated fields define begin
    @Id
    private final int id;
    private final int baseValue;
    private final int levelValue;
    private final int starValue;
    // auto generated fields define end

   // assemble fields define start

   // assemble fields define end

    public Battle2025ValueHeroCfg() {
        // auto generated fields init begin
        this.id = 0;
            
        this.baseValue = 0;
            
        this.levelValue = 0;
            
        this.starValue = 0;
            
        // auto generated fields init end

        // assemble fields init start

        // assemble fields init end
    }
    
    public int getId() {
        return id;
    }
    
    public int getBaseValue() {
        return baseValue;
    }
    
    public int getLevelValue() {
        return levelValue;
    }
    
    public int getStarValue() {
        return starValue;
    }
    

    @Override
    protected boolean assemble() {
        // TODO extra assemble
        return super.assemble();
    }

    @Override
    protected boolean checkValid() {
        // TODO extra checkValid
        return super.checkValid();
    }

}