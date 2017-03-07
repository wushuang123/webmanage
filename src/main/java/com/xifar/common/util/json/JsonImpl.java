package com.xifar.common.util.json;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonImpl implements IJson {

	private static Gson gson = null;

	static {
		GsonBuilder gb = new GsonBuilder();
		gb.setDateFormat("yyyy-MM-dd HH:mm:SSS");
		gson = gb.create();
	}

	@Override
	public <T> String toJson(T t) {
		return gson.toJson(t);
	}

	@Override
	public <T> T fromJson(String json, Class<T> clazz) {
		return gson.fromJson(json, clazz);
	}

	@Override
	public <T> T fromJson(String json, Type type) {
		return gson.fromJson(json, type);
	}

	/********************************************* fastJson ************************************************/

	// /** 转为JSON字符串 **/
	// public String toJson(Object obj) {
	// JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:SSS";
	// String str = JSON.toJSONString(obj,
	// SerializerFeature.WriteDateUseDateFormat);
	// return str;
	// }
	//
	// /** 解析JSON串 **/
	// public <T> T fromJson(String str, Class<T> clazz) {
	// return JSON.parseObject(str, clazz);
	// }
	//
	// /** 解析泛型对象 **/
	// public <T> T fromJson(String json, Type typeOfT) {
	// return JSON.parseObject(json, typeOfT);
	// }

}
