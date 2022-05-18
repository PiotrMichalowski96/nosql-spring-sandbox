package com.piotr.redis.reactive.data.repository;

import com.piotr.redis.reactive.data.domain.Stock;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
@RequiredArgsConstructor
public class StockRepository {

  private static final String STOCK_KEY = "stocks";

  private final ReactiveRedisOperations<String, Stock> reactiveRedisOperations;

  public Flux<Stock> findAll(){
    return reactiveRedisOperations.opsForList()
        .range(STOCK_KEY, 0, -1);
  }

  public Mono<Stock> findById(Long id) {
    return findAll().filter(stock -> Objects.equals(stock.getId(), id))
        .last();
  }

  public Mono<Long> save(Stock stock) {
    return reactiveRedisOperations.opsForList()
        .rightPush(STOCK_KEY, stock);
  }

  public Mono<Long> delete(Stock stock) {
    return reactiveRedisOperations.opsForList()
        .remove(STOCK_KEY, 1, stock);
  }

  public Mono<Boolean> deleteAll() {
    return reactiveRedisOperations.opsForList()
        .delete(STOCK_KEY);
  }
}
