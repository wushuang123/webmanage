package com.xifar.common.utils.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ParameterizedTypeImplement implements ParameterizedType {

	private final Type[] actualTypeArguments;
	private final Type ownerType;
	private final Type rawType;

	public ParameterizedTypeImplement(Type[] actualTypeArguments, Type ownerType, Type rawType) {
		this.actualTypeArguments = actualTypeArguments;
		this.ownerType = ownerType;
		this.rawType = rawType;
	}

	public Type[] getActualTypeArguments() {
		return actualTypeArguments;
	}

	public Type getOwnerType() {
		return ownerType;
	}

	public Type getRawType() {
		return rawType;
	}

}