package com.binance.trade.service;

import com.binance.trade.client.enums.TimeUtils;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class TimeStampTest {

    @Test
    void timestamp() {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(TimeUtils.getCurrentTimestamp());
        System.out.println(TimeUtils.getTimeToLocalDatetime(timestamp.getTime()));
    }

    @Test
    void timestampSecond() {
        long from = TimeUtils.getCurrentTimestamp();
        long to = TimeUtils.getTimestampRange(Calendar.SECOND , -3);

        System.out.println(TimeUtils.getTimeToLocalDatetime(from));
        System.out.println(TimeUtils.getTimeToLocalDatetime(to));
    }

    @Test
    void timestampByDuration() {
        Timestamp timestamp = new Timestamp(new Date().getTime());
        System.out.println(timestamp);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(timestamp.getTime());

        // subtract 30 seconds
        cal.add(Calendar.SECOND, -30);
        timestamp = new Timestamp(cal.getTime().getTime());
        System.out.println(timestamp.getTime());

        // subtract 10 hours
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.HOUR, -10);
        timestamp = new Timestamp(cal.getTime().getTime());
        System.out.println(timestamp.getTime());

        // subtract 20 days
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.DAY_OF_MONTH, -20);
        timestamp = new Timestamp(cal.getTime().getTime());
        System.out.println(timestamp.getTime());

        // subtract 12 months
        cal.setTimeInMillis(timestamp.getTime());
        cal.add(Calendar.MONTH, -12);
        timestamp = new Timestamp(cal.getTime().getTime());
        System.out.println(timestamp.getTime());
    }

    @Test
    void name() {

        BigDecimal bigDecimal = new BigDecimal(18135.52000000);

        BigDecimal divide = bigDecimal.divide(new BigDecimal(2), 8 , RoundingMode.HALF_DOWN);
        System.out.println(divide);

    }
}


