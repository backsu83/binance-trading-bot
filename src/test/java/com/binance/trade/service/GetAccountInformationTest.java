package com.binance.trade.service;

import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.model.trade.AccountInformation;
import com.binance.trade.client.model.trade.Asset;
import com.binance.trade.client.model.trade.Position;
import com.binance.trade.examples.PrivateConfig;
import com.binance.trade.model.TradeHistory;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@SpringBootTest
public class GetAccountInformationTest {

    @Autowired
    TradeHistoryService tradeHistoryService;

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

    @Test
    void getMyBuyPossibleQuantity() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        AccountInformation myInfomation = syncRequestClient.getAccountInformation();
        List<Asset> myAssets = myInfomation.getAssets();
        System.out.println(myInfomation);

        Asset myUsdtAsset =
                myAssets.stream()
                        .filter((asset -> asset.getAsset().equalsIgnoreCase("USDT")))
                        .findFirst()
                        .orElseThrow(IllegalStateException::new);

        BigDecimal myUsdtAvailableBalance = myUsdtAsset.getMaxWithdrawAmount();

        List<TradeHistory> tradeHistories = tradeHistoryService.getTradeListByBinance("APTUSDT");

        TradeHistory lastTradeHistory = tradeHistories.get(tradeHistories.size()-1);
        BigDecimal lastTradePrice = lastTradeHistory.getPrice();

        System.out.println("lastPrice : "  + lastTradePrice);
        System.out.println("myUsdtAvailableBalance : " + myUsdtAvailableBalance);

        BigDecimal tradeQuantity = myUsdtAvailableBalance.divide(lastTradePrice, 1, RoundingMode.DOWN);
        System.out.println(tradeQuantity);
        System.out.println(tradeQuantity.toString());
    }
}
