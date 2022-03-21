package com.binance.trade.service;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class SlackMessage {

    public void send(String signal,String symbol ,int second, int limit, double per, double over) {
        //https://hooks.slack.com/services/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
        StringBuilder message = new StringBuilder();
        message.append("코인심볼: ")
                .append(symbol + "\n")
                .append("시그널: ")
                .append(signal + "\n")
                .append("총시간(초단위): ")
                .append(second*limit + "\n")
                .append("데이터 수집간격(초단위): ")
                .append(second + "\n")
                .append(signal+" 비율: ")
                .append(per + "\n")
                .append("체결수 비율(100건이상): ")
                .append(over + "\n")
                .toString();

        Map<String, StringBuilder> bodyMap = new HashMap();
        bodyMap.put("text", message);
        WebClient webClient = WebClient.create("https://hooks.slack.com");
        webClient.post()
                .uri("/services/T4XF9PJR3/B036ZNJKBLL/13F3dFEg5tIvr0SWy9BBoLdO")
                .header(HttpHeaders.CONTENT_TYPE , MediaType.APPLICATION_JSON_VALUE)
                .body(BodyInserters.fromValue(bodyMap))
                .exchange()
                .block();
    }
}
