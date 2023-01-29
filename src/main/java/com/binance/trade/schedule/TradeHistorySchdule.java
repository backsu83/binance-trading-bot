package com.binance.trade.schedule;


import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.model.trade.PositionRisk;
import com.binance.trade.order.RequestOptions;
import com.binance.trade.order.SyncRequestClient;
import com.binance.trade.service.FutureBetLogic;
import com.binance.trade.service.TradeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.binance.trade.PrivateConfig;
import java.util.Calendar;
import java.util.List;

@Component
public class TradeHistorySchdule {

    @Autowired
    TradeHistoryService tradeHistoryService;

    @Autowired
    FutureBetLogic futureBetLogic;

//    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroyETH() {
        tradeHistoryService.tradehistroy(CoinSymbols.SANDUSDT);
        tradeHistoryService.getRsi(CoinSymbols.SANDUSDT);
    }

    @Scheduled(fixedDelay = 1000)
    public void schduleTradeHistroyAPT() {
        tradeHistoryService.tradehistroy(CoinSymbols.APTUSDT);
        int rsi = tradeHistoryService.getRsi(CoinSymbols.APTUSDT);
        if (rsi > 95) {
            // short
        } else if (rsi < 10) {
            // long
        }
    }

    @Scheduled(fixedDelay = 10000)
    public void closeAllPositions() {
        RequestOptions options = new RequestOptions();
        SyncRequestClient syncRequestClient = SyncRequestClient.create(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY,
                options);

        List<PositionRisk> myPosition = syncRequestClient.getPositionRisk("APTUSDT");
        System.out.println(myPosition.get(0).getUnrealizedProfit());
    }

//    @Scheduled(fixedDelay = 3000)
    public void schduleTradeConclusionETHBy3() {
        tradeHistoryService.tradeConclusion(CoinSymbols.ETHUSDT , Calendar.SECOND , -3);
    }

//    @Scheduled(fixedDelay = 10000)
    public void schduleTradeConclusionETHBy10() {
        tradeHistoryService.tradeConclusion(CoinSymbols.SANDUSDT , Calendar.SECOND , -10);
    }

//    @Scheduled(fixedDelay = 60000)
    public void schduleFutureLogic() {
        futureBetLogic.getTadeConclusion(CoinSymbols.ETHUSDT.name(),10,10 , 0);

    }
}
