package com.piotr.redis.data.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("Stock")
public class Stock implements Serializable {
  private Long id;
  private String ticker;
  private String stockType;
  private String exchange;
  private BigDecimal price;
  private String currency;
  private BigInteger volume;
  private LocalDateTime stockTimestamp;
}
