package com.timeblog.framework.system.utils;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Dong.Chao
 * @Classname CronUtils
 * @Description 将日期类型转为Cron表达式
 * @Date 2020/3/19 12:17
 * @Version V1.0
 */
public class CronUtils {

    /***
     *
     * @param date
     * @param dateFormat : e.g:yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String formatDateByPattern(Date date, String dateFormat){
        //An instantaneous point on the time-line.(时间线上的一个瞬时点。)
        Instant instant = date.toInstant();
        //A time-zone ID, such as {@code Europe/Paris}.(时区)
        ZoneId zoneId = ZoneId.systemDefault();
        LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
        int hour = localDateTime.getHour();
        //如果是0点 追加8个小时
        if (hour == 0){
            localDateTime = localDateTime.plusHours(8);
        }
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("ss mm HH dd MM ?");
        return dateTimeFormatter.format(localDateTime);
    }
    /***
     * convert Date to cron ,eg.  "0 07 10 15 1 ? 2016"
     * @param date  : 时间点
     * @return
     */
    public static String getCron(Date  date){
        String dateFormat="ss mm HH dd MM ? yyyy";
        return formatDateByPattern(date, dateFormat);
    }
}
