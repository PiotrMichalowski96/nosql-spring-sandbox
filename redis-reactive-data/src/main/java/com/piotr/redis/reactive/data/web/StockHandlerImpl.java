package com.piotr.redis.reactive.data.web;

import com.piotr.redis.reactive.data.domain.Stock;
import com.piotr.redis.reactive.data.repository.StockRepository;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class StockHandlerImpl implements StockHandler {

  private final StockRepository stockRepository;

  @Override
  public Mono<ServerResponse> getAllStocks(ServerRequest request) {
    return stockRepository.findAll()
        .collect(Collectors.toList())
        .flatMap(stockList -> ServerResponse.ok().bodyValue(stockList))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  @Override
  public Mono<ServerResponse> getStockById(ServerRequest request) {
    Long id = Long.valueOf(request.pathVariable("id"));

    return stockRepository.findById(id)
        .flatMap(stock -> ServerResponse.ok().bodyValue(stock))
        .switchIfEmpty(ServerResponse.notFound().build());
  }

  @Override
  public Mono<ServerResponse> saveStock(ServerRequest request) {
    return request.bodyToMono(Stock.class)
        .flatMap(stockRepository::save)
        .flatMap(value -> ServerResponse.ok().build());
  }

  @Override
  public Mono<ServerResponse> deleteStock(ServerRequest request) {
    Long id = Long.valueOf(request.pathVariable("id"));

    return stockRepository.findById(id)
        .flatMap(stockRepository::delete)
        .flatMap(value -> ServerResponse.ok().build());
  }

  @Override
  public Mono<ServerResponse> deleteAllStocks(ServerRequest request) {
    return stockRepository.deleteAll()
        .flatMap(value -> ServerResponse.ok().build());
  }
}
