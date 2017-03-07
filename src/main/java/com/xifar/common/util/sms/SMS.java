package com.xifar.common.util.sms;

import java.util.List;
import java.util.Map;

public interface SMS {

	/** 发送 **/
	Object send(String url, String templateID, String content, String phoneNumber, Map<String, Object> map);

	/** 批量发送 **/
	Object sendBath(String url, String templateID, String content, List<String> numberList, Map<String, Object> map);

	/** 获取请求地址 **/
	String getURL();

}
