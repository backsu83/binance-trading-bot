package com.binance.trade.service;


import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.client.enums.CoinSymbols;
import com.binance.trade.client.enums.TimeUtils;
import com.binance.trade.mapper.TradeConclusionMapper;
import com.binance.trade.mapper.TradeHistoryMapper;
import com.binance.trade.model.TradeConclusion;
import com.binance.trade.model.TradeHistory;
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
    private final ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(TradeHistoryService.class);

    public void tradehistroy(String symbol) {
        List<TradeHistory> tradeList = getTradeListByBinance(symbol);
        saveTradeHistroy(symbol , tradeList);
    }

    public void tradeConclusion(String symbol , int type , int second) {

        List<TradeHistory> tradeListRange = getTradeListRange(symbol, type, second);
        //체결데이터 카운터
        long count = tradeListRange.stream().count();
        if(count < 1) {
            logger.info("검색된 거래체결 내용이 없습니다.");
            return;
        }

        BigDecimal minPrice = null;
        BigDecimal maxPrice = null;
        BigDecimal avgPrice = null;
        String signal = "UP";

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

        TradeConclusion tradeConclusion = tradeConclusionMapper.selectTradeConclusionLatest(symbol);
        if(!ObjectUtils.isEmpty(tradeConclusion)) {
            if(tradeConclusion.getAvg().compareTo(avgPrice) < 0) {
                signal = "UP";
            } else if(tradeConclusion.getAvg().compareTo(avgPrice) > 0) {
                signal = "DOWN";
            } else {
                logger.info(symbol + " 거래체결 평균값에 대한 변경이 없습니다.");
                return;
            }
        } else {
            logger.info(symbol + " 마지막 거래체결 데이터 조회 실패했습니다.");
            return;
        }

        TradeConclusion conclusion = TradeConclusion.builder()
                .symbol(symbol)
                .signal(signal)
                .second(Math.abs(second))
                .min(minPrice)
                .max(maxPrice)
                .avg(avgPrice)
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

    public List<TradeHistory> getTradeListRange(String symbol, int type , int second) {
        long end = TimeUtils.getCurrentTimestamp();
        long start = TimeUtils.getTimestampRange(type , second);
        List<TradeHistory> tradeHistories = new ArrayList<>();

        if(symbol.equals(CoinSymbols.ETHUSDT.name())) {
            tradeHistories = tradeHistoryMapper.selectTradeHistoryETH(start , end);
        } else if(symbol.equals(CoinSymbols.BTCUSDT.name())) {
            tradeHistories = tradeHistoryMapper.selectTradeHistoryBTC(start , end);
        }
        return tradeHistories;
    }

    public void saveTradeHistroy(String symbol, List<TradeHistory> tradeHistorys) {
        for (TradeHistory tradeHistory : tradeHistorys) {
            tradeHistory.setSymbol(symbol);
            tradeHistory.setTranId(tradeHistory.getId());

            if(symbol.equals(CoinSymbols.ETHUSDT.name())) {
                tradeHistoryMapper.insertTradeHistoryETH(tradeHistory);
            } else if(symbol.equals(CoinSymbols.BTCUSDT.name())) {
                tradeHistoryMapper.insertTradeHistoryBTC(tradeHistory);
            }
        }
    }
}

