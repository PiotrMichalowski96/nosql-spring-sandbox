package com.piotr.redis.spring.cache.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.piotr.redis.spring.cache.config.CacheConfig;
import com.piotr.redis.spring.cache.entity.Stock;
import com.piotr.redis.spring.cache.repository.StockRepository;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.embedded.RedisServer;

@Import({CacheConfig.class, StockService.class})
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = {CacheAutoConfiguration.class, RedisAutoConfiguration.class})
@EnableCaching
public class StockServiceCachingIntegrationTest {

  @MockBean
  private StockRepository stockRepository;

  @Autowired
  private StockService stockService;

  @Autowired
  private CacheManager cacheManager;

  @Test
  void shouldReturnStockFromCache() {
    //given
    Long id = 1L;
    String ticker = "EUR/USD";
    Stock stock = createStockWith(id, ticker);

    given(stockRepository.findById(id)).willReturn(Optional.of(stock));

    //when
    stockService.getStockById(id);

    //then
    verify(stockRepository, times(1)).findById(id);

    Object stockFromCache = cacheManager.getCache("stockCache").get(id).get();
    assertThat(stockFromCache)
        .isInstanceOf(Stock.class)
        .isEqualTo(stock);
  }

  @TestConfiguration
  static class EmbeddedRedisConfiguration {

    private final RedisServer redisServer;

    public EmbeddedRedisConfiguration() {
      this.redisServer = new RedisServer();
    }

    @PostConstruct
    public void startRedis() {
      redisServer.start();
    }

    @PreDestroy
    public void stopRedis() {
      this.redisServer.stop();
    }
  }

  private Stock createStockWith(Long id, String ticker) {
    Stock stock = new Stock();
    stock.setId(id);
    stock.setTicker(ticker);
    return stock;
  }
}
