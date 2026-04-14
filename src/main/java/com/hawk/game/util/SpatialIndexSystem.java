package com.hawk.game.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 大型网格地图空间索引系统
 * 支持在1000×1000网格地图上快速查找指定点周围20单位距离内的对象
 */
public class SpatialIndexSystem {
	final Logger logger = LoggerFactory.getLogger("Server");
	// 地图配置常量
	private final int MAP_WIDTH;
	private final int MAP_HEIGHT;
	private final int DEFAULT_QUERY_RADIUS;
	private final int GRID_SIZE; // 网格划分大小

	// 计算网格行列数
	private final int GRID_COLS;
	private final int GRID_ROWS;

	// 主索引结构：网格索引
	private final List<List<GameObject>> gridIndex;

	// 对象存储
	protected final Map<Long, GameObject> allObjects = new ConcurrentHashMap<>();
	private long nextObjectId = 1;

	/**
	 * 构造函数
	 */
	public SpatialIndexSystem(int mapX, int mapY) {
		MAP_WIDTH = mapX;
		MAP_HEIGHT = mapY;
		DEFAULT_QUERY_RADIUS = 20;
		GRID_SIZE = 20; // 网格划分大小

		// 计算网格行列数
		GRID_COLS = (MAP_WIDTH + GRID_SIZE - 1) / GRID_SIZE;
		GRID_ROWS = (MAP_HEIGHT + GRID_SIZE - 1) / GRID_SIZE;

		// 初始化网格索引
		gridIndex = new ArrayList<>(GRID_COLS * GRID_ROWS);
		for (int i = 0; i < GRID_COLS * GRID_ROWS; i++) {
			gridIndex.add(new CopyOnWriteArrayList<>());
		}

	}

	protected long addObject(int x, int y, Object data) {
		return addObject(x, y, data, -1);
	}

	/**
	 * 添加游戏对象
	 * @param x X坐标
	 * @param y Y坐标
	 * @param data 对象数据
	 * @return 对象ID
	 */
	protected long addObject(int x, int y, Object data, long objId) {
		// 验证坐标
		if (x < 0 || x >= MAP_WIDTH || y < 0 || y >= MAP_HEIGHT) {
			throw new IllegalArgumentException("坐标超出地图范围: (" + x + ", " + y + ")");
		}

		long id = objId;
		if (id < 0) {
			id = nextObjectId++;
		}
		GameObject obj = new GameObject(id, x, y, data);

		// 添加到主存储
		allObjects.put(id, obj);

		// 添加到网格索引
		int gridIndexPos = calculateGridIndex(x, y);
		gridIndex.get(gridIndexPos).add(obj);

		return id;
	}

	protected GameObject getObject(long objectId) {
		return allObjects.get(objectId);
	}

	/**
	 * 移除游戏对象
	 * @param objectId 对象ID
	 * @return 是否成功移除
	 */
	protected boolean removeObject(long objectId) {
		GameObject obj = allObjects.remove(objectId);
		if (obj == null) {
			return false;
		}

		// 从网格索引移除
		int gridIndexPos = calculateGridIndex(obj.x, obj.y);
		gridIndex.get(gridIndexPos).remove(obj);

		return true;
	}

	/**
	 * 移动游戏对象
	 * @param objectId 对象ID
	 * @param newX 新X坐标
	 * @param newY 新Y坐标
	 * @return 是否成功移动
	 */
	protected boolean moveObject(long objectId, int newX, int newY) {
		// 验证坐标
		if (newX < 0 || newX >= MAP_WIDTH || newY < 0 || newY >= MAP_HEIGHT) {
			return false;
		}

		GameObject obj = allObjects.get(objectId);
		if (obj == null) {
			return false;
		}

		// 从原位置移除
		int oldGridIndex = calculateGridIndex(obj.x, obj.y);
		gridIndex.get(oldGridIndex).remove(obj);

		// 更新位置
		obj.x = newX;
		obj.y = newY;

		// 添加到新位置
		int newGridIndex = calculateGridIndex(newX, newY);
		gridIndex.get(newGridIndex).add(obj);

		return true;
	}

	/**
	 * 查找指定点周围的游戏对象
	 * @param centerX 中心点X坐标
	 * @param centerY 中心点Y坐标
	 * @param radius 搜索半径
	 * @return 周围的对象列表
	 */
	protected List<GameObject> findNearbyObjects(int centerX, int centerY, int radius) {
		// 验证输入
		if (centerX < 0 || centerX >= MAP_WIDTH || centerY < 0 || centerY >= MAP_HEIGHT) {
			logger.info("中心点坐标超出地图范围: {},{}", centerX, centerY);
		}
		if (radius <= 0) {
			return new ArrayList<>();
		}

		// 计算需要检查的网格范围
		int minGridX = Math.max(0, (centerX - radius) / GRID_SIZE);
		int maxGridX = Math.min(GRID_COLS - 1, (centerX + radius) / GRID_SIZE);
		int minGridY = Math.max(0, (centerY - radius) / GRID_SIZE);
		int maxGridY = Math.min(GRID_ROWS - 1, (centerY + radius) / GRID_SIZE);

		List<GameObject> result = new ArrayList<>();
		// 检查范围内的所有网格
		for (int gridX = minGridX; gridX <= maxGridX; gridX++) {
			for (int gridY = minGridY; gridY <= maxGridY; gridY++) {
				int gridIndexPos = gridY * GRID_COLS + gridX;
				for (GameObject obj : gridIndex.get(gridIndexPos)) {
					// 精确过滤
					double distance = calculateDistance(centerX, centerY, obj.x, obj.y);
					if (distance <= radius) {
						result.add(obj);
					}
				}
			}
		}

		return result;
	}

