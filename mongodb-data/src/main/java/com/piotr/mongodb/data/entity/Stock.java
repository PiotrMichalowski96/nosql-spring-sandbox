package com.piotr.mongodb.data.entity;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Stock {
  @Id
  private Long id;
  private String ticker;
  private String stockType;
  private String exchange;
  private BigDecimal price;
  private String currency;
  private BigInteger volume;
  private LocalDateTime stockTimestamp;
}
