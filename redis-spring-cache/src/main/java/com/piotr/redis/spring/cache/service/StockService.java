package com.piotr.redis.spring.cache.service;

import com.piotr.redis.spring.cache.entity.Stock;
import com.piotr.redis.spring.cache.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

  private final StockRepository stockRepository;

  @Cacheable(value = "stockCache")
  public Stock getStockById(Long id) {
    return stockRepository.findById(id)
        .orElseThrow(RuntimeException::new);
  }
}
