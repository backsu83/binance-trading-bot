package com.binance.trade.schedule;

import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.service.AssetService;
import com.binance.trade.service.SlackMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class AssetSchedule {

    @Autowired
    AssetService assetService;

    @Autowired
    SlackMessage slackMessage;

    @Scheduled(fixedDelay = 1000)
    public void getMyAssets() throws JsonProcessingException {
        BigDecimal myProfitPercent = assetService.getMyFutureAssets();
        if (myProfitPercent.compareTo(BigDecimal.valueOf(10)) > 0 || myProfitPercent.compareTo(BigDecimal.valueOf(-10)) < 0) {
            slackMessage.sendRsi(CoinSymbols.APTUSDT + " profitPercent : " + myProfitPercent);
        }
    }
}
