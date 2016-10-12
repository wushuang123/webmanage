package com.xifar.common.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 节点类
 */
class Node {
	/**
	 * 节点编号
	 */
	public String id;

	/**
	 * 节点内容
	 */
	public String text;

	/**
	 * 父节点编号
	 */
	public String parentId;

	/**
	 * 孩子节点列表
	 */
	private List children = new ArrayList();

	// 添加孩子节点
	public void addChild(Node node) {
		children.add(node);
	}

	// 先序遍历，拼接JSON字符串
	public String toString() {
		String result = "{" + "id : '" + id + "'" + ", text : '" + text + "'";
		if (children.size() != 0) {
			result += ", children : [";
			for (int i = 0; i < children.size(); i++) {
				result += ((Node) children.get(i)).toString() + ",";
			}
			result = result.substring(0, result.length() - 1);
			result += "]";
		} else {
			result += ", leaf : true";
		}
		return result + "}";
	}

	// 兄弟节点横向排序
	public void sortChildren() {
		if (children.size() != 0) {
			// 对本层节点进行排序（可根据不同的排序属性，传入不同的比较器，这里 传入ID比较器）
			Collections.sort(children, new NodeIDComparator());
			// 对每个节点的下一层节点进行排序
			for (int i = 0; i < children.size(); i++) {
				((Node) children.get(i)).sortChildren();
			}
		}
	}

}
