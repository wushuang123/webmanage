package com.xifar.common.tree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** 节点类 **/
class Node<T> {
	/** 节点编号 **/
	private String id;
	/** 节点对象 **/
	private Object entity;
	/** 节点顺序 **/
	private long order;
	/** 父节点编号 **/
	private String pid;
	/** 孩子节点列表 **/
	private List<T> childrens = new ArrayList<T>();
	/** 排序方法(可动态传入) **/
	private Comparator<T> comparator;

	/** 添加孩子节点 **/
	@SuppressWarnings("unchecked")
	public void addChild(Node<T> node) {
		childrens.add((T) node);
	}

//	/** 先序遍历，拼接JSON字符串 **/
//	public String toString() {
//		String result = "{" + "id : '" + id + "'" + ", text : '" + text + "'";
//		if (children.size() != 0) {
//			result += ", children : [";
//			for (int i = 0; i < children.size(); i++) {
//				result += ((Node) children.get(i)).toString() + ",";
//			}
//			result = result.substring(0, result.length() - 1);
//			result += "]";
//		} else {
//			result += ", leaf : true";
//		}
//		return result + "}";
//	}

	/** 兄弟节点横向排序 (递归) **/
	public void sortChildren() {
		if (childrens.size() != 0) {
			// 对本层节点进行排序:可根据不同的排序属性，传入不同的比较器
			Collections.sort(childrens, comparator);
			// 对每个节点的下一层节点进行排序
			for (int i = 0; i < childrens.size(); i++) {
				((Node<?>) childrens.get(i)).sortChildren();
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public long getOrder() {
		return order;
	}

	public void setOrder(long order) {
		this.order = order;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public List<T> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<T> childrens) {
		this.childrens = childrens;
	}

	public Comparator<T> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<T> comparator) {
		this.comparator = comparator;
	}

}
