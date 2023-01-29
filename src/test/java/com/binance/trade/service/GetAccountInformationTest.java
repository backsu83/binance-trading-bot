package com.binance.trade.service;

import com.binance.trade.client.model.trade.AccountInformation;
import com.binance.trade.client.model.trade.Position;
import com.binance.trade.examples.PrivateConfig;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@SpringBootTest
public class GetAccountInformationTest {

    @Test
    void send() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        AccountInformation myInfomation = syncRequestClient.getAccountInformation();
        List<Position> myPosition = myInfomation.getPositions();
        if (CollectionUtils.isEmpty(myPosition)) {
            return;
        }
        Position myUnrealizedPosition =
                myPosition.stream()
                        .filter((position) -> position.getSymbol().equals("APTUSDT"))
                        .findFirst()
                        .orElseThrow(IllegalStateException::new);

        BigDecimal profit = myUnrealizedPosition.getUnrealizedProfit();
        BigDecimal initAmount = myUnrealizedPosition.getPositionInitialMargin();
        BigDecimal scoreRate = profit.divide(initAmount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
        System.out.println("scoreRate : " + scoreRate);

    }
}
