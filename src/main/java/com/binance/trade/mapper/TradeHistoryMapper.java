package com.binance.trade.mapper;

import com.binance.trade.model.TradeHistory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryMapper {

    List<TradeHistory> selectTradeHistory();
    int insertTradeHistory(TradeHistory tradeHistory);
}
