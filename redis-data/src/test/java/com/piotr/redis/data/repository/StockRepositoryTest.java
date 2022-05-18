package com.piotr.redis.data.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.piotr.redis.data.config.RedisConfig;
import com.piotr.redis.data.entity.Stock;
import java.util.Optional;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import redis.embedded.RedisServer;

@Import(RedisConfig.class)
@ExtendWith(SpringExtension.class)
@ImportAutoConfiguration(classes = RedisAutoConfiguration.class)
public class StockRepositoryTest {

  @Autowired
  private StockRepository stockRepository;

  @Test
  void shouldSaveStock() {
    //given
    Stock stock = createStockWith(1L, "USD/EUR");

    //when
    stockRepository.save(stock);
    Stock retrievedStock = stockRepository.findById(stock.getId()).get();

    //then
    assertThat(retrievedStock).isEqualTo(stock);
  }

  @Test
  void shouldUpdateStock() {
    //given
    Stock stock = createStockWith(1L, "USD/EUR");

    //when
    stockRepository.save(stock);
    stock.setTicker("USD/PLN");
    stockRepository.save(stock);

    Stock retrievedStock = stockRepository.findById(stock.getId()).get();

    //then
    assertThat(retrievedStock).isEqualTo(stock);
  }

  @Test
  void shouldDeleteStock() {
    //given
    Stock stock = createStockWith(1L, "USD/EUR");

    //when
    stockRepository.save(stock);
    stockRepository.delete(stock);
    Optional<Stock> retrievedStock = stockRepository.findById(stock.getId());

    //then
    assertThat(retrievedStock).isEmpty();
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
