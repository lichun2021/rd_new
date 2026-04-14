package com.hawk.game.module.lianmengtaiboliya.worldpoint;

import java.util.function.Function;

import org.hawk.os.HawkException;

import com.hawk.game.module.lianmengtaiboliya.ITBLYWorldPoint;

public class TBLYWorldPointUpdateEventFunc implements Function<Object, Object> {

	private ITBLYWorldPoint point;
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

	public ITBLYWorldPoint getPoint() {
		return point;
	}

	public void setPoint(ITBLYWorldPoint point) {
		this.point = point;
	}

	public boolean isDelete() {
		return delete;
	}

	public void setDelete(boolean delete) {
		this.delete = delete;
	}

}
