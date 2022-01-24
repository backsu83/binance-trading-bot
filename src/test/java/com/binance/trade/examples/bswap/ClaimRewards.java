package com.binance.trade.examples.bswap;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.examples.PrivateConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedHashMap;

public class ClaimRewards {
    private static final Logger logger = LoggerFactory.getLogger(ClaimRewards.class);
    public static void main(String[] args) {
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createBswap().claimRewards(parameters);
        logger.info(result);
    }
}