	/**
	 * 查找指定点周围的游戏对象（默认20单位半径）
	 * @param centerX 中心点X坐标
	 * @param centerY 中心点Y坐标
	 * @return 周围的对象列表
	 */
	protected List<GameObject> findNearbyObjects(int centerX, int centerY) {
		return findNearbyObjects(centerX, centerY, DEFAULT_QUERY_RADIUS);
	}

	/**
	 * 获取所有对象数量
	 * @return 对象总数
	 */
	public int getObjectCount() {
		return allObjects.size();
	}

	/**
	 * 清空所有对象
	 */
	protected void clearAll() {
		allObjects.clear();
		for (List<GameObject> cell : gridIndex) {
			cell.clear();
		}
	}

	/**
	 * 计算两点间距离
	 */
	private double calculateDistance(int x1, int y1, int x2, int y2) {
		int dx = x2 - x1;
		int dy = y2 - y1;
		return Math.sqrt(dx * dx + dy * dy);
	}

	/**
	 * 计算网格索引位置
	 */
	private int calculateGridIndex(int x, int y) {
		int col = x / GRID_SIZE;
		int row = y / GRID_SIZE;
		return row * GRID_COLS + col;
	}

	/**
	 * 游戏对象类
	 */
	public static class GameObject {
		public final long id;
		public int x;
		public int y;
		public final Object data;

		public GameObject(long id, int x, int y, Object data) {
			this.id = id;
			this.x = x;
			this.y = y;
			this.data = data;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null || getClass() != o.getClass())
				return false;
			GameObject that = (GameObject) o;
			return id == that.id;
		}

		@Override
		public int hashCode() {
			return Long.hashCode(id);
		}

		@Override
		public String toString() {
			return "GameObject{" +
					"id=" + id +
					", x=" + x +
					", y=" + y +
					", data=" + data +
					'}';
		}
	}

	/**
	 * 测试主方法
	 */
	public static void main(String[] args) {
		System.out.println("=== 大型网格地图空间索引系统测试 ===\n");

		// 创建系统实例
		SpatialIndexSystem system = new SpatialIndexSystem(150, 300);
		Random random = new Random(42); // 固定种子以便重现

		// 添加测试对象
		System.out.println("添加测试对象...");
		List<Long> objectIds = new ArrayList<>();

		for (int i = 0; i < 1000; i++) {
			int x = random.nextInt(system.MAP_WIDTH);
			int y = random.nextInt(system.MAP_HEIGHT);
			long id = system.addObject(x, y, "Object-" + i);
			objectIds.add(id);
		}

		System.out.println("已添加 " + system.getObjectCount() + " 个对象\n");

		// 测试查找
		System.out.println("测试查找功能...");

		// 测试点1: 地图中心
		int testX1 = system.MAP_WIDTH / 2;
		int testY1 = system.MAP_HEIGHT / 2;
		List<GameObject> nearby1 = system.findNearbyObjects(testX1, testY1, 20);
		System.out.println("中心点(" + testX1 + "," + testY1 + ") 半径20内的对象数量: " + nearby1.size());

		// 测试点2: 地图角落
		int testX2 = 50;
		int testY2 = 50;
		List<GameObject> nearby2 = system.findNearbyObjects(testX2, testY2, 20);
		System.out.println("角落点(" + testX2 + "," + testY2 + ") 半径20内的对象数量: " + nearby2.size());

		// 测试点3: 随机点
		int testX3 = random.nextInt(system.MAP_WIDTH);
		int testY3 = random.nextInt(system.MAP_HEIGHT);
		List<GameObject> nearby3 = system.findNearbyObjects(testX3, testY3, 20);
		System.out.println("随机点(" + testX3 + "," + testY3 + ") 半径20内的对象数量: " + nearby3.size());

		// 测试移动功能
		System.out.println("\n测试移动功能...");
		if (!objectIds.isEmpty()) {
			long objectToMove = objectIds.get(0);
			boolean moved = system.moveObject(objectToMove, 100, 100);
			System.out.println("移动对象 " + objectToMove + " 到 (100,100): " + (moved ? "成功" : "失败"));
		}

		// 测试移除功能
		System.out.println("\n测试移除功能...");
		if (!objectIds.isEmpty()) {
			long objectToRemove = objectIds.get(1);
			boolean removed = system.removeObject(objectToRemove);
			System.out.println("移除对象 " + objectToRemove + ": " + (removed ? "成功" : "失败"));
		}

		// 性能测试
		System.out.println("\n=== 性能测试 ===");
		int queryCount = 10000;
		long startTime = System.currentTimeMillis();

		for (int i = 0; i < queryCount; i++) {
			int qx = random.nextInt(system.MAP_WIDTH);
			int qy = random.nextInt(system.MAP_HEIGHT);
			system.findNearbyObjects(qx, qy, 20);
		}

		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		double avgTime = (double) totalTime / queryCount;

		System.out.println("执行 " + queryCount + " 次查询");
		System.out.println("总耗时: " + totalTime + "ms");
		System.out.println("平均每次查询耗时: " + String.format("%.4f", avgTime) + "ms");
		System.out.println("每秒可处理查询: " + String.format("%.0f", 1000.0 / avgTime));

		// 清理
		system.clearAll();
		System.out.println("\n清理后对象数量: " + system.getObjectCount());
	}
}
