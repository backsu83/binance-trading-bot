package com.binance.trade.examples.savings;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InterestHistory {
    private static final Logger logger = LoggerFactory.getLogger(InterestHistory.class);
    public static void main(String[] args) {
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("lendingType", "DAILY");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createSavings().interestHistory(parameters);
        logger.info(result);
    }
}
