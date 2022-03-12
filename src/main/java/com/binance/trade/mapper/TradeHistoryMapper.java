package com.binance.trade.mapper;

import com.binance.trade.model.TradeHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryMapper {

//    List<TradeHistory> selectTradeHistory();
    List<TradeHistory> selectTradeHistory(@Param("start") long start , @Param("end") long end);
    int insertTradeHistory(TradeHistory tradeHistory);
}
