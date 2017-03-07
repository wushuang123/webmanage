package com.xifar.console.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisHttpSession
public class Config {
	
	@Bean
    public JedisConnectionFactory connectionFactory() {
		JedisConnectionFactory config =new JedisConnectionFactory();
		config.setHostName("192.168.177.132");
		config.setPort(6379);
        config.setTimeout(10000);
        config.setUsePool(true);
        config.setDatabase(0);
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxWaitMillis(20000);
        poolConfig.setMaxTotal(20);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnCreate(true);
        return config;
    }

}
