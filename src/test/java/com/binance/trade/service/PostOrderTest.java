package com.binance.trade.service;

import com.binance.trade.client.model.enums.*;
import com.binance.trade.examples.PrivateConfig;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PostOrderTest {

    @Test
    void name() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
//        System.out.println(syncRequestClient.postOrder("BTCUSDT", OrderSide.SELL, PositionSide.BOTH, OrderType.LIMIT, TimeInForce.GTC,
//                "1", "1", null, null, null, null));

        // place dual position side order.
        // Switch between dual or both position side, call: com.binance.client.examples.trade.ChangePositionSide
        System.out.println(syncRequestClient.postOrder(
                "XRPUSDT",
                OrderSide.BUY,
                PositionSide.BOTH,
                OrderType.LIMIT,
                TimeInForce.GTC,
                "10",
                "0.862",
                null,
                null,
                null,
                null, NewOrderRespType.RESULT));

    }
}

