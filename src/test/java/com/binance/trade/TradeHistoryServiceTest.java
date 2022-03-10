package com.binance.trade;

import com.binance.trade.service.TradeHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;


@SpringBootTest
public class TradeHistoryServiceTest {

    @Autowired
    TradeHistoryService testService;

    @Test
    void ethusdt() {
        testService.getTradeList("ETHUSDT");
    }
}