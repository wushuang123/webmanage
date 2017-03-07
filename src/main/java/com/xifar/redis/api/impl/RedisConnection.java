package com.xifar.redis.api.impl;

import com.xifar.redis.api.interfaces.Connection;

public class RedisConnection implements Connection{

	@Override
	public <T> T connect(String host, int port, int timeout, boolean ssl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T close(T t) {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public <T> T connect(String host, int port, int timeout, boolean ssl) {
//		return new Jedis(host,port,timeout,ssl);
//	}
//
//	@Override
//	public <T> T close(T t) {
//		// TODO Auto-generated method stub
//		return null;
//	}

	

}
