package com.binance.trade.service;

import com.binance.trade.client.model.trade.AccountInformation;
import com.binance.trade.client.model.trade.Position;
import com.binance.trade.config.PrivateConfig;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetService {

    @Autowired
    FutureBetLogic futureBetLogic;

    private static final Logger logger = LoggerFactory.getLogger(AssetService.class);

    public BigDecimal getMyFutureAssets() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);


        AccountInformation myInfomation = syncRequestClient.getAccountInformation();
        List<Position> myPosition = myInfomation.getPositions();
        if (CollectionUtils.isEmpty(myPosition)) {
            return null;
        }
        Position myUnrealizedPosition =
                myPosition.stream()
                        .filter((position) -> position.getSymbol().equals("APTUSDT"))
                        .findFirst()
                        .orElseThrow(IllegalStateException::new);

        if (ObjectUtils.isEmpty(myUnrealizedPosition) || myUnrealizedPosition.getPositionInitialMargin().equals(BigDecimal.valueOf(0))) {
            return null;
        }

        BigDecimal profit = myUnrealizedPosition.getUnrealizedProfit();
        BigDecimal initAmount = myUnrealizedPosition.getPositionInitialMargin();
        BigDecimal scoreRate = profit.divide(initAmount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        logger.info("scoreRate : {}", scoreRate);

        return scoreRate;

    }

    public BigDecimal getMyFutureTradingQuantity() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);


        AccountInformation myInfomation = syncRequestClient.getAccountInformation();
        List<Position> myPosition = myInfomation.getPositions();
        if (CollectionUtils.isEmpty(myPosition)) {
            return null;
        }
        Position myUnrealizedPosition =
                myPosition.stream()
                        .filter((position) -> position.getSymbol().equals("APTUSDT"))
                        .findFirst()
                        .orElseThrow(IllegalStateException::new);

        BigDecimal profit = myUnrealizedPosition.getUnrealizedProfit();
        BigDecimal initAmount = myUnrealizedPosition.getPositionInitialMargin();
        BigDecimal scoreRate = profit.divide(initAmount, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));

        logger.info("scoreRate : {}", scoreRate);

        return scoreRate;

    }
}
