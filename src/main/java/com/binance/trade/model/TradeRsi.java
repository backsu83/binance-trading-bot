package com.binance.trade.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TradeRsi {

    private BigDecimal price;
    private String dateYmd;
}
