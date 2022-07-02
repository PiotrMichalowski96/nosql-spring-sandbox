package com.piotr.mongodb.reactive.data.repository;

import com.piotr.mongodb.reactive.data.domain.Stock;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface StockRepository extends ReactiveMongoRepository<Stock, Long> {
}
