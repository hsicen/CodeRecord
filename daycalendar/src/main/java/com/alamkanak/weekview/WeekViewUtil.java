package com.alamkanak.weekview;

import android.content.Context;

import java.util.Calendar;

/**
 * <p>作者：黄思程  2018/5/3 15:15
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：WeekView工具类
 */
public class WeekViewUtil {

    /*** 判断两个时刻是否为同一天
     ***  @param dayOne 时刻一
     ***  @param dayTwo 时刻二
     ***  @return true/false  */
    public static boolean isSameDay(Calendar dayOne, Calendar dayTwo) {

        return dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)
                && dayOne.get(Calendar.DAY_OF_YEAR) == dayTwo.get(Calendar.DAY_OF_YEAR);
    }

    /*** 获取今天的时间
     ***  @return 今天 */
    public static Calendar today() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 12);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);
        return today;
    }


    /*** 将dip或dp值转换为px值，保证尺寸大小不变
     ***  @param dipValue 要转换的dp值
     ***  @param mContext mContext
     ***  @return 转换后的px */
    public static int dip2px(Context mContext, float dipValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }


    /*** 将sp值转换为px值，保证文字大小不变
     ***  @param spValue  要转换的sp值
     ***  @param mContext mContext
     ***  @return sp转换为px的结果 */
    public static int sp2px(Context mContext, float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
