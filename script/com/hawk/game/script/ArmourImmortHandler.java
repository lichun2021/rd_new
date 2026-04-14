package com.hawk.game.script;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.*;

import com.hawk.game.entity.ArmourEntity;
import com.hawk.game.protocol.HP;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.net.protocol.HawkProtocol;
import org.hawk.os.HawkException;
import org.hawk.os.HawkOSOperator;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;

import com.hawk.game.GsConfig;
import com.hawk.game.global.GlobalData;
import com.hawk.game.player.Player;

/**
 * 不朽装备测试类
 * http://localhost:8080/script/armourImmort?oper=1&playerId=1aax-42ky73-1
 *
 * @author zhy
 */
public class ArmourImmortHandler extends HawkScript {

    @Override
    public String action(Map<String, String> params, HawkScriptHttpInfo httpInfo) {
        return doAction(params);
    }

    public static String doAction(Map<String, String> params) {
        if (!GsConfig.getInstance().isDebug()) {
            return HawkScript.failedResponse(SCRIPT_ERROR, "");
        }

        try {
            if (!GsConfig.getInstance().isDebug()) {
                return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "is not debug");
            }
            Player player = GlobalData.getInstance().scriptMakesurePlayer(params);
            if (player == null) {
                return HawkScript.failedResponse(HawkScript.SCRIPT_ERROR, "player not exist");
            }
            int oper = Integer.parseInt(params.getOrDefault("oper", "0"));
            int soldierType = Integer.parseInt(params.getOrDefault("soldier", "0"));
            int echo = Integer.parseInt(params.getOrDefault("echo", "0"));
            String attrs = params.getOrDefault("attrs", "1,2,3,4,5");
            List<ArmourEntity> armourEntities = player.getData().getArmourEntityList();
            if (oper == 3) {
                int armourSuit = player.getEntity().getArmourSuit();
                List<ArmourEntity> armours = player.getData().getSuitArmours(armourSuit);
                for (ArmourEntity entity : armours) {
                    entity.getImmort().GmImmortSuit(player, echo);
                }
            } else {
                for (ArmourEntity entity : armourEntities) {
                    if (oper == 1) {
                        entity.getImmort().GmAttr(player, soldierType, attrs);
                    } else if (oper == 2) {
                        entity.getImmort().GmImmort(player);
                    }
                }
            }

            return HawkScript.successResponse("ok");
        } catch (Exception e) {
            HawkException.catchException(e);
            return HawkException.formatStackMsg(e);
        }
    }
}
