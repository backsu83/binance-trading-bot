package com.binance.trade.schedule;


import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.model.enums.*;
import com.binance.trade.client.model.trade.AccountInformation;
import com.binance.trade.client.model.trade.Asset;
import com.binance.trade.client.model.trade.Order;
import com.binance.trade.config.PrivateConfig;
import com.binance.trade.model.TradeHistory;
import com.binance.trade.model.TradeVolumeExplosion;
import com.binance.trade.model.TradeVolumeReport;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import com.binance.trade.service.FutureBetLogic;
import com.binance.trade.service.SlackMessage;
import com.binance.trade.service.TradeHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class TradeHistorySchdule {

    private static final Logger logger = LoggerFactory.getLogger(TradeHistorySchdule.class);

    @Autowired
    TradeHistoryService tradeHistoryService;

    @Autowired
    FutureBetLogic futureBetLogic;

    @Autowired
    SlackMessage slackMessage;

    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroySOL() {
        tradeHistoryService.tradehistroy(CoinSymbols.SOLUSDT);
        int rsi = tradeHistoryService.getRsi(CoinSymbols.SOLUSDT);
    }

    @Scheduled(fixedDelay = 5000)
    public void schduleTradeVolumeReport() {
        TradeVolumeReport tradeVolumeReport = new TradeVolumeReport();
        tradeVolumeReport.setPair(CoinSymbols.SOLUSDT.toString());
        tradeVolumeReport.setSymbol(CoinSymbols.SOLUSDT.getTag());
        tradeHistoryService.tradeVolumeReport(tradeVolumeReport);
    }

    @Scheduled(fixedDelay = 1000)
    public void schduleCheckTradeVolumeExplosion() {

        TradeVolumeExplosion result = tradeHistoryService.checkTradeVolumeExplosion(CoinSymbols.SOLUSDT);

        String tradeDirection;

        if (result.getTradeVolumeIncreaseRate() >= 3) {
            if (result.getPriceMovingAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
                tradeDirection = "SHORT";
                logger.warn(CoinSymbols.SOLUSDT + " SHORT");
            } else {
                tradeDirection = "LONG";
                logger.warn(CoinSymbols.SOLUSDT + "LONG");
            }
            schduleFutureLogic(CoinSymbols.SOLUSDT.name(), tradeDirection);
            int rsi = tradeHistoryService.getRsi(CoinSymbols.SOLUSDT);
//            slackMessage.sendRsi(CoinSymbols.SOLUSDT + " explosionRate : " + result.getTradeVolumeIncreaseRate() + ", RSI : " + rsi + ", " + tradeDirection);
        }
    }

    private void schduleFutureLogic(String tradePair, String direction) {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        AccountInformation myInfomation = syncRequestClient.getAccountInformation();
        List<Asset> myAssets = myInfomation.getAssets();
        Asset myUsdtAsset =
                myAssets.stream()
                        .filter((asset -> asset.getAsset().equalsIgnoreCase("USDT")))
                        .findFirst()
                        .orElseThrow(IllegalStateException::new);

        BigDecimal myUsdtAvailableBalance = myUsdtAsset.getMaxWithdrawAmount();

        List<TradeHistory> tradeHistories = tradeHistoryService.getTradeListByBinance("SOLUSDT", 3);

        TradeHistory lastTradeHistory = tradeHistories.get(tradeHistories.size()-1);
        BigDecimal lastTradePrice = lastTradeHistory.getPrice();

        System.out.println("lastPrice : "  + lastTradePrice);
        System.out.println("myUsdtAvailableBalance : " + myUsdtAvailableBalance);

        BigDecimal tradeQuantity = myUsdtAvailableBalance.divide(lastTradePrice, 0, RoundingMode.DOWN);
        System.out.println(tradeQuantity);

        if (tradeQuantity.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            return;
        }

        if (direction.equalsIgnoreCase("LONG")) {
            syncRequestClient.changeInitialLeverage(tradePair, 10);
            Order orderInfo = syncRequestClient.postOrder(
                    tradePair,
                    OrderSide.BUY,
                    PositionSide.BOTH,
                    OrderType.MARKET,
                    null,
                    tradeQuantity.toString(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    NewOrderRespType.RESULT);
            System.out.println(orderInfo);
            tradeHistoryService.setTradeTransaction(orderInfo);

        } else if (direction.equalsIgnoreCase("SHORT")) {
            syncRequestClient.changeInitialLeverage(tradePair, 10);
            Order orderInfo = syncRequestClient.postOrder(
                    tradePair,
                    OrderSide.SELL,
                    PositionSide.BOTH,
                    OrderType.MARKET,
                    null,
                    tradeQuantity.toString(),
                    null,
                    null,
                    null,
                    null,
                    null,
                    NewOrderRespType.RESULT);
            System.out.println(orderInfo);
            slackMessage.sendMsg(orderInfo.toString());
            tradeHistoryService.setTradeTransaction(orderInfo);
        }
    }
}
