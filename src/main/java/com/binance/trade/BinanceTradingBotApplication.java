package com.binance.trade;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BinanceTradingBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(BinanceTradingBotApplication.class, args);
    }
}
