package com.binance.trade.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TradeConclusion {
    private BigInteger id;
    private BigDecimal min;
    private BigDecimal max;
    private BigDecimal avg;
    private String symbol;
    private String signal;
    private Integer count;
    private Integer second;
    private LocalDateTime trade_at;
}
