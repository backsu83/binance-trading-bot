package com.binance.trade.service;

import com.binance.trade.mapper.TradeConclusionMapper;
import com.binance.trade.model.TradeConclusion;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FutureBetLogic {

    private final TradeConclusionMapper tradeConclusionMapper;
    private final SlackMessage slackMessage;
    private static final Logger logger = LoggerFactory.getLogger(FutureBetLogic.class);

    /**
     * 체결결과 데이터 조회
     * 시간단위 거래량 및 시그널을 통해 거래폭 분석
     * @param symbol
     * @param second
     * @param limit
     */
    public void getTadeConclusion(String symbol, int second, int limit , int over) {
        List<TradeConclusion> tradeConclusions
                = tradeConclusionMapper.selectTradeConclusion(symbol , second, limit);

        if(tradeConclusions.size() < limit) {
            logger.info("체결결과 데이터 갯수:{}, 요청 갯수:{}" , tradeConclusions.size() , limit);
            return;
        }

        //UP, DOWN, OVER 비율 계산
        double cntUp = 0.0;
        double cntDown = 0.0;
        double cntOver = 0.0;
        double perUp = 0.0;
        double perDown = 0.0;
        double perOver = 0.0;
        double perSignal = 75.0;

        for (TradeConclusion tradeConclusion : tradeConclusions) {
            if(tradeConclusion.getSignal().equals("UP")) {
                cntUp++;
            } else {
                cntDown++;
            }

            if(tradeConclusion.getCount() > over) {
                cntOver++;
            }
        }

        perUp = Double.parseDouble(String.format("%.1f",(cntUp / limit) * 100.0));
        perDown = Double.parseDouble(String.format("%.1f",(cntDown / limit) * 100.0));
        perOver = Double.parseDouble(String.format("%.1f",(cntOver / limit) * 100.0));

        if(perUp < perSignal && perDown < perSignal) {
            logger.info("UP 비율:{} , DOWN 비율:{}" , perUp , perDown);
            return;
        }

        if(perUp > perSignal && perOver > perSignal) {
            slackMessage.send("UP",symbol,second,limit,perUp,perOver);
        }

        if(perDown > perSignal && perOver > perSignal) {
            slackMessage.send("DOWN",symbol,second,limit,perDown,perOver);
        }
    }
}
