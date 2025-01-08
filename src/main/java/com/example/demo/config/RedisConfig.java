package com.example.demo.config;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfig {

	@Bean
	public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
		RedisTemplate<String, Object> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);

		// 配置自定义的 ObjectMapper
		ObjectMapper objectMapper = customObjectMapper();
		// 设置序列化器
		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

		// 设置 key 和 value 的序列化方式
		template.setKeySerializer(new StringRedisSerializer());
		template.setValueSerializer(serializer);
		template.setHashKeySerializer(new StringRedisSerializer());
		template.setHashValueSerializer(serializer);

		template.afterPropertiesSet();
		return template;
	}

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		// 配置序列化方式，与 RedisTemplate 保持一致
		// 配置自定义的 ObjectMapper
		// 配置自定义的 ObjectMapper
		ObjectMapper objectMapper = customObjectMapper();
		// 设置序列化器
		GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(objectMapper);

		RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
				.serializeKeysWith(
						RedisSerializationContext.SerializationPair.fromSerializer(new StringRedisSerializer()))
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(serializer))
				.disableCachingNullValues();

		// 构建 RedisCacheManager
		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(config)
				.transactionAware() // 开启事务支持
				.build();
	}

	// 提取公共的 ObjectMapper 配置
	private ObjectMapper customObjectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		// 设置 ObjectMapper 的可见性
		objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
		// 注册支持 Java 8 日期时间类型的模块，解决 LocalDateTime 的序列化问题
		objectMapper.registerModule(new JavaTimeModule());
		// 自动发现其他模块
		objectMapper.findAndRegisterModules();
		// 禁用时间戳
		objectMapper.disable(com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		// activateDefaultTyping 是 Jackson 用于启用默认类型信息的配置方法，它在序列化和反序列化时指定如何处理类型信息
		objectMapper.activateDefaultTyping(
				LaissezFaireSubTypeValidator.instance,
				// 所有非 final 的类（可以被继承的类）都会被插入类型信息
				ObjectMapper.DefaultTyping.NON_FINAL,
				// 将类型信息作为 JSON 的一个字段插入
				JsonTypeInfo.As.PROPERTY);
		return objectMapper;
	}
}
