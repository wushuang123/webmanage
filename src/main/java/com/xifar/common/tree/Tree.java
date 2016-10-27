package com.xifar.common.tree;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Tree {
	
	public 
	
	
	
	
	
	public static void main(String[] args) {
		// 读取层次数据结果集列表
		List dataList = VirtualDataGenerator.getVirtualResult();
		// 节点列表（映射表，用于临时存储节点对象）
		HashMap nodeList = new HashMap();
		// 根节点
		Node root = null;
		// 将结果集存入映射表（后面将借助映射表构造多叉树）
		for (Iterator it = dataList.iterator(); it.hasNext();) {
			Map dataRecord = (Map) it.next();
			Node node = new Node();
			node.id = (String) dataRecord.get("id");
			node.text = (String) dataRecord.get("text");
			node.parentId = (String) dataRecord.get("parentId");
			nodeList.put(node.id, node);
		}
		// 构造无序的多叉树
		Set entrySet = nodeList.entrySet();
		for (Iterator it = entrySet.iterator(); it.hasNext();) {
			Node node = (Node) ((Map.Entry) it.next()).getValue();
			if (node.parentId == null || node.parentId.equals("")) {
				root = node;
			} else {
				((Node) nodeList.get(node.parentId)).addChild(node);
			}
		}
		// 输出无序的树形结构的JSON字符串
		System.out.println(root);
		// 对多叉树进行横向排序
		root.sortChildren();
		// 输出有序的树形结构的JSON字符串
		System.out.println(root);
	}
}