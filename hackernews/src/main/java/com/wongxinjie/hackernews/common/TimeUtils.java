package com.wongxinjie.hackernews.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeUtils {

    private static final Logger log = LoggerFactory.getLogger(TimeUtils.class);
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";


    public static String localTimeText(Date timestamp) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
        String text = dateFormat.format(timestamp);
        return text;
    }

    /**
     * 获取当前的时间戳
     * @return YYYY-MM-DD hh:mm:ss
     */
    public static String currentTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);
        String now = dateFormat.format(new Date());
        return now;
    }

    /**
     *
     * @param target the datetime, set null to default now
     * @param days
     * @param hours
     * @param minutes
     * @param seconds
     * @return
     */
    public static String timedelta(String target, Integer days, Integer hours, Integer minutes, Integer seconds) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATETIME_PATTERN);

        Date targetTime = null;
        if(target != null) {
            try{
                targetTime = dateFormat.parse(target);
            } catch (ParseException e) {
                log.error("parse time error", e);
                return null;
            }
        } else {
            targetTime = new Date();
        }

        LocalDateTime localDateTime = targetTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if(days != null) {
            localDateTime = localDateTime.plusDays(days);
        }
        if(hours != null) {
            localDateTime = localDateTime.plusHours(hours);
        }
        if(minutes != null) {
            localDateTime = localDateTime.plusMinutes(minutes);
        }
        if(seconds != null) {
            localDateTime = localDateTime.plusSeconds(seconds);
        }

        Date targetDate = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
        return dateFormat.format(targetDate);
    }

    public static int currentTimeSecond() {
        return (int)(System.currentTimeMillis() / 1000);
    }

    public static void main(String[] args) {
        Date now = new Date();
        System.out.println(TimeUtils.localTimeText(now));
    }

}