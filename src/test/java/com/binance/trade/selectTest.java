package com.binance.trade;

import com.binance.trade.service.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class selectTest {

    @Autowired
    TestService testService;

    @Test
    void name() {
        testService.serviceTest();
    }

    @Test
    void name2() {
        System.out.println("!================ START ================!");
        testService.serviceTest2();
        System.out.println("!================ END ================!");
    }

    @Test
    void ethusdt() {
        String response = testService.getTradeList("ETHUSDT");
        System.out.println(response);
    }
}
