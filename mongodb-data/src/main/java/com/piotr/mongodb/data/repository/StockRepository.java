package com.piotr.mongodb.data.repository;

import com.piotr.mongodb.data.entity.Stock;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<Stock, Long> {
}
