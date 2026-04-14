package com.hawk.game.lianmengstarwars.worldmarch;

import java.util.function.Function;

import com.hawk.game.protocol.World.MarchEvent;

public class SWNotifyMarchEventFunc implements Function<Object, Object> {
	private ISWWorldMarch march;
	private MarchEvent eventType;

	@Override
	public Object apply(Object t) {
		march.pushMarchEvent(eventType);
		return null;
	}

	public ISWWorldMarch getMarch() {
		return march;
	}

	public void setMarch(ISWWorldMarch march) {
		this.march = march;
	}

	public MarchEvent getEventType() {
		return eventType;
	}

	public void setEventType(MarchEvent eventType) {
		this.eventType = eventType;
	}

}
