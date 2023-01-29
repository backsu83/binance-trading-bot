package com.binance.trade.service;

import com.binance.trade.client.model.trade.PositionRisk;
import com.binance.trade.examples.PrivateConfig;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class GetAccountInformationTest {

    private final SyncRequestClient syncRequestClient;

    public GetAccountInformationTest(SyncRequestClient syncRequestClient) {
        this.syncRequestClient = syncRequestClient;
    }

    @Test
    void send() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
//        System.out.println(syncRequestClient.getAccountInformation());

//        System.out.println(syncRequestClient.getBalance());

//        System.out.println(syncRequestClient.getAllOrders(
//                "ETHUSDT"
//                , null
//                ,1649438985L
//                ,1654031187L
//                , 10));
//        System.out.println(syncRequestClient.getRecentTrades("ETHUSDT",10));
        System.out.println(syncRequestClient.getPositionRisk("APTUSDT"));
        List<PositionRisk> myPosition = syncRequestClient.getPositionRisk("APTUSDT");
        System.out.println(myPosition.get(0).getUnrealizedProfit());
        System.out.println(myPosition.get(0).getPositionAmt().toString());

//        syncRequestClient.simplePostOrder(
//                "APTUSDT",
//                "short",
//                "BOTH",
//                "MARKET",
//                myPosition.get(0).getPositionAmt().toString(),
//                null,
//                null
//        );
    }
}
