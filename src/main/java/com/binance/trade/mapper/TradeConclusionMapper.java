package com.binance.trade.mapper;

import com.binance.trade.model.TradeConclusion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeConclusionMapper {

    List<TradeConclusion> selectTradeConclusion();
    TradeConclusion selectTradeConclusionLatest(@Param("symbol") String symbol);
    int insertTradeConclusion(TradeConclusion tradeHistory);

}
