package org.anyframe.gateway.auth.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class RedisConfiguration {

	@Bean
	public JedisConnectionFactory jedisConnFactory(){
		JedisConnectionFactory jedisConnFactory = new JedisConnectionFactory();
		jedisConnFactory.setUsePool(true);
		return jedisConnFactory;
	}
	
	@Bean
	public RedisTemplate<String, List<String>> redisTemplate(JedisConnectionFactory jedisConnFactory){
		RedisTemplate<String, List<String>> redisTemplate = new RedisTemplate<String, List<String>>();
		redisTemplate.setConnectionFactory(jedisConnFactory);
		
		return redisTemplate;
	}
}
