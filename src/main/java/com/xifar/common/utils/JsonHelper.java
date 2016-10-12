package com.xifar.common.utils;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonHelper {

	/** 转为JSON字符串 **/
	public static String toJson(Object obj) {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:SSS";
		String str = JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
		return str;
	}

	/** 解析JSON串 **/
	public static <T> T fromJSON(String str, Class<T> clazz) {
		return JSON.parseObject(str, clazz);
	}

	/** 解析泛型对象 **/
	public static <T> T fromJson(String json, Type typeOfT) {
		return JSON.parseObject(json, typeOfT);
	}

	public static void main(String[] args) {
	}

}
