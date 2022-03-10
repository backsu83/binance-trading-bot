package com.binance.trade.schedule;


import com.binance.trade.service.TradeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TradeHistorySchdule {

    @Autowired
    TradeHistoryService tradeHistoryService;

    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroy() {
        tradeHistoryService.getTradeList("ETHUSDT");
    }
}
