package com.piotr.redis.spring.cache;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RedisSpringCacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(RedisSpringCacheApplication.class, args);
  }
}
