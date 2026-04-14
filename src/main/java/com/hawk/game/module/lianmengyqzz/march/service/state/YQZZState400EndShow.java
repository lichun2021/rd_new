package com.hawk.game.module.lianmengyqzz.march.service.state;

import com.hawk.game.module.lianmengyqzz.march.service.YQZZConst.YQZZActivityState;
import com.hawk.game.module.lianmengyqzz.march.service.YQZZMatchService;

public class YQZZState400EndShow  extends IYQZZServiceState {
	
	public YQZZState400EndShow(YQZZMatchService parent) {
		super(parent);
	}
	
	@Override
	public void init() {
		this.getDataManager().getStateData().setState(YQZZActivityState.END_SHOW);
		this.getDataManager().getStateData().saveRedis();
	}

	@Override
	public void tick() {
	}


	@Override
	public void gmOp() {
	}

	
}
