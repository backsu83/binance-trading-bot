package com.binance.trade.client.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  CoinSymbols {
    ETHUSDT("eth"),
    APTUSDT("apt");
//    BTCUSDT("btc"),
//    SANDUSDT("sand");

    private String tag;
}
