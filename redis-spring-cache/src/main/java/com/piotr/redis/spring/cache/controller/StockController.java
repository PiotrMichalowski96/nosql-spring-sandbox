package com.piotr.redis.spring.cache.controller;

import com.piotr.redis.spring.cache.entity.Stock;
import com.piotr.redis.spring.cache.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class StockController {

  private final StockService stockService;

  @GetMapping("/stock/{id}")
  public Stock getStockById(@PathVariable Long id) {
    return stockService.getStockById(id);
  }
}
