package com.xifar.common.utils;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 不带虚拟节点的一致性Hash算法
 */
public class ConsistentHashingWithoutVirtualNode {

	// 待添加入Hash环的服务器列表
	private static String[] servers = { "192.168.0.0:111", "192.168.0.1:111", "192.168.0.2:111", "192.168.0.3:111",
			"192.168.0.4:111" };

	// key表示服务器的hash值，value表示服务器
	private static SortedMap<Long, String> sortedMap = new TreeMap<Long, String>();

	// 程序初始化，将所有的服务器放入sortedMap中
	static {
		for (int i = 0; i < servers.length; i++) {
			long hash = HashAlgorithm.FNV1_32_HASH.hash(servers[i]);
			System.out.println("[" + servers[i] + "]加入集合中, 其Hash值为" + hash);
			sortedMap.put(hash, servers[i]);
		}
		System.out.println();
	}

	// 得到应当路由到的结点
	private static String getServer(String key) {
		// 得到该key的hash值
		long hash = HashAlgorithm.FNV1_32_HASH.hash(key);
		// 得到大于该Hash值的所有Map
		SortedMap<Long, String> subMap = sortedMap.tailMap(hash);
		if (subMap.isEmpty()) {
			// 如果没有比该key的hash值大的，则从第一个node开始
			Long i = sortedMap.firstKey();
			// 返回对应的服务器
			return sortedMap.get(i);
		} else {
			// 第一个Key就是顺时针过去离node最近的那个结点
			Long i = subMap.firstKey();
			// 返回对应的服务器
			return subMap.get(i);
		}
	}

	public static void main(String[] args) {
		String[] keys = { "太阳", "月亮", "星星" };
		for (int i = 0; i < keys.length; i++)
			System.out.println("[" + keys[i] + "]的hash值为" + HashAlgorithm.FNV1_32_HASH.hash(keys[i]) + ", 被路由到结点["
					+ getServer(keys[i]) + "]");
	}
}