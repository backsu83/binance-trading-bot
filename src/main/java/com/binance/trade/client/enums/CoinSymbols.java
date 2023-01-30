package com.binance.trade.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  CoinSymbols {
    BTCUSDT("btc"),
    ETHUSDT("eth"),
    SOLUSDT("sol"),
    APTUSDT("apt");

    private String tag;
}
