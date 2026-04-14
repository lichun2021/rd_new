package com.hawk.game.battle.effect.impl.hero1119;

import java.util.HashMap;
import java.util.Map;

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

@BattleTupleType(tuple = Type.ATKFIRE)
@EffectChecker(effType = EffType.HERO_12822)
public class Checker12822 implements IChecker {
	/**
	 * 【12822】
	- 【万分比】【12822】任命在战备参谋部时，集结战斗中自身部队攻击敌方远程单位时，触发破阵打击，
	使自身本次攻击时超能攻击增加 【固定值(effect12822BaseVaule)  +XX.XX%【12822】*战备参谋部中任命兰恩的友方成员数量】（友方兰恩计数时至多取 7（effect12822CountMaxinum）个）->针对己方兵种留个内置系数effect12822SoldierAdjust
	- 战报相关
	- 于战报中隐藏
	- 不合并至精简战报中
	- 仅对集结战斗生效（包含集结进攻和集结防守）
	- 在战斗开始前判定，满足条件后本次战斗全程生效
	- 任命在战备参谋部时，集结战斗中己方部队攻击敌方远程单位时
	- 对所有友方部队生效
	- 远程部队：直升机（兵种类型=4）、突击步兵（兵种类型 = 5）、狙击兵（兵种类型 = 6）、攻城车（兵种类型 = 7）
	- 触发破阵打击，使自身本次攻击时超能攻击增加 【固定值(effect12822BaseVaule)  +XX.XX%【12822】*战备参谋部中任命兰恩的友方成员数量】
	- 作用号固定数值读取const表，字段effect12822BaseVaule
	  - 配置格式：万分比
	- 该作用号为常规外围属性加成效果，与其他作用号累加计算
	  - 即 实际属性 = 基础属性*（1 + 各类加成 +【本作用值】 * 自身兵种修正系数）
	- 己方兰恩计数时至多取 7（effect12822CountMaxinum）个）
	- 取集结己方委任英雄中携带兰恩（ID1119）的玩家数量
	- 该计数有最高值限制，读取const表，字段effect12822CountMaxinum
	  - 配置格式：绝对值
	- ->针对己方兵种留个内置系数effect12822SoldierAdjust
	- 实际针对自身各兵种类型，单独配置系数；自身兵种修正系数 读取const表，字段effect12822SoldierAdjust
	  - 配置格式：兵种类型id1_修正系数1，......兵种类型id8_修正系数8
	    - 修正系数具体配置为万分比
	- 配置格式：万分比
	 */
	@Override
	public CheckerKVResult value(CheckerParames parames) {
		int effPer = 0;
		int effNum = 0;
		if (isSoldier(parames.type) && isYuanCheng(parames.tarType) && parames.unity.getEffVal(effType()) > 0) {
			int hero1119cnt = selectPlayer(parames);
			hero1119cnt = Math.min(hero1119cnt, ConstProperty.getInstance().effect12822CountMaxinum);
			effPer = ConstProperty.getInstance().effect12822BaseVaule + parames.unity.getEffVal(effType()) * hero1119cnt;
			effPer = (int) (effPer * GsConst.EFF_PER * ConstProperty.getInstance().effect12822SoldierAdjustMap.getOrDefault(parames.type, 10000));
		}
		return new CheckerKVResult(effPer, effNum);
	}
	
	/**数值最高的玩家*/
	private int selectPlayer(CheckerParames parames) {

		Map<String, Integer> valMap = new HashMap<>();
		for (BattleUnity unity : parames.unityList) {
			if (valMap.containsKey(unity.getPlayerName())) {
				continue;
			}
			if (unity.getEffVal(effType()) > 0) {
				int effvalue = unity.getEffVal(effType());
				valMap.put(unity.getPlayerName(), effvalue);
			}
		}

		return valMap.size();
	}
}
