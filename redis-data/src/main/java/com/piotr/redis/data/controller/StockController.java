package com.piotr.redis.data.controller;

import com.piotr.redis.data.entity.Stock;
import com.piotr.redis.data.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
@RequiredArgsConstructor
public class StockController {

  private final StockService stockService;

  @GetMapping("/{id}")
  public ResponseEntity<Stock> findById(@PathVariable Long id) {
    return stockService.getStockById(id)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @PostMapping
  public ResponseEntity<Stock> saveStock(@RequestBody Stock stock) {
    return ResponseEntity.ok(stockService.save(stock));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteStock(@PathVariable Long id) {
    return stockService.getStockById(id)
        .map(stock -> {
          stockService.delete(stock);
          return ResponseEntity.noContent().build();
        })
        .orElse(ResponseEntity.notFound().build());
  }
}
