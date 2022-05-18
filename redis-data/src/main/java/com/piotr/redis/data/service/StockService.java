package com.piotr.redis.data.service;

import com.piotr.redis.data.entity.Stock;
import com.piotr.redis.data.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

  private final StockRepository stockRepository;

  public Stock getStockById(Long id) {
    return stockRepository.findById(id)
        .orElseThrow(RuntimeException::new);
  }

  public Stock save(Stock stock) {
    return stockRepository.save(stock);
  }

  public void delete(Long stockId) {
    stockRepository.findById(stockId)
        .ifPresent(stockRepository::delete);
  }
}
