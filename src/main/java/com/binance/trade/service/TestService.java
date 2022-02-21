package com.binance.trade.service;


import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.mapper.TestMapper;
import com.binance.trade.model.TradeTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;
    private static final Logger logger = LoggerFactory.getLogger(TestService.class);

    public List<TradeTest> serviceTest() {

        List<TradeTest> tradeTests = testMapper.selectTest();
        System.out.println(tradeTests.get(0).getColumn_1());
        System.out.println(tradeTests.get(0).getColumn_2());
        return tradeTests;
    }

    public List<TradeTest> serviceTest2() {
        List<TradeTest> tradeTests = testMapper.selectTest2();
        System.out.println(tradeTests.get(0).getColumn_1());
        System.out.println(tradeTests.get(0).getColumn_2());
        return tradeTests;
    }
    public String getTradeList(String symbol) {

        if (symbol.isEmpty()) {
            symbol = "ETHUSDT";
        }

        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl();

        parameters.put("symbol", symbol);
        String result = client.createMarket().trades(parameters);

        logger.info(result);
        return result;
    }

    @Scheduled(fixedDelay=1000)
    public void getTradeList() {

        String symbol = "ETHUSDT";

        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl();

        parameters.put("symbol", symbol);
        String result = client.createMarket().trades(parameters);

        logger.info(result);
        System.out.println("스케줄링 테스트");
    }
}

