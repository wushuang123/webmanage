package com.xifar.common.utils.xml;

import java.lang.reflect.Type;

public interface IXml {

	public <T> T toXML(T t);

	public <T> T fromXML(String xml, Class<T> clazz);

	public <T> T fromXML(String xml, Type type);

}
