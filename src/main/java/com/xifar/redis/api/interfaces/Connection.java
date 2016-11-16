package com.xifar.redis.api.interfaces;

import java.util.Map;

public interface Connection {
	
	/** 获取连接 **/
	<T> T connect(String host,int port,int timeout,boolean ssl);

	/** 关闭连接 **/
	<T> T close(T t);

}
