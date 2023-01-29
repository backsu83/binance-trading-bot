package com.binance.trade.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class TradeVolumeExplosion {

    private int tradeVolumeIncreaseRate;
    private BigDecimal priceMovingAmount;
}
