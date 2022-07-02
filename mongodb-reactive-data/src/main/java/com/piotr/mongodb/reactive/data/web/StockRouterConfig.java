package com.piotr.mongodb.reactive.data.web;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class StockRouterConfig {

  private static final String STOCK_URL = "/stock";
  private static final String STOCK_URL_ID = "/stock/{id}";

  @Bean
  public RouterFunction<ServerResponse> stockRoutes(StockHandler stockHandler) {
    return RouterFunctions.route()
        .GET(STOCK_URL, accept(APPLICATION_JSON), stockHandler::getAllStocks)
        .GET(STOCK_URL_ID, accept(APPLICATION_JSON), stockHandler::getStockById)
        .POST(STOCK_URL, accept(APPLICATION_JSON), stockHandler::saveStock)
        .DELETE(STOCK_URL, accept(APPLICATION_JSON), stockHandler::deleteAllStocks)
        .DELETE(STOCK_URL_ID, accept(APPLICATION_JSON), stockHandler::deleteStock)
        .build();
  }
}
