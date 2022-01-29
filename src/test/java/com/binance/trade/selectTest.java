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
        testService.serviceTest2();
    }
}
