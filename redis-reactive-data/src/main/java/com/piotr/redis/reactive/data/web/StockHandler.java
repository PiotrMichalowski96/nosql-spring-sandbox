package com.piotr.redis.reactive.data.web;

import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public interface StockHandler {
  Mono<ServerResponse> getAllStocks(ServerRequest request);
  Mono<ServerResponse> getStockById(ServerRequest request);
  Mono<ServerResponse> saveStock(ServerRequest request);
  Mono<ServerResponse> deleteStock(ServerRequest request);
  Mono<ServerResponse> deleteAllStocks(ServerRequest request);
}
