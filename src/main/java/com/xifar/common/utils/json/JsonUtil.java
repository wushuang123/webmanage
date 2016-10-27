package com.xifar.common.utils.json;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.xifar.common.utils.ProxyHandler;

public class JsonUtil {

	static ProxyHandler proxy = new ProxyHandler();

	public static <T> String toJson(T t) {
		IJson iJson = (IJson) proxy.bind(new JsonImpl());
		return iJson.toJson(t);
	}

	public static <T> T fromJson(String json, Class<T> clazz) {
		IJson iJson = (IJson) proxy.bind(new JsonImpl());
		return iJson.fromJson(json, clazz);
	}

	public <T> T fromJson(String json, Type type) {
		IJson iJson = (IJson) proxy.bind(new JsonImpl());
		return iJson.fromJson(json, type);
	}
}
