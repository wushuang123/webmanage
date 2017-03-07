package com.xifar.common.util.json;

import java.lang.reflect.Type;

public interface IJson {

	<T> String toJson(T t);

	<T> T fromJson(String json, Class<T> clazz);

	<T> T fromJson(String json, Type type);

}
