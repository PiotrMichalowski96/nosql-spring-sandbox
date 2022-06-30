package com.piotr.mongodb.data.controller;

import com.piotr.mongodb.data.entity.Stock;
import com.piotr.mongodb.data.service.StockService;
import lombok.RequiredArgsConstructor;
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
  public Stock findById(@PathVariable Long id) {
    return stockService.getStockById(id);
  }

  @PostMapping
  public Stock saveStock(@RequestBody Stock stock) {
    return stockService.save(stock);
  }

  @DeleteMapping("/{id}")
  public void deleteStock(@PathVariable Long id) {
    stockService.delete(id);
  }
}
