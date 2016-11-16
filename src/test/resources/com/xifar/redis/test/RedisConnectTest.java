package com.xifar.redis.test;

import redis.clients.jedis.Jedis;

public class RedisConnectTest {

	public static void main(String[] args) {
		// 连接redis服务
		Jedis jedis = new Jedis("192.168.177.132");
		System.out.println("Connection to server sucessfully");
		// 查看服务是否运行
		System.out.println("Server is running:" + jedis.ping());
		System.out.println(jedis.set("roboot", "mexcir"));
//		jedis.mget
	}

}
