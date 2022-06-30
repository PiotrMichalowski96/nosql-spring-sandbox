package com.piotr.mongodb.data.service;

import com.piotr.mongodb.data.entity.Stock;
import com.piotr.mongodb.data.repository.StockRepository;
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
