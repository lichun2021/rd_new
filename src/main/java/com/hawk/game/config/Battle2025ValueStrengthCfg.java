package com.hawk.game.config;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;

/**
 *
 *
 * @author XmlToJavaConverter Tool
 * @since 2025-09-24
 */
@HawkConfigManager.XmlResource(file = "xml/battle2025ValueStrength.xml")
public class Battle2025ValueStrengthCfg extends HawkConfigBase {
     // auto generated fields define begin
    @Id
    private final int id;
    private final int strengthFactor;
    // auto generated fields define end

   // assemble fields define start

   // assemble fields define end

    public Battle2025ValueStrengthCfg() {
        // auto generated fields init begin
        this.id = 0;
            
        this.strengthFactor = 0;
            
        // auto generated fields init end

        // assemble fields init start

        // assemble fields init end
    }
    
    public int getId() {
        return id;
    }
    
    public int getStrengthFactor() {
        return strengthFactor;
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