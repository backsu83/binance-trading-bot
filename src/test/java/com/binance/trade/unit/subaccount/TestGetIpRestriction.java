package com.binance.trade.unit.subaccount;

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

public class TestGetIpRestriction {
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
    public void testGetIpRestrictionWithoutParameters() {
        String path = "/sapi/v1/sub-account/subAccountApi/ipRestriction";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        thrown.expect(BinanceConnectorException.class);
        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, baseUrl);
        client.createSubAccount().getIpRestriction(parameters);
    }

    @Test
    public void testGetIpRestriction() {
        String path = String.format("/sapi/v1/sub-account/subAccountApi/ipRestriction?email=%s&subAccountApiKey=abc",
                UrlBuilder.urlEncode("alice@test.com"));;
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("email","alice@test.com");
        parameters.put("subAccountApiKey", "abc");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(apiKey, secretKey, baseUrl);
        String result = client.createSubAccount().getIpRestriction(parameters);
        assertEquals(MOCK_RESPONSE, result);
    }
}
