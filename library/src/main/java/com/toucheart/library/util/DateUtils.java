package com.toucheart.library.util;

import org.joda.time.DateTime;

/**
 * <p>作者：黄思程  2017/11/23 13:52
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：日期工具类
 */
public class DateUtils {
    private DateUtils() {

        throw new UnsupportedOperationException("you can't initial this ...");
    }

    /**
     * @param dateTime1 日期1
     * @param dateTime2 日期2
     * @return 是否同年同月
     */
    public static boolean isEqualsMonth(DateTime dateTime1, DateTime dateTime2) {
        return dateTime1.getYear() == dateTime2.getYear()
                && dateTime1.getMonthOfYear() == dateTime2.getMonthOfYear();
    }

    /**
     * @param dateTime 日期1
     * @return 是否为今年今月
     */
    public static boolean isEqualsMonth(DateTime dateTime) {
        DateTime current = DateTime.now();

        return current.getYear() == dateTime.getYear()
                && current.getMonthOfYear() == dateTime.getMonthOfYear();
    }

    /**
     * @param dateTime 日期1
     * @return 是否为同一天
     */
    public static boolean isEqualsDay(DateTime dateTime) {
        DateTime current = DateTime.now();

        return current.getYear() == dateTime.getYear()
                && current.getMonthOfYear() == dateTime.getMonthOfYear()
                && current.getDayOfMonth() == dateTime.getDayOfMonth();
    }

    /**
     * @param dateTime  日期1
     * @param dateTime2 日期2
     * @return 是否为同一天
     */
    public static boolean isEqualsDay(DateTime dateTime, DateTime dateTime2) {

        return dateTime2.getYear() == dateTime.getYear()
                && dateTime2.getMonthOfYear() == dateTime.getMonthOfYear()
                && dateTime2.getDayOfMonth() == dateTime.getDayOfMonth();
    }

    /**
     * @param dateTime 日期
     * @return 是否是今天
     */
    public static boolean isToday(DateTime dateTime) {

        return new DateTime().toLocalDate().equals(dateTime.toLocalDate());
    }

    /**
     * @return 某月第一天是周几
     */
    public static int getFirstDay(int year, int month) {
        int dayOfWeek = new DateTime(year, month, 1, 0, 0, 0).getDayOfWeek();
        if (dayOfWeek == 7) {
            return 0;
        }
        return dayOfWeek;
    }

    /**
     * @param dateTime 日期1
     * @return 判断是否为本月之后的时间
     */
    public static boolean isNextMonth(DateTime dateTime) {
        boolean isNext;
        DateTime current = DateTime.now();

        isNext = dateTime.getYear() > current.getYear()
                || dateTime.getYear() == current.getYear()
                && dateTime.getMonthOfYear() > current.getMonthOfYear();

        return isNext;
    }

    /**
     * @param dateTime 日期1
     * @return 判断是否为本日之后的时间
     */
    public static boolean isNextDay(DateTime dateTime) {
        boolean isNext;
        DateTime current = DateTime.now();

        if (dateTime.getYear() > current.getYear()) {
            isNext = true;
        } else if (dateTime.getYear() == current.getYear()) {
            if (dateTime.getMonthOfYear() > current.getMonthOfYear()) {
                isNext = true;
            } else if (dateTime.getMonthOfYear() == current.getMonthOfYear()) {
                isNext = dateTime.getDayOfMonth() > current.getDayOfMonth();
            } else {
                isNext = false;
            }
        } else {
            isNext = false;
        }

        return isNext;
    }
}
