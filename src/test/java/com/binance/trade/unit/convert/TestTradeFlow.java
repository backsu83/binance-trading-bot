package com.binance.trade.unit.convert;

import com.binance.connector.client.enums.HttpMethod;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.UrlBuilder;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.binance.trade.unit.MockWebServerDispatcher;

import java.util.LinkedHashMap;

import static org.junit.Assert.assertEquals;

public class TestTradeFlow {
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

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testTradeFlowWithoutParameters() {
        String path = "/sapi/v1/convert/tradeFlow";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        thrown.expect(BinanceConnectorException.class);
        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, baseUrl);
        client.createConvert().tradeFlow(parameters);
    }

    @Test
    public void testTradeFlow() {
        String path = "/sapi/v1/convert/tradeFlow?startTime=1234567&endTime=1234568";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("startTime", 1234567L);
        parameters.put("endTime", 1234568L);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, baseUrl);
        String result = client.createConvert().tradeFlow(parameters);
        assertEquals(MOCK_RESPONSE, result);
    }
}
