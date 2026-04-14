package com.hawk.game.module.lianmengXianquhx.worldpoint;

import java.util.function.Function;

import org.hawk.os.HawkException;

import com.hawk.game.module.lianmengXianquhx.IXQHXWorldPoint;

public class XQHXWorldPointUpdateEventFunc implements Function<Object, Object> {

	private IXQHXWorldPoint point;
	private boolean delete;

	@Override
	public Object apply(Object t) {
		try {
			point.getParent().getWorldSence().updateViewPoint(point, delete);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return null;
	}

	public IXQHXWorldPoint getPoint() {
		return point;
	}

	public void setPoint(IXQHXWorldPoint point) {
		this.point = point;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

}
