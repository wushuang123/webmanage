package com.xifar.common.utils;

import java.util.List;
import java.util.RandomAccess;

public class ListHelper {

	// 只是一个例子，实际用时照这个例子写就写
	public static <T> String foreachList(List<T> list) {
		if (list instanceof RandomAccess) {
			for (int i = 0, size = list.size(); i < size; i++) {
				String s = (String) list.get(i);
				return null ;
			}
		}else{
			for (T t : list) {
				String s = (String) t;
				return s;
			}
		}
		return null;
	}

}
