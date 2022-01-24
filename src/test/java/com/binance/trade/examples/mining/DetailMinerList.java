package com.binance.trade.examples.mining;

import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.trade.examples.PrivateConfig;
import java.util.LinkedHashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DetailMinerList {
    private static final Logger logger = LoggerFactory.getLogger(DetailMinerList.class);
    public static void main(String[] args) {
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("algo", "sha256");
        parameters.put("userName", "");
        parameters.put("workerName", "");

        SpotClientImpl client = new SpotClientImpl(PrivateConfig.API_KEY, PrivateConfig.SECRET_KEY);
        String result = client.createMining().detailMinerList(parameters);
        logger.info(result);
    }
}
