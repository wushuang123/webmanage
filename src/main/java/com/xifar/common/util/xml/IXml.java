package com.xifar.common.util.xml;

import org.dom4j.Document;


public interface IXml {

	public <T> String toXML(T t);

	public Document parse(String xml);

}
