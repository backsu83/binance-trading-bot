package com.binance.trade.mapper;

import com.binance.trade.model.TradeConclusion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeConclusionMapper {

    List<TradeConclusion> selectTradeConclusion(@Param("symbol") String symbol,
                                                @Param("second") int second,
                                                @Param("limit") int limit);

    TradeConclusion selectTradeConclusionLatest(@Param("symbol") String symbol);
    int insertTradeConclusion(TradeConclusion tradeHistory);

}
