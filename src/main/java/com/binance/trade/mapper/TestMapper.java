package com.binance.trade.mapper;

import com.binance.trade.model.TradeTest;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TestMapper {

    List<TradeTest> selectTest();

    @Select("SELECT * FROM trade.test")
    List<TradeTest> selectTest2();


}
