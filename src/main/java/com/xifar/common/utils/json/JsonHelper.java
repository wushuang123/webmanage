package com.xifar.common.utils.json;

import java.lang.reflect.Type;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonHelper {

	/********************************************* fastJson ************************************************/

	/** 转为JSON字符串 **/
	public static String serialize(Object obj) {
		JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:SSS";
		String str = JSON.toJSONString(obj, SerializerFeature.WriteDateUseDateFormat);
		return str;
	}

	/** 解析JSON串 **/
	public static <T> T deserialize(String str, Class<T> clazz) {
		return JSON.parseObject(str, clazz);
	}

	/** 解析泛型对象 **/
	public static <T> T deserialize(String json, Type typeOfT) {
		return JSON.parseObject(json, typeOfT);
	}

	/********************************************* Gson ************************************************/
	protected static Gson gson = null;

	static {
		GsonBuilder gb = new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd HH:mm:SSS");
		gson = gb.create();
	}

	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	public static <T> T fromJson(String str, Class<T> cls) {
		return gson.fromJson(str, cls);
	}

	public static <T> T fromJson(String json, Type typeOfT) {
		return gson.fromJson(json, typeOfT);
	}

}
