package com.xifar.common.util.json;

import java.lang.reflect.Type;

import com.xifar.common.utils.ProxyHandler;

public class JsonUtil {

	private static JsonImpl jsonImpl = new JsonImpl();

	static ProxyHandler proxy = new ProxyHandler();

	public static <T> String toJson(T t) {
		IJson iJson = (IJson) proxy.bind(jsonImpl);
		return iJson.toJson(t);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		IJson iJson = (IJson) proxy.bind(jsonImpl);
		return iJson.fromJson(json, clazz);
	}

	public <T> T fromJson(String json, Type type) {
		IJson iJson = (IJson) proxy.bind(jsonImpl);
		return iJson.fromJson(json, type);
	}
	
/***************************************** 使用cglibProxy ******************************************/
//	static CglibProxy proxy = new CglibProxy();
//
//	public static <T> String toJson(T t) {
//		IJson iJson = (IJson) proxy.getProxy(JsonImpl.class);
//		return iJson.toJson(t);
//	}
//
//	public static <T> T fromJson(String json, Class<T> clazz) {
//		IJson iJson = (IJson) proxy.getProxy(JsonImpl.class);
//		return iJson.fromJson(json, clazz);
//	}
//
//	public <T> T fromJson(String json, Type type) {
//		IJson iJson = (IJson) proxy.getProxy(JsonImpl.class);
//		return iJson.fromJson(json, type);
//	}
	
}
