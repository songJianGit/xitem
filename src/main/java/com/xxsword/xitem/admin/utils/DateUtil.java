package com.xxsword.xitem.admin.utils;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.concurrent.TimeUnit;

public class DateUtil {
    public static final DateTimeFormatter sdfA1 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter sdfA2 = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter sdfA3 = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter sdfA4 = DateTimeFormat.forPattern("yyyy/MM/dd");
    public static final DateTimeFormatter sdfA5 = DateTimeFormat.forPattern("yyyy年MM月dd日HH时");
    public static final DateTimeFormatter sdfA6 = DateTimeFormat.forPattern("yyyy年MM月dd日");
    public static final DateTimeFormatter sdfB1 = DateTimeFormat.forPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter sdfB2 = DateTimeFormat.forPattern("yyyyMMddHHmmssSSS");
    public static final DateTimeFormatter sdfB3 = DateTimeFormat.forPattern("yyyyMMdd");
    public static final DateTimeFormatter sdfC1 = DateTimeFormat.forPattern("yyyy");
    public static final DateTimeFormatter sdfC2 = DateTimeFormat.forPattern("yyyyMM");
    public static final DateTimeFormatter sdfD3 = DateTimeFormat.forPattern("MM-dd");

    public static String now() {
        return new DateTime().toString(sdfA1);
    }

    public static String day() {
        return new DateTime().toString(sdfA3);
    }

    public static String now(DateTimeFormatter sdf) {
        return new DateTime().toString(sdf);
    }

    /**
     * 获取几分钟前时间
     *
     * @param date
     * @param num  1表示一分钟后 -1表示一分钟前
     * @return
     */
    public static DateTime getMinute(DateTime date, Integer num) {
        if (num > 0) {
            return date.plusMinutes(num);
        } else {
            return date.minusMinutes(Math.abs(num));
        }
    }

    /**
     * 获取几天前时间
     *
     * @param date
     * @param num  1表示一天后-1表示一天前
     * @return
     */
    public static DateTime getDay(DateTime date, Integer num) {
        if (num > 0) {
            return date.plusDays(num);
        } else {
            return date.minusDays(Math.abs(num));
        }
    }

    /**
     * 获取上一个月 -1， 获取下一个月 1
     *
     * @param date
     * @param num
     * @return
     */
    public static DateTime getBeforeMonth(DateTime date, int num) {
        if (num > 0) {
            return date.plusMonths(num);
        } else {
            return date.minusMonths(Math.abs(num));
        }
    }

    /**
     * 获取上一秒 -1， 获取下一秒 1
     *
     * @param date
     * @param num
     * @return
     */
    public static DateTime getSeconds(DateTime date, Integer num) {
        if (num > 0) {
            return date.plusSeconds(num);
        } else {
            return date.minusSeconds(Math.abs(num));
        }
    }

    /**
     * 两个时间相差秒数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Long differSecond(DateTime startDate, DateTime endDate) {
        Duration duration = new Duration(startDate, endDate);
        return duration.getStandardSeconds();
    }

    public static Long differSecond(String startDate, String endDate, DateTimeFormatter sdf) {
        Duration duration = new Duration(DateTime.parse(startDate, sdf), DateTime.parse(endDate, sdf));
        return duration.getStandardSeconds();
    }

    //本月第一天
    public static String firstDayOfCurrentMouth(DateTimeFormatter dateTimeFormatter) {
        LocalDate firstDayOfCurrentMouth = LocalDate.now().dayOfMonth().withMinimumValue();
        return firstDayOfCurrentMouth.toString(dateTimeFormatter);
    }

    //本月最后天一天
    public static String lastDayOfCurrentMouth(DateTimeFormatter dateTimeFormatter) {
        LocalDate lastDayOfCurrentMouth = LocalDate.now().dayOfMonth().withMaximumValue();
        return lastDayOfCurrentMouth.toString(dateTimeFormatter);
    }

    public static String calculateTimeDifference(String startDateTime, String endDateTime) {
        return calculateTimeDifference(startDateTime, endDateTime, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * * Joda-Time 计算两个时间差（年，月，星期，日，小时，分钟，秒，毫秒）   注： 开始时间 和 结束时间 格式须相同
     *
     * @param startDateTime 开始时间
     * @param endDateTime   结束时间
     * @param dateTimeType  时间格式（2018年01月20日 21:02:37（yyyy年MM月dd日 HH:mm:ss））
     * @return 中文描述的时间信息
     */
    public static String calculateTimeDifference(String startDateTime, String endDateTime, String dateTimeType) {
        DateTimeFormatter format = DateTimeFormat.forPattern(dateTimeType);
        DateTime dateTimeStart = format.parseDateTime(startDateTime);
        DateTime dateTimeEnd = format.parseDateTime(endDateTime);
        if (dateTimeStart.isAfter(dateTimeEnd)) {
            DateTime temp = dateTimeStart;
            dateTimeStart = dateTimeEnd;
            dateTimeEnd = temp;
        }
        Interval interval = new Interval(dateTimeStart.getMillis(), dateTimeEnd.getMillis());
        Period p = interval.toPeriod();
        String str = "";
        if (p.getYears() != 0) {
            str += p.getYears() + "年";
        }
        if (p.getMonths() != 0) {
            str += p.getMonths() + "个月";
        }
        if (p.getWeeks() != 0) {
            str += p.getWeeks() + "星期";
        }
        if (p.getDays() != 0) {
            str += p.getDays() + "天";
        }
        if (p.getHours() != 0) {
            str += p.getHours() + "小时";
        }
        if (p.getMinutes() != 0) {
            str += p.getMinutes() + "分钟";
        }
        if (p.getSeconds() != 0) {
            str += p.getSeconds() + "秒";
        }
        if (p.getMillis() != 0) {
            str += p.getMillis() + "毫秒";
        }
        return str;
    }

    /**
     * 将秒转化为 00:00:00的格式
     *
     * @return
     */
    public static String sToHHmmss(Long seconds) {
        if (seconds == null) {
            return "00:00:00";
        }
        long hours = TimeUnit.SECONDS.toHours(seconds);
        long remainingMinutes = TimeUnit.SECONDS.toMinutes(seconds) - TimeUnit.HOURS.toMinutes(hours);
        return String.format("%02d:%02d:%02d", hours, remainingMinutes, seconds % 60);
    }
}
