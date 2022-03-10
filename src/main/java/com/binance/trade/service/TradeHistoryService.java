package com.binance.trade.service;


import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.mapper.TradeHistoryMapper;
import com.binance.trade.model.TradeHistory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TradeHistoryService {


    @Autowired
    private TradeHistoryMapper tradeHistoryMapper;

    private static final Logger logger = LoggerFactory.getLogger(TradeHistoryService.class);

    public void getTradeList(String symbol) {

        if (symbol.isEmpty()) {
            symbol = "ETHUSDT";
        }

        ObjectMapper objectMapper = new ObjectMapper();

        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        SpotClientImpl client = new SpotClientImpl();
        List<TradeHistory> tradeHistorys = new ArrayList();

        parameters.put("symbol", symbol);
        parameters.put("limit", 100);

        String result = client.createMarket().trades(parameters);
        logger.info(result);

        try {
            tradeHistorys = objectMapper.readValue(result, new TypeReference<>(){});
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }


        for (TradeHistory tradeHistory : tradeHistorys) {
            tradeHistory.setSymbol("ETHUSDT");
            tradeHistory.setTranId(tradeHistory.getId());
            logger.info("tradeHistory : {} ", tradeHistory.toString());
            tradeHistoryMapper.insertTradeHistory(tradeHistory);
        }
        logger.info(result);
    }

}

