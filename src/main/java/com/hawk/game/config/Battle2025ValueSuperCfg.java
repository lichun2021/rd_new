package com.hawk.game.config;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

/**
 *
 *
 * @author XmlToJavaConverter Tool
 * @since 2025-09-24
 */
@HawkConfigManager.XmlResource(file = "xml/battle2025ValueSuper.xml")
public class Battle2025ValueSuperCfg extends HawkConfigBase {
     // auto generated fields define begin
    @Id
    private final int id;
    private final int levelFactor;
    // auto generated fields define end

   // assemble fields define start

   // assemble fields define end

    public Battle2025ValueSuperCfg() {
        // auto generated fields init begin
        this.id = 0;
            
        this.levelFactor = 0;
            
        // auto generated fields init end

        // assemble fields init start

        // assemble fields init end
    }
    
    public int getId() {
        return id;
    }
    
    public int getLevelFactor() {
        return levelFactor;
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