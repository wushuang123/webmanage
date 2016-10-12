package com.xifar.common.utils;

import java.util.Arrays;

public class ArrayUtil {

	/**
	 * @author wushuang
	 * 
	 * @note 数组扩容 
	 * 
	 **/
	public static <T> T[] expandCapacity(T[] data, int newLength) {

		newLength = newLength < 0 ? 0 : newLength;
		return Arrays.copyOf(data, newLength);
	}

}
