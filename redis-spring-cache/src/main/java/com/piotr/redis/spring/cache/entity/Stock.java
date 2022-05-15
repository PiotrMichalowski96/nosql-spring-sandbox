package com.piotr.redis.spring.cache.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public class Stock implements Serializable {
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
