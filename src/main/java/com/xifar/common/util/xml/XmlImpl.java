package com.xifar.common.util.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XmlImpl implements IXml {

	private static Logger log = LoggerFactory.getLogger(XmlImpl.class);

	@Override
	public <T> String toXML(T t) {
		return null;
	}

	@Override
	public Document parse(String xml) {
		try {
			return DocumentHelper.parseText(xml);
		} catch (DocumentException e) {
			e.printStackTrace();
			log.error("解析XML异常" + e.getMessage());
			return null;
		}
	}

}
