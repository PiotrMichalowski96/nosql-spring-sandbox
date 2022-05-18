package com.piotr.redis.reactive.data.config;

import com.piotr.redis.reactive.data.domain.Stock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {

  @Bean
  public ReactiveRedisOperations<String, Stock> reactiveRedisOperations(ReactiveRedisConnectionFactory factory) {
    Jackson2JsonRedisSerializer<Stock> serializer = new Jackson2JsonRedisSerializer<>(Stock.class);
    RedisSerializationContext.RedisSerializationContextBuilder<String, Stock> builder = RedisSerializationContext.newSerializationContext(
        new StringRedisSerializer());

    RedisSerializationContext<String, Stock> context = builder.value(serializer)
        .build();

    return new ReactiveRedisTemplate<>(factory, context);
  }
}
