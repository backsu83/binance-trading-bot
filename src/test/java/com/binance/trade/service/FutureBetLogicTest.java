package com.binance.trade.service;

import com.binance.trade.client.enums.CoinSymbols;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FutureBetLogicTest {

    @Autowired
    FutureBetLogic futureBetLogic;

    @Test
    void _DB_체결결과_대이터_조회() {
        futureBetLogic.getTadeConclusion(CoinSymbols.ETHUSDT.name() , 3 , 20,50);
    }
}