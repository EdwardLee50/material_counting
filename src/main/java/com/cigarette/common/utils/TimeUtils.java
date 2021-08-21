package com.cigarette.common.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Date;
import java.util.TimeZone;

/**
 * @author EdwardLee
 */
public class TimeUtils {

    /** 默认时区 */
    public static String DEFAULT_TIMEZONE_STR = "Asia/Shanghai";

    /** 系统默认 日期类型 yyyy-MM-dd */
    public static final String DATE_PATTERN_DEFAULT = "yyyy-MM-dd 00：:0:00";

    /** 时间 日期类型 HH:mm:ss */
    public static final String DATE_PATTERN_TIME = "HH:mm:ss";

    /** 日期时间 日期类型 yyyy-MM-dd HH:mm:ss */
    public static final String DATE_PATTERN_DATETIME = "yyyy-MM-dd HH:mm:ss";

    public static DateTime getNow(String timeZoneStr) {
        return DateTime.now(DateTimeZone.forTimeZone(TimeZone.getTimeZone(timeZoneStr)));
    }

    public static DateTime getNow() {
        return getNow(DEFAULT_TIMEZONE_STR);
    }

    public static Date getNowOfDateObj() {
        return getNow(DEFAULT_TIMEZONE_STR).toDate();
    }

    public static long getMillis() {
        return getNow(DEFAULT_TIMEZONE_STR).getMillis();
    }

    public static String getTimeStr(String patternStr, String timeZoneStr) {
        DateTime now = getNow(timeZoneStr);
        DateTimeFormatter pattern = DateTimeFormat.forPattern(patternStr);
        return now.toString(pattern);
    }

    public static String getTimeStr(String patternStr) {
        return getTimeStr(patternStr,DEFAULT_TIMEZONE_STR);
    }

    public static String getTimeStr() {
        return getTimeStr(DATE_PATTERN_DATETIME, DEFAULT_TIMEZONE_STR);
    }

    public static Date dateTime2Date(DateTime dt){
        return dt.toDate();
    }

    public static DateTime date2DateTime(Date date){
        return new DateTime(date);
    }

    public static DateTime str2DateTime(String str){
        DateTimeFormatter inFormat = DateTimeFormat.forPattern(DATE_PATTERN_DATETIME);
        return inFormat.parseDateTime(str);
    }

    public static String dateTime2Str(DateTime dateTime,String patternStr){
        DateTimeFormatter pattern = DateTimeFormat.forPattern(patternStr);
        return dateTime.toString(pattern);
    }

    public static String dateTime2Str(DateTime dateTime) {
        DateTimeFormatter pattern = DateTimeFormat.forPattern(DATE_PATTERN_DATETIME);
        return dateTime.toString(pattern);
    }

    public static void main(String[] args) {
        String timeStamp = TimeUtils.getTimeStr();
        System.out.println(timeStamp);
        DateTime dateTime = str2DateTime("2021-05-05 10:28:51");
        System.out.println(dateTime2Str(dateTime));
    }
}
