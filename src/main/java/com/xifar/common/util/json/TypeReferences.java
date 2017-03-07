package com.xifar.common.util.json;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;

public class TypeReferences<T> {

	protected final Type type;

	/**
	 * Constructs a new type literal. Derives represented class from type
	 * parameter.
	 *
	 * <p>
	 * Clients create an empty anonymous subclass. Doing so embeds the type
	 * parameter in the anonymous class's type hierarchy so we can reconstitute
	 * it at runtime despite erasure.
	 */
	protected TypeReferences() {
		Type superClass = getClass().getGenericSuperclass();

		type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
	}

	/**
	 * @since 1.2.9
	 * @param actualTypeArguments
	 */
	protected TypeReferences(Type... actualTypeArguments) {
		Type superClass = getClass().getGenericSuperclass();

		ParameterizedType argType = (ParameterizedType) ((ParameterizedType) superClass).getActualTypeArguments()[0];
		Type rawType = argType.getRawType();
		Type[] argTypes = argType.getActualTypeArguments();

		int actualIndex = 0;
		for (int i = 0; i < argTypes.length; ++i) {
			if (argTypes[i] instanceof TypeVariable) {
				argTypes[i] = actualTypeArguments[actualIndex++];
				if (actualIndex >= actualTypeArguments.length) {
					break;
				}
			}
		}
		type = new ParameterizedTypeImplement(argTypes, this.getClass(), rawType);
	}

	/**
	 * Gets underlying {@code Type} instance.
	 */
	public Type getType() {
		return type;
	}
}