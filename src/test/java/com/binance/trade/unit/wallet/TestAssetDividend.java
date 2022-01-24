package com.binance.trade.unit.wallet;

import com.binance.connector.client.enums.HttpMethod;
import com.binance.connector.client.impl.SpotClientImpl;
import java.util.LinkedHashMap;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import com.binance.trade.unit.MockWebServerDispatcher;

public class TestAssetDividend {
    private MockWebServer mockWebServer;
    private String baseUrl;
    private final String prefix = "/";
    private final String MOCK_RESPONSE = "{\"key_1\": \"value_1\", \"key_2\": \"value_2\"}";
    private final String apiKey = "apiKey";
    private final String secretKey = "secretKey";

    @Before
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(prefix).toString();
    }

    @Test
    public void testAssetDividend() {
        String path = "/sapi/v1/asset/assetDividend?asset=BNB&startTime=1591063000087&endTime=1591063000087&limit=10";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("asset","BNB");
        parameters.put("startTime","1591063000087");
        parameters.put("endTime","1591063000087");
        parameters.put("limit","10");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, baseUrl);
        String result = client.createWallet().assetDividend(parameters);
        assertEquals(MOCK_RESPONSE, result);
    }
}
