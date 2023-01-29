package com.binance.trade.schedule;


import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.model.enums.*;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import com.binance.trade.service.FutureBetLogic;
import com.binance.trade.service.SlackMessage;
import com.binance.trade.service.TradeHistoryService;
import com.binance.trade.config.PrivateConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@Component
public class TradeHistorySchdule {

    @Autowired
    TradeHistoryService tradeHistoryService;

    @Autowired
    FutureBetLogic futureBetLogic;

    @Autowired
    SlackMessage slackMessage;

//    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroyETH() {
        tradeHistoryService.tradehistroy(CoinSymbols.ETHUSDT);
//        tradeHistoryService.getRsi(CoinSymbols.SANDUSDT);
    }

    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroyAPT() {
        tradeHistoryService.tradehistroy(CoinSymbols.APTUSDT);
        int rsi = tradeHistoryService.getRsi(CoinSymbols.APTUSDT);
        if (rsi > 90 || rsi < 20) {
            slackMessage.sendRsi(CoinSymbols.APTUSDT + " RSI : " + rsi);
        }
    }

//    @Scheduled(fixedDelay = 3000)
    public void schduleTradeConclusionETHBy3() {
        tradeHistoryService.tradeConclusion(CoinSymbols.ETHUSDT , Calendar.SECOND , -3);
    }

//    @Scheduled(fixedDelay = 5000)
    public void schduleTradeConclusionETHBy5() {
        tradeHistoryService.tradeConclusion(CoinSymbols.ETHUSDT , Calendar.SECOND , -5);
    }

    @Scheduled(fixedDelay = 1000)
    public void schduleFutureLogic() {

        String response = tradeHistoryService.selectTradeConclusionUpDown();
        SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");

        String format_time1 = format1.format (System.currentTimeMillis());
        if (response != null) {

            System.out.println(format_time1 + " : " + response);
        }
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);
/*
        if (response.equals("long")) {

            System.out.println(syncRequestClient.postOrder(
                    "ETHUSDT",
                    OrderSide.BUY,
                    PositionSide.BOTH,
                    OrderType.MARKET,
                    TimeInForce.GTC,
                    "10",
                    null,
                    null,
                    null,
                    null,
                    null, NewOrderRespType.RESULT));
        } else if (response.equals("short")) {

            System.out.println(syncRequestClient.postOrder(
                    "ETHUSDT",
                    OrderSide.SELL,
                    PositionSide.BOTH,
                    OrderType.MARKET,
                    TimeInForce.GTC,
                    "10",
                    null,
                    null,
                    null,
                    null,
                    null, NewOrderRespType.RESULT));
        }
*/
    }
}
