package com.binance.trade.service;

import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.enums.TimeUtils;
import com.binance.trade.mapper.TradeConclusionMapper;
import com.binance.trade.model.TradeConclusion;
import com.binance.trade.model.TradeHistory;
import com.binance.trade.service.TradeHistoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
public class TradeHistoryServiceTest {

    private BigDecimal minPrice = null;
    private BigDecimal maxPrice = null;
    private BigDecimal avgPrice = null;

    @Autowired
    TradeHistoryService testService;

    @Autowired
    TradeConclusionMapper tradeConclusionMapper;

    @Test
    void _바이낸스_체결데이터_조회() {
        final List<TradeHistory> ethusdt = testService.getTradeListByBinance(CoinSymbols.SANDUSDT.name());
        System.out.println(ethusdt.toString());
    }

    @Test
    void _바이낸스_체결데이터_저장() {
        testService.tradehistroy(CoinSymbols.ETHUSDT);
    }

    @Test
    void _바이낸스_체결데이터_계산로직() {
        List<TradeHistory> ethusdt = testService.getTradeListByBinance(CoinSymbols.ETHUSDT.name());

        //최소값,최대값,평균 구하기
        List<TradeHistory> collects = ethusdt.stream()
                .sorted(Comparator.comparing(TradeHistory::getPrice))
                .collect(Collectors.toList());

        int count = 0;
        BigDecimal sum = collects
                .stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        for (TradeHistory collect : collects) {
            if(minPrice == null) {
                minPrice = collect.getPrice();
            }
            maxPrice = collect.getPrice();
            count++;
        }

        System.out.println(minPrice);
        System.out.println(maxPrice);
        System.out.println(maxPrice.subtract(minPrice));
        System.out.println(minPrice.add(maxPrice).divide(new BigDecimal(2)));
        avgPrice = sum.divide(new BigDecimal(count));
        System.out.println(sum);
        System.out.println(avgPrice);
    }

    @Test
    void _DB_체결데이터_조회() {

        List<TradeHistory> tradeListByMinutes
                = testService.getTradeListRange(CoinSymbols.ETHUSDT.getTag() , Calendar.SECOND , -10);
        System.out.println("tradeListByMinutes size : " + tradeListByMinutes.size());
        for (TradeHistory tradeHistory : tradeListByMinutes) {
            System.out.println(TimeUtils.getTimeToLocalDatetime(tradeHistory.getTime().longValue()));
        }
    }

    @Test
    void _DB_체결데이터_결과저장() {
        testService.tradeConclusion(CoinSymbols.ETHUSDT , Calendar.SECOND , -11360);
    }

    @Test
    void _DB_체결데이터_마지막조회() {
        TradeConclusion tradeConclusion
                = tradeConclusionMapper.selectTradeConclusionLatest(CoinSymbols.ETHUSDT.name());
        System.out.println("signal : " + tradeConclusion.getSignal());
        System.out.println(tradeConclusion.toString());
    }

}