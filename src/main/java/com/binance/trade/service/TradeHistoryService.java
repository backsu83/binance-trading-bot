package com.binance.trade.service;


import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.enums.TimeUtils;
//import com.binance.trade.client.model.trade.Order;
import com.binance.trade.mapper.TradeConclusionMapper;
import com.binance.trade.mapper.TradeHistoryMapper;
import com.binance.trade.mapper.TradeRsiMapper;
import com.binance.trade.model.TradeConclusion;
import com.binance.trade.model.TradeHistory;
import com.binance.trade.model.TradeRsi;
import com.binance.trade.model.TradeVolumeExplosion;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TradeHistoryService {

    private final TradeHistoryMapper tradeHistoryMapper;
    private final TradeConclusionMapper tradeConclusionMapper;
    private final TradeRsiMapper tradeRsiMapper;
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(TradeHistoryService.class);

//    public int setOrderInfo(Order orderInfo) {
//        return tradeConclusionMapper.insertTradeHistory(orderInfo);
//    }

    public void tradehistroy(CoinSymbols symbol) {
        List<TradeHistory> tradeList = getTradeListByBinance(symbol.name());
        saveTradeHistroy(symbol , tradeList);
    }

    public void tradeConclusion(CoinSymbols symbol , int type , int second) {

        List<TradeHistory> tradeListRange = getTradeListRange(symbol.getTag() , type, second);
        //체결데이터 카운터
        long count = tradeListRange.stream().count();
        if(count < 1) {
            logger.info("검색된 거래체결 내용이 없습니다.");
            return;
        }

        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        BigDecimal avgPrice = null;
        String signal = "EQUAL";

        //최소값,최대값,평균 구하기
        List<TradeHistory> collects = tradeListRange.stream()
                .sorted(Comparator.comparing(TradeHistory::getPrice))
                .collect(Collectors.toList());

//        int count = 0;
        for (TradeHistory collect : collects) {
            if(minPrice == null) {
                minPrice = collect.getPrice();
            }
            maxPrice = collect.getPrice();
        }

        BigDecimal sum = collects
                .stream()
                .map(x -> x.getPrice())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        avgPrice = sum.divide(new BigDecimal(count), 8 , RoundingMode.HALF_DOWN);

        TradeConclusion tradeConclusion = tradeConclusionMapper.selectTradeConclusionLatest(symbol.name());
        if(!ObjectUtils.isEmpty(tradeConclusion)) {
            if(tradeConclusion.getAvg().compareTo(avgPrice) < 0) {
                signal = "UP";
            } else if(tradeConclusion.getAvg().compareTo(avgPrice) > 0) {
                signal = "DOWN";
            } else {
                logger.info(symbol + " 거래체결 평균값에 대한 변경이 없습니다.");
                return;
            }
        }

        TradeConclusion conclusion = TradeConclusion.builder()
                .symbol(symbol.name())
                .signal(signal)
                .second(Math.abs(second))
                .min(minPrice)
                .max(maxPrice)
                .avg(avgPrice)
                .dev(maxPrice.subtract(minPrice))
                .count(collects.size())
                .trade_at(LocalDateTime.now())
                .build();
        tradeConclusionMapper.insertTradeConclusion(conclusion);
    }

    public List<TradeHistory> getTradeListByBinance(String symbol) {

        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl();
        List<TradeHistory> tradeHistorys = new ArrayList();
        parameters.put("symbol", symbol);
        parameters.put("limit", 100);
        String result = client.createMarket().trades(parameters);

        try {
            tradeHistorys = objectMapper.readValue(result, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return tradeHistorys;
    }

    public String selectTradeConclusionUpDown(){
        List<TradeConclusion> recentList = tradeConclusionMapper.selectTradeConclusionUpDown();
        int i = 0;
        List<String> signals = new ArrayList<>();
        String flagSignal = "";
        for (TradeConclusion list : recentList) {
            if (i != 3) {
                signals.add(list.getSignal());
            } else {
                flagSignal = list.getSignal();
            }
            i++;
        }
        List<String> resultSignals = signals.stream().distinct().collect(Collectors.toList());
        if (resultSignals.size() == 1 && !flagSignal.equals(resultSignals.get(0))) {
            if (flagSignal.equals("UP")) {
                return "long";
            } else {
                return "short";
            }
        }
        return null;
    }

    public List<TradeHistory> getTradeListRange(String tag, int type , int second) {
        long end = TimeUtils.getCurrentTimestamp();
        long start = TimeUtils.getTimestampRange(type , second);
        List<TradeHistory> tradeHistories = tradeHistoryMapper.selectTradeHistory(tag , start , end);
        return tradeHistories;
    }

    public void saveTradeHistroy(CoinSymbols symbol, List<TradeHistory> tradeHistorys) {
        for (TradeHistory tradeHistory : tradeHistorys) {
            tradeHistory.setSymbol(symbol.name());
            tradeHistory.setTag(symbol.getTag());
            tradeHistory.setTranId(tradeHistory.getId());
            tradeHistoryMapper.insertTradeHistory(tradeHistory);
        }
    }

    public int getRsi(CoinSymbols symbol) {
        List<TradeRsi> tradeRsi = tradeRsiMapper.selectTradeRsi(symbol.getTag());
        List<BigDecimal> data = new ArrayList<>();
        List<BigDecimal> U = new ArrayList<>();
        List<BigDecimal> D = new ArrayList<>();

        Collections.reverse(tradeRsi);

        for (TradeRsi rsi : tradeRsi) {
//            System.out.println(rsi.getPrice());
            data.add(rsi.getPrice());
        }

/*
        ArrayList<BigDecimal> data = new ArrayList<BigDecimal>(Arrays.asList(BigDecimal.valueOf(9),
                BigDecimal.valueOf(13),
                BigDecimal.valueOf(24),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(1),
                BigDecimal.valueOf(5),
                BigDecimal.valueOf(7)));
*/
        int i = 0;
        for (BigDecimal p : data) {
            if (data.size()-1 == i) break;
//            System.out.println("========= index : " + i + "  ============");
//            System.out.println(data.get(i+1) + " - " + p);
//            System.out.println(data.get(i+1).subtract(p));
            if (data.get(i+1).subtract(p).compareTo(BigDecimal.valueOf(0)) > 0) {
                U.add(data.get(i+1).subtract(p));
                D.add(BigDecimal.valueOf(0));
            } else {
                U.add(BigDecimal.valueOf(0));
                D.add(data.get(i+1).subtract(p).multiply(BigDecimal.valueOf(-1)));
            }

            i++;
        }

//        System.out.println(U);
//        System.out.println(D);
        BigDecimal AU = BigDecimal.valueOf(0);
        BigDecimal AD = BigDecimal.valueOf(0);
        BigDecimal RSI = BigDecimal.valueOf(0);
        for (BigDecimal b : U) {
            AU = AU.add(b);
        }
        AU = AU.divide(BigDecimal.valueOf(U.size()), 8, RoundingMode.HALF_UP);

        for (BigDecimal b : D) {
            AD = AD.add(b);
        }
        AD = AD.divide(BigDecimal.valueOf(D.size()), 8, RoundingMode.HALF_UP);

        BigDecimal RS = AU.divide(AD, 8, RoundingMode.DOWN);

//        System.out.println("AU : " + AU);
//        System.out.println("AD : " + AD);
//        System.out.println("RS : " + RS);

        RSI = RS.divide(RS.add(BigDecimal.valueOf(1)),8, RoundingMode.HALF_UP);
        RSI = RSI.multiply(BigDecimal.valueOf(100));
        int resultRsi = RSI.intValue();
//        System.out.println("RSI : " + resultRsi);
        return resultRsi;
    }

    public void tradeVolumeReport(CoinSymbols aptusdt) {
        tradeHistoryMapper.insertTradeVolume(aptusdt.getTag());
    }

    public TradeVolumeExplosion checkTradeVolumeExplosion(CoinSymbols aptusdt) {
        return tradeHistoryMapper.checkTradeVolumeExplosion(aptusdt.getTag());
    }
}

