package com.hawk.game.util;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.hawk.os.HawkException;
import org.hawk.os.HawkRand;

public class WeightFactor<T> {
	/**
	 * 权重值
	 */
	private List<Integer> weightVals;
	/**
	 * 参与权重运算的对象
	 */
	private List<T> weightObjs;

	public WeightFactor() {
		weightVals = new LinkedList<Integer>();
		weightObjs = new LinkedList<T>();
	}

	/**
	 * 添加权重对象
	 * 
	 * @param weight
	 * @param obj
	 * @return
	 */
	public boolean addWeightObj(int weight, T obj) {
		weightVals.add(weight);
		weightObjs.add(obj);
		return true;
	}

	/**
	 * 随机权重对象
	 * 
	 * @return
	 */
	public T randomObj() {
		try {
			if (weightObjs.isEmpty()) {
				return null;
			}
			return HawkRand.randomWeightObject(weightObjs, weightVals);

		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return null;
	}

	public List<T> randomObj(int count) {
		try {
			if (weightObjs.isEmpty()) {
				return Collections.emptyList();
			}
			count = Math.min(count, weightObjs.size());
			return HawkRand.randomWeightObject(weightObjs, weightVals, count);
		} catch (Exception e) {
			HawkException.catchException(e);
		}
		return Collections.emptyList();
	}
}
