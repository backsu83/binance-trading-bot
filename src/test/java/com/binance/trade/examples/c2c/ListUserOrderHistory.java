package com.binance.trade.examples.c2c;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ListUserOrderHistory {
    private static final Logger logger = LoggerFactory.getLogger(ListUserOrderHistory.class);
    public static void main(String[] args) {
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("tradeType", "BUY");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createC2C().listUserOrderHistory(parameters);
        logger.info(result);
    }
}
