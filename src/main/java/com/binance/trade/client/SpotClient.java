package com.binance.trade.client;

import com.binance.trade.client.impl.spot.BSwap;
import com.binance.trade.client.impl.spot.Blvt;
import com.binance.trade.client.impl.spot.C2C;
import com.binance.trade.client.impl.spot.Convert;
import com.binance.trade.client.impl.spot.CryptoLoans;
import com.binance.trade.client.impl.spot.Fiat;
import com.binance.trade.client.impl.spot.Futures;
import com.binance.trade.client.impl.spot.Margin;
import com.binance.trade.client.impl.spot.Market;
import com.binance.trade.client.impl.spot.Mining;
import com.binance.trade.client.impl.spot.NFT;
import com.binance.trade.client.impl.spot.Pay;
import com.binance.trade.client.impl.spot.Rebate;
import com.binance.trade.client.impl.spot.Savings;
import com.binance.trade.client.impl.spot.SubAccount;
import com.binance.trade.client.impl.spot.Trade;
import com.binance.trade.client.impl.spot.UserData;
import com.binance.trade.client.impl.spot.Wallet;


public interface SpotClient {
    Blvt createBlvt();
    BSwap createBswap();
    C2C createC2C();
    Convert createConvert();
    CryptoLoans createCryptoLoans();
    Fiat createFiat();
    Futures createFutures();
    Market createMarket();
    Margin createMargin();
    Mining createMining();
    NFT createNFT();
    Pay createPay();
    Rebate createRebate();
    Savings createSavings();
    SubAccount createSubAccount();
    Trade createTrade();
    UserData createUserData();
    Wallet createWallet();
}
