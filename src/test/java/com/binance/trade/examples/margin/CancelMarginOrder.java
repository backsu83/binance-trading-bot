package com.binance.trade.examples.margin;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CancelMarginOrder {
    private static final Logger logger = LoggerFactory.getLogger(CancelMarginOrder.class);
    public static void main(String[] args) {
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BNBUSDT");
        parameters.put("orderId", "");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createMargin().cancelOrder(parameters);
        logger.info(result);
    }
}
