package com.binance.trade.mapper;

import com.binance.trade.model.TradeHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryMapper {

//    List<TradeHistory> selectTradeHistory();
    List<TradeHistory> selectTradeHistoryETH(@Param("start") long start , @Param("end") long end);
    int insertTradeHistoryETH(TradeHistory tradeHistory);
    List<TradeHistory> selectTradeHistoryBTC(@Param("start") long start , @Param("end") long end);
    int insertTradeHistoryBTC(TradeHistory tradeHistory);

}
