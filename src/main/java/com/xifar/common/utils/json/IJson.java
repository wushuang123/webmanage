package com.xifar.common.utils.json;

import java.lang.reflect.Type;

public interface IJson {

	public <T> String toJson(T t);

	public <T> T fromJson(String json, Class<T> clazz);

	public <T> T fromJson(String json, Type type);

}
