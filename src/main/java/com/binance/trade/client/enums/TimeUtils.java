package com.binance.trade.client.enums;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;


public class TimeUtils {

    private static final String strDatetime = "yyyy-MM-dd HH:mm:ss";

    public static long getCurrentTimestamp() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        return timestamp.getTime();
    }

    public static long getTimestampRange(int type , int second) {
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(getCurrentTimestamp());
        cal.add(type , second);
        return new Timestamp(cal.getTime().getTime()).getTime();
    }

    public static String getTimeToLocalDatetime(long timestamp) {
        LocalDateTime localDateTime = new Timestamp(timestamp).toLocalDateTime();
        return localDateTime.format(DateTimeFormatter.ofPattern(strDatetime));
    }
}
