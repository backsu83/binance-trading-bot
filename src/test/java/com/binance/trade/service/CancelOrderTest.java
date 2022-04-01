package com.binance.trade.service;

import com.alibaba.fastjson.JSONArray;
import com.binance.trade.client.model.trade.Order;
import com.binance.trade.examples.PrivateConfig;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class CancelOrderTest {

    @Test
    void name() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        SyncRequestClient allorders = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        Order xrpusdt = allorders.getOrder("XRPUSDT",20550482199L,null);
        System.out.println(xrpusdt.toString());


//        System.out.println(syncRequestClient.cancelAllOpenOrder("XRPUSDT"));

        System.out.println(syncRequestClient.cancelOrder("XRPUSDT",
                20550482199L, null));

//        JSONArray orderIds = new JSONArray();
//        orderIds.add(20550482199L);
//        orderIds.add(20549647439L);
//        System.out.println(syncRequestClient.batchCancelOrders("XRPUSDT", orderIds.toJSONString(), null));

    }
}
