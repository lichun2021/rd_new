package com.hawk.game.script;

import java.util.Map;

import org.hawk.app.HawkApp;
import org.hawk.script.HawkScript;
import org.hawk.script.HawkScriptHttpInfo;

import com.hawk.game.global.GlobalData;
import com.hawk.game.invoker.CollegeAddContributeMsg;
import com.hawk.game.msg.PlayerVitCostMsg;
import com.hawk.game.player.Player;
import com.hawk.game.protocol.Script.ScriptError;
import com.hawk.game.service.college.CollegeService;
import com.hawk.gamelib.GameConst.MsgId;

/**
 * 招募不扣费
 * 
 * http://localhost:8080/script/collegeOp?type=costVit&playerId=LLLLLL&count=100000
 * 
 * @author lwt
 */
public class CollegeTestHandler extends HawkScript {
    

    @Override
    public String action(Map<String, String> params, HawkScriptHttpInfo httpInfo) {
        String typeStr = params.get("type");
        Player player = GlobalData.getInstance().scriptMakesurePlayer(params);
		if (player == null) {
			return HawkScript.failedResponse(ScriptError.PARAMS_ERROR_VALUE, "player not found");
		}
        if(typeStr.equals("costVit")){
        	int count = Integer.parseInt(params.get("count"));
        	HawkApp.getInstance().postMsg(player, PlayerVitCostMsg.valueOf(player.getId(), count));      	
        }
        if(typeStr.equals("addCollegeExp")){
        	int count = Integer.parseInt(params.get("count"));
        	player.msgCall(MsgId.COLLEGE_CONTRIBUTE_ADD, CollegeService.getInstance(), 
					new CollegeAddContributeMsg(player.getCollegeId(),count, 0,0));
        }
        return HawkScript.successResponse("sucess");
    }

}
