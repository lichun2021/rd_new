package com.hawk.game.config;

import java.util.List;
import java.util.stream.Collectors;

import org.hawk.config.HawkConfigBase;
import org.hawk.config.HawkConfigManager;
import org.hawk.config.iterator.ConfigIterator;
import org.hawk.os.HawkRand;

import com.hawk.game.util.RandomUtil;
import com.hawk.game.util.WeightAble;

/**
 * 玩家头像配置
 *
 * @author david
 *
 */
@HawkConfigManager.XmlResource(file = "xml/player_show.xml")
public class PlayerShowCfg extends HawkConfigBase implements WeightAble {
	
	public static final int ID_BASE = 701000;
	/**
	 * 免费
	 */
	public static final int FREE = 0;
	/**
	 * 需要购买
	 */
	public static final int PURCHASE = 1;
	/**
	 * 需要解锁天奢装扮
	 */
	public static final int LUXURY = 2;
	 
	@Id
	protected final int id;
	/**
	 * 类型type，0免费，1需要购买，2要求天奢装扮
	 */
	private final int type;
	/**
	 * 价格
	 */
	private final int price;
	/**
	 * 天奢装扮id
	 */
	private final int luxuryDressID;
	
	public PlayerShowCfg() {
		id = 0;
		type = 0;
		price = 0;
		luxuryDressID = 0;
	}

	public int getId() {
		return id;
	}

	public int getType() {
		return type;
	}

	public int getPrice() {
		return price;
	}
	
	public int getLuxuryDressID() {
		return luxuryDressID;
	}
	
	/**
	 * 新玩家随机取一个
	 */
	public static int randPlayerShow() {
		return HawkRand.randInt(0, 1) == 0 ? 0 : 10; //策划需求，新玩家从这两id里随机一个
	}
	
	public static int randmShow() {
		ConfigIterator<PlayerShowCfg> poolList = HawkConfigManager.getInstance().getConfigIterator(PlayerShowCfg.class);
		List<PlayerShowCfg> list = poolList.toList().stream().filter(e -> e.getType() == 0).collect(Collectors.toList());
		PlayerShowCfg mingCfg = RandomUtil.random(list);
		return mingCfg.getId() - ID_BASE;
	}

	@Override
	public int getWeight() {
		return 1;
	}
	
	@Override
	protected boolean checkValid() {
		if (type == LUXURY) {
			DressToolCfg cfg = HawkConfigManager.getInstance().getConfigByKey(DressToolCfg.class, luxuryDressID);
			if (cfg == null) {
				return false;
			}
		}
		return super.checkValid();
	}
}
