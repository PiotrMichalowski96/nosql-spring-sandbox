package com.piotr.mongodb.data.service;

import com.piotr.mongodb.data.entity.Stock;
import com.piotr.mongodb.data.repository.StockRepository;
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
