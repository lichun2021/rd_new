package com.hawk.game.player.hero.rise;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hawk.os.HawkException;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hawk.game.player.hero.SerializJsonStrAble;
import com.hawk.game.protocol.Const.SoldierType;
import com.hawk.game.protocol.Hero.PBHeroRiseSetting;

/**
 * 生效设置星穷技
 */
public class RiseSkillSettings implements SerializJsonStrAble {
	private Map<SoldierType, RiseSkillPage> skillSettings = new HashMap<>();
	public final static RiseSkillSettings defaultInstance = new RiseSkillSettings();
	
	public RiseSkillPage getSkillPage(SoldierType type) {
		if (type == null) {
			return null;
		}
		if (skillSettings.containsKey(type)) {
			return skillSettings.get(type);
		}
		RiseSkillPage page = new RiseSkillPage();
		page.setType(RisePageType.getSoldierPageType(type));
		page.setSoldierType(type);
		skillSettings.put(type, page);
		return page;
	}

	public PBHeroRiseSetting toPBObj() {
		PBHeroRiseSetting.Builder builder = PBHeroRiseSetting.newBuilder();
		for (RiseSkillPage page : skillSettings.values()) {
			builder.addPages(page.toPBObj());
		}
		return builder.build();
	}

	@Override
	public String serializ() {
		JSONObject obj = new JSONObject();
		JSONArray arr = new JSONArray();
		for (RiseSkillPage page : skillSettings.values()) {
			arr.add(page.serializ());
		}
		obj.put("pages", arr);
		return obj.toJSONString();
	}

	@Override
	public void mergeFrom(String serialiedStr) {
		if (StringUtils.isEmpty(serialiedStr)) {
			return;
		}
		try {
			JSONObject obj = JSONObject.parseObject(serialiedStr);
			JSONArray arrV1 = obj.getJSONArray("page");
			if(arrV1!=null && ! arrV1.isEmpty()){
				fixV1(arrV1);
				return;
			}
			
			JSONArray arr = obj.getJSONArray("pages");
			if (arr != null) {
				for (Object str : arr) {
					RiseSkillPage page = new RiseSkillPage();
					page.mergeFrom(str.toString());
					skillSettings.put(page.getSoldierType(), page);
				}
			}
		} catch (Exception e) {
			HawkException.catchException(e, serialiedStr);
		}

	}

	// 兼容旧版本
	private void fixV1(JSONArray arrV1) {
		for (Object str : arrV1) {
			RiseSkillPage page = new RiseSkillPage();
			page.mergeFrom(str.toString());
			RiseSkillPage page2 = new RiseSkillPage();
			page2.mergeFrom(str.toString());
			switch (page.getType()) {
			case One:
				page.setSoldierType(SoldierType.TANK_SOLDIER_1);
				page2.setSoldierType(SoldierType.TANK_SOLDIER_2);
				break;
			case Two:
				page.setSoldierType(SoldierType.PLANE_SOLDIER_3);
				page2.setSoldierType(SoldierType.PLANE_SOLDIER_4);
				break;
			case Three:
				page.setSoldierType(SoldierType.FOOT_SOLDIER_5);
				page2.setSoldierType(SoldierType.FOOT_SOLDIER_6);
				break;
			case Four:
				page.setSoldierType(SoldierType.CANNON_SOLDIER_7);
				page2.setSoldierType(SoldierType.CANNON_SOLDIER_8);
				break;

			default:
				break;
			}
			skillSettings.put(page.getSoldierType(), page);
			skillSettings.put(page2.getSoldierType(), page2);
		}
	}

}
