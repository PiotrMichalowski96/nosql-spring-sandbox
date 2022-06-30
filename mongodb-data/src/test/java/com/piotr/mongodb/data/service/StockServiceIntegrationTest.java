package com.piotr.mongodb.data.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.piotr.mongodb.data.config.MongoTestConfig;
import com.piotr.mongodb.data.entity.Stock;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@DataMongoTest
@ActiveProfiles("TEST")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = MongoTestConfig.class)
class StockServiceIntegrationTest {

  @Autowired
  private MongoTemplate mongoTemplate;

  @Autowired
  private StockService stockService;

  @Test
  void shouldSaveStock() {
    //given
    Long id = 1L;
    Stock stock = Stock.builder()
        .id(id)
        .ticker("EUR/USD")
        .build();

    //when
    stockService.save(stock);
    Stock actualStock = mongoTemplate.findById(id, Stock.class);

    //then
    assertThat(actualStock).isEqualTo(stock);
  }

  @Test
  void shouldGetStockById() {
    //given
    Long id = 2L;
    Stock stock = Stock.builder()
        .id(id)
        .ticker("PLN/CHF")
        .build();

    mongoTemplate.save(stock);

    //when
    Stock actualStock = stockService.getStockById(id);

    //then
    assertThat(actualStock).isEqualTo(stock);
  }

  @Test
  void shouldDeleteStock() {
    //given
    Long id = 3L;
    Stock stock = Stock.builder()
        .id(id)
        .ticker("USD/GBP")
        .build();

    mongoTemplate.save(stock);

    //when
    stockService.delete(id);
    Stock searchedStock = mongoTemplate.findById(id, Stock.class);

    //then
    assertThat(searchedStock).isNull();
  }
}
