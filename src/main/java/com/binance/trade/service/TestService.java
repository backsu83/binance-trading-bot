package com.binance.trade.service;


import com.binance.trade.mapper.TestMapper;
import com.binance.trade.model.TradeTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TestService {

    @Autowired
    private TestMapper testMapper;

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
}

