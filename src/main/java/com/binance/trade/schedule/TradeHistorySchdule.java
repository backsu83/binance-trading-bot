package com.binance.trade.schedule;


import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.model.enums.*;
import com.binance.trade.client.model.trade.AccountInformation;
import com.binance.trade.client.model.trade.Asset;
import com.binance.trade.client.model.trade.Order;
import com.binance.trade.config.PrivateConfig;
import com.binance.trade.model.TradeHistory;
import com.binance.trade.model.TradeVolumeExplosion;
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
    public void schduleTradeHistroyBTC() {
        tradeHistoryService.tradehistroy(CoinSymbols.BTCUSDT);
        int rsi = tradeHistoryService.getRsi(CoinSymbols.BTCUSDT);
        if (rsi >= 90 || rsi <= 10) {
            slackMessage.sendRsi(CoinSymbols.BTCUSDT + " RSI : " + rsi);
        }
    }

    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroyAPT() {
        tradeHistoryService.tradehistroy(CoinSymbols.APTUSDT);
        int rsi = tradeHistoryService.getRsi(CoinSymbols.APTUSDT);
//        logger.warn(CoinSymbols.APTUSDT + " RSI : {}", rsi);
        if (rsi >= 90 || rsi <= 10) {
//            slackMessage.sendRsi(CoinSymbols.APTUSDT + " RSI : " + rsi);
        }
    }

    @Scheduled(fixedDelay = 5000)
    public void schduleTradeVolumeReport() {
        tradeHistoryService.tradeVolumeReport(CoinSymbols.APTUSDT);
    }

    @Scheduled(fixedDelay = 1000)
    public void schduleCheckTradeVolumeExplosion() {

        TradeVolumeExplosion result = tradeHistoryService.checkTradeVolumeExplosion(CoinSymbols.APTUSDT);

        String tradeDirection;

        if (result.getTradeVolumeIncreaseRate() >= 3) {
            if (result.getPriceMovingAmount().compareTo(BigDecimal.valueOf(0)) > 0) {
                tradeDirection = "SHORT";
                logger.warn(CoinSymbols.APTUSDT + " SHORT");
            } else {
                tradeDirection = "LONG";
                logger.warn(CoinSymbols.APTUSDT + "LONG");
            }
            schduleFutureLogic(CoinSymbols.APTUSDT.name(), tradeDirection);
            int rsi = tradeHistoryService.getRsi(CoinSymbols.APTUSDT);
//            System.out.println(CoinSymbols.APTUSDT + " explosionRate : " + result.getTradeVolumeIncreaseRate() + ", RSI : " + rsi);
            slackMessage.sendRsi(CoinSymbols.APTUSDT + " explosionRate : " + result.getTradeVolumeIncreaseRate() + ", RSI : " + rsi + ", " + tradeDirection);
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

        List<TradeHistory> tradeHistories = tradeHistoryService.getTradeListByBinance("APTUSDT");

        TradeHistory lastTradeHistory = tradeHistories.get(tradeHistories.size()-1);
        BigDecimal lastTradePrice = lastTradeHistory.getPrice();

        System.out.println("lastPrice : "  + lastTradePrice);
        System.out.println("myUsdtAvailableBalance : " + myUsdtAvailableBalance);

        BigDecimal tradeQuantity = myUsdtAvailableBalance.divide(lastTradePrice, 1, RoundingMode.DOWN);
        System.out.println(tradeQuantity);

        if (tradeQuantity.compareTo(BigDecimal.valueOf(0.5)) < 0) {
            return;
        }

        if (direction.equalsIgnoreCase("LONG")) {
            syncRequestClient.changeInitialLeverage(tradePair, 20);
            Order orderInfo = syncRequestClient.postOrder(
                    tradePair,
                    OrderSide.BUY,
                    PositionSide.BOTH,
                    OrderType.LIMIT,
                    TimeInForce.GTC,
                    tradeQuantity.toString(),
                    lastTradePrice.toString(),
                    null,
                    null,
                    null,
                    null,
                    NewOrderRespType.RESULT);
            System.out.println(orderInfo);
//            setOrderInfo(orderInfo);

        } else if (direction.equalsIgnoreCase("SHORT")) {
            syncRequestClient.changeInitialLeverage(tradePair, 20);
            Order orderInfo = syncRequestClient.postOrder(
                    tradePair,
                    OrderSide.SELL,
                    PositionSide.BOTH,
                    OrderType.LIMIT,
                    TimeInForce.GTC,
                    tradeQuantity.toString(),
                    lastTradePrice.toString(),
                    null,
                    null,
                    null,
                    null,
                    NewOrderRespType.RESULT);
            System.out.println(orderInfo);
//            setOrderInfo(orderInfo);
        }
    }

//    public int setOrderInfo(Order orderInfo) {
//        return tradeHistoryService.setOrderInfo(orderInfo);
//    }
}
