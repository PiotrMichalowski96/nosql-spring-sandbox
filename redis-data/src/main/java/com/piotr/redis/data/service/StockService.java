package com.piotr.redis.data.service;

import com.piotr.redis.data.entity.Stock;
import com.piotr.redis.data.repository.StockRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StockService {

  private final StockRepository stockRepository;

  public Optional<Stock> getStockById(Long id) {
    return stockRepository.findById(id);
  }

  public Stock save(Stock stock) {
    return stockRepository.save(stock);
  }

  public void delete(Stock stock) {
    stockRepository.delete(stock);
  }
}
