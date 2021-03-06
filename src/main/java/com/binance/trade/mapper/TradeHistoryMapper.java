package com.binance.trade.mapper;

import com.binance.trade.model.TradeHistory;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryMapper {

    int insertTradeHistory(TradeHistory tradeHistory);

    List<TradeHistory> selectTradeHistory(@Param("tag") String tag,
                                          @Param("start") long start,
                                          @Param("end") long end);


}
