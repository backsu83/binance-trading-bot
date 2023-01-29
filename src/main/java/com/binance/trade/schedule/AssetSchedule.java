package com.binance.trade.schedule;

import com.binance.trade.client.model.trade.AccountBalance;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import com.binance.trade.service.AssetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AssetSchedule {

    @Autowired
    AssetService assetService;

    @Scheduled(fixedDelay = 1000)
    public void getMyAssets() {
        System.out.println(assetService.getMyFutureAssets());
    }
}
