package com.xifar.redis.api.interfaces;

import java.util.List;

public interface IRedis {
	
	int set(String key,String value);
	
	int set(String key,String value,long time);
	
	String get(String key);
	
	String get(String key, long end);
	
	String get(String key,long start, long end);
	
	String getSet(String key, String value);
	
	List<String> mget(List<String> list);
	
}
