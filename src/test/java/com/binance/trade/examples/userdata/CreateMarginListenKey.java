package com.binance.trade.examples.userdata;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateMarginListenKey {
    private static final Logger logger = LoggerFactory.getLogger(CreateMarginListenKey.class);
    public static void main(String[] args) {
        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);

        String result = client.createUserData().createMarginListenKey();
        logger.info(result);
    }
}
