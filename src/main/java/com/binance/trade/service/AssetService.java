package com.binance.trade.service;

import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.model.trade.AccountBalance;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.binance.trade.config.PrivateConfig;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    @Autowired
    FutureBetLogic futureBetLogic;

    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);

    public List<AccountBalance> getMyFutureAssets() {

    }
}
