package com.binance.trade.mapper;

import com.binance.trade.model.TradeConclusion;
import com.binance.trade.model.TradeHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeConclusionMapper {

    List<TradeConclusion> selectTradeConclusion();
    TradeConclusion selectTradeConclusionLatest();
    int insertTradeConclusion(TradeConclusion tradeHistory);

}
