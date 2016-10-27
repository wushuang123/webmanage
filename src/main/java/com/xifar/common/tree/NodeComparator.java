package com.xifar.common.tree;

import java.util.Comparator;

/** 比较方法(按顺序比较) **/
class NodeComparator implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		long j1 = ((Node<?>) o1).getOrder();
		long j2 = ((Node<?>) o2).getOrder();
		return (j1 < j2 ? -1 : (j1 == j2 ? 0 : 1));
	}
}
