package com.binance.trade.examples.pay;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public class Transactions {
    private static final Logger logger = LoggerFactory.getLogger(Transactions.class);
    public static void main(String[] args) {
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createPay().transactions(parameters);
        logger.info(result);
    }
}
