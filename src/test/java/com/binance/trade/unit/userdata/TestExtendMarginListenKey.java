package com.binance.trade.unit.userdata;

import com.binance.connector.client.enums.HttpMethod;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import java.util.LinkedHashMap;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.binance.trade.unit.MockWebServerDispatcher;

public class TestExtendMarginListenKey {
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
    public void testExtendMarginListenKeyWithoutParameters() {
        String path = "/sapi/v1/userDataStream";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.PUT, 200);
        mockWebServer.setDispatcher(dispatcher);

        thrown.expect(BinanceConnectorException.class);
        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, baseUrl);
        client.createUserData().extendMarginListenKey(parameters);
    }

    @Test
    public void testExtendMarginListenKey() {
        String path = "/sapi/v1/userDataStream?listenKey=test";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("listenKey","test");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.PUT, 200);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, baseUrl);
        String result = client.createUserData().extendMarginListenKey(parameters);
        assertEquals(MOCK_RESPONSE, result);
    }
}
