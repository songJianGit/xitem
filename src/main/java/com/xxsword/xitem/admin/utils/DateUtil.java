package com.xxsword.xitem.admin.utils;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateUtil {
    public static final DateTimeFormatter sdfA1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter sdfA2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter sdfA3 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter sdfA4 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter sdfA5 = DateTimeFormatter.ofPattern("yyyy年MM月dd日HH时");
    public static final DateTimeFormatter sdfA6 = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
    public static final DateTimeFormatter sdfB1 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
    public static final DateTimeFormatter sdfB2 = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
    public static final DateTimeFormatter sdfB3 = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter sdfC1 = DateTimeFormatter.ofPattern("yyyy");
    public static final DateTimeFormatter sdfC2 = DateTimeFormatter.ofPattern("yyyyMM");
    public static final DateTimeFormatter sdfD3 = DateTimeFormatter.ofPattern("MM-dd");

    public static String now() {
        return LocalDateTime.now().format(sdfA1);
    }

    public static String day() {
        return LocalDateTime.now().format(sdfA3);
    }

    public static String now(DateTimeFormatter sdf) {
        return LocalDateTime.now().format(sdf);
    }

    /**
     * 获取几分钟前时间
     *
     * @param date
     * @param num  1表示一分钟后 -1表示一分钟前
     * @return
     */
    public static LocalDateTime getMinute(LocalDateTime date, Integer num) {
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
    public static LocalDateTime getDay(LocalDateTime date, Integer num) {
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
    public static LocalDateTime getBeforeMonth(LocalDateTime date, int num) {
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
    public static LocalDateTime getSeconds(LocalDateTime date, Integer num) {
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
    public static Long differSecond(LocalDateTime startDate, LocalDateTime endDate) {
        Duration duration = Duration.between(startDate, endDate);
        return duration.getSeconds(); // 注意：返回 long，不是 Long
    }

    public static Long differSecond(String startDate, String endDate, DateTimeFormatter sdf) {
        Duration duration = Duration.between(LocalDateTime.parse(startDate, sdf), LocalDateTime.parse(endDate, sdf));
        return duration.getSeconds();
    }

    public static long differDay(String startDate, String endDate, DateTimeFormatter formatter) {
        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);
        return ChronoUnit.DAYS.between(start, end); // 返回 end - start 的天数
    }

    public static String firstDayOfCurrentMonth(DateTimeFormatter dateTimeFormatter) {
        LocalDate firstDay = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        return firstDay.format(dateTimeFormatter); // 注意：用 .format()，不是 .toString()
    }

    public static String lastDayOfCurrentMonth(DateTimeFormatter dateTimeFormatter) {
        LocalDate lastDay = LocalDate.now().with(TemporalAdjusters.lastDayOfMonth());
        return lastDay.format(dateTimeFormatter);
    }

    /**
     * 将秒数转换为 HH:mm:ss 格式（支持负数、大数）
     *
     * @param seconds 秒数，null 视为 0
     * @return 格式化字符串，如 "01:01:01"
     */
    public static String sToHHmmss(Long seconds) {
        if (seconds == null) {
            return "00:00:00";
        }

        long totalSeconds = Math.abs(seconds);
        long hours = totalSeconds / 3600;
        long minutes = (totalSeconds % 3600) / 60;
        long secs = totalSeconds % 60;

        String formatted = String.format("%02d:%02d:%02d", hours, minutes, secs);

        // 如果原输入为负数，加负号（可选，根据业务需求）
        return seconds < 0 ? "-" + formatted : formatted;
    }
}