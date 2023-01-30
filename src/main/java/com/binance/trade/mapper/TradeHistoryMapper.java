package com.binance.trade.mapper;

import com.binance.trade.client.model.trade.Order;
import com.binance.trade.model.TradeHistory;
import com.binance.trade.model.TradeVolumeExplosion;
import com.binance.trade.model.TradeVolumeReport;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeHistoryMapper {

    int insertTradeHistory(TradeHistory tradeHistory);

    List<TradeHistory> selectTradeHistory(@Param("tag") String tag,
                                          @Param("start") long start,
                                          @Param("end") long end);

    void insertTradeVolume(TradeVolumeReport tradeVolumeReport);

    TradeVolumeExplosion checkTradeVolumeExplosion(String tag);

    void insertTradeTransaction(Order order);
}
