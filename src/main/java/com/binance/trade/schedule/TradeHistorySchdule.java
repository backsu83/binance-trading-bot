package com.binance.trade.schedule;


import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.service.TradeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Calendar;

@Component
public class TradeHistorySchdule {

    @Autowired
    TradeHistoryService tradeHistoryService;

    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroyETH() {
        tradeHistoryService.tradehistroy(CoinSymbols.ETHUSDT.name());
    }

    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroyBTC() {
        tradeHistoryService.tradehistroy(CoinSymbols.BTCUSDT.name());
    }


    @Scheduled(fixedDelay = 3000)
    public void schduleTradeConclusionETHBy3() {
        tradeHistoryService.tradeConclusion(CoinSymbols.ETHUSDT.name() , Calendar.SECOND , -3);
    }

    @Scheduled(fixedDelay = 10000)
    public void schduleTradeConclusionETHBy10() {
        tradeHistoryService.tradeConclusion(CoinSymbols.ETHUSDT.name() , Calendar.SECOND , -10);
    }

    @Scheduled(fixedDelay = 10000)
    public void schduleTradeConclusionBTCBy10() {
        tradeHistoryService.tradeConclusion(CoinSymbols.BTCUSDT.name() , Calendar.SECOND , -10);
    }

}
