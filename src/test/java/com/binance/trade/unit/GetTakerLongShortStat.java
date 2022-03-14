package com.binance.trade.unit;

import com.binance.trade.client.model.enums.PeriodType;
import com.binance.trade.examples.PrivateConfig;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import org.junit.Test;

public class GetTakerLongShortStat {
    @Test
    public void name() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
        System.out.println(syncRequestClient.getOpenInterestStat("BTCUSDT", PeriodType._5m,null,null,10));

    }
}
