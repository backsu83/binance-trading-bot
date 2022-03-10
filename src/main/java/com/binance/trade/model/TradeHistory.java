package com.binance.trade.model;


import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class TradeHistory {

    private BigInteger id;
    private BigInteger tranId;
    private BigDecimal price;
    private String symbol;
    private BigDecimal qty;
    private BigDecimal quoteQty;
    private Boolean isBuyerMaker;
    private Boolean isBestMatch;
    private BigInteger time;
}
