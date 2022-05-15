package com.piotr.redis.spring.cache.repository;

import com.piotr.redis.spring.cache.entity.Stock;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends CrudRepository<Stock, Long> {
}
