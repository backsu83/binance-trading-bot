package com.binance.trade.mapper;

import com.binance.trade.model.TradeRsi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TradeRsiMapper {
    List<TradeRsi> selectTradeRsi(String tag);
}
