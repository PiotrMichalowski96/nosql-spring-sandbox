package com.piotr.redis.data.config;

import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.GenericToStringSerializer;

@Configuration
@ConfigurationProperties(prefix = "database.redis")
@EnableRedisRepositories(basePackages = "com.piotr.redis.data.repository")
public class RedisConfig {

  private String hostname;
  private int port;
  private String password;

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfig = new RedisStandaloneConfiguration();
    redisStandaloneConfig.setHostName(hostname);
    redisStandaloneConfig.setPort(port);
    redisStandaloneConfig.setPassword(RedisPassword.of(password));

    JedisClientConfiguration jedisClientConfig = JedisClientConfiguration.builder()
        .connectTimeout(Duration.ofSeconds(60))
        .build();

    return new JedisConnectionFactory(redisStandaloneConfig, jedisClientConfig);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    template.setValueSerializer(new GenericToStringSerializer<>(Object.class));
    return template;
  }
}
