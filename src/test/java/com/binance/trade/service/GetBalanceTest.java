package com.binance.trade.service;

import com.binance.trade.examples.PrivateConfig;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class GetBalanceTest {

    @Test
    void name() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(syncRequestClient.getBalance());
    }
}
