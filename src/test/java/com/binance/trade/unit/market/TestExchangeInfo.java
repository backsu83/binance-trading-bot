package com.binance.trade.unit.market;

import com.binance.connector.client.enums.HttpMethod;
import com.binance.connector.client.exceptions.BinanceConnectorException;
import com.binance.connector.client.impl.SpotClientImpl;
import com.binance.connector.client.utils.UrlBuilder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockWebServer;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import com.binance.trade.unit.MockWebServerDispatcher;

public class TestExchangeInfo {
    private MockWebServer mockWebServer;
    private String baseUrl;
    private final String prefix = "/";
    private final String MOCK_RESPONSE = "{\"key_1\": \"value_1\", \"key_2\": \"value_2\"}";

    @Before
    public void init() {
        this.mockWebServer = new MockWebServer();
        this.baseUrl = mockWebServer.url(prefix).toString();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testExchangeInfo() {
        String path = "/api/v3/exchangeInfo";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(null, null, baseUrl);
        String result = client.createMarket().exchangeInfo(parameters);
        assertEquals(MOCK_RESPONSE, result);
    }

    @Test
    public void testExchangeInfoWithOneSymbol() {
        String path = "/api/v3/exchangeInfo?symbol=BNBUSD";
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        parameters.put("symbol", "BNBUSDT");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(null, null, baseUrl);
        String result = client.createMarket().exchangeInfo(parameters);
        assertEquals(MOCK_RESPONSE, result);
    }

    @Test
    public void testExchangeInfoWithMultipleSymbol() {
        String path = String.format("/api/v3/exchangeInfo?symbols=%s",
                                    UrlBuilder.urlEncode("[\"BNBUSDT\",\"BTCUSDT\"]"));
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        ArrayList<String> symbols = new ArrayList<>();
        symbols.add("BNBUSDT");
        symbols.add("BTCUSDT");
        parameters.put("symbols", symbols);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        SpotClientImpl client = new SpotClientImpl(null, null, baseUrl);
        String result = client.createMarket().exchangeInfo(parameters);
        assertEquals(MOCK_RESPONSE, result);
    }

    @Test
    public void testExchangeInfoWithInvalidType() {
        String path = String.format("/api/v3/exchangeInfo?symbols=%s",
                UrlBuilder.urlEncode("[\"BNBUSDT\",\"BTCUSDT\"]"));
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        String[] symbols = {"BNBUSDT", "BTCUSDT"};
        parameters.put("symbols", symbols);

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        thrown.expect(BinanceConnectorException.class);
        SpotClientImpl client = new SpotClientImpl(null, null, baseUrl);
        client.createMarket().exchangeInfo(parameters);
    }

    @Test
    public void testExchangeInfoWithDoubleParameter() {
        String path = String.format("/api/v3/exchangeInfo?symbols=%s",
                UrlBuilder.urlEncode("[\"BNBUSDT\",\"BTCUSDT\"]"));
        LinkedHashMap<String,Object> parameters = new LinkedHashMap<>();
        String[] symbols = {"BNBUSDT", "BTCUSDT"};
        parameters.put("symbols", symbols);
        parameters.put("symbol", "ETHUSDT");

        Dispatcher dispatcher = MockWebServerDispatcher.getDispatcher(prefix, path, MOCK_RESPONSE, HttpMethod.GET, 200);
        mockWebServer.setDispatcher(dispatcher);

        thrown.expect(BinanceConnectorException.class);
        SpotClientImpl client = new SpotClientImpl(null, null, baseUrl);
        client.createMarket().exchangeInfo(parameters);
    }
}
