package com.alamkanak.weekview;

import java.util.Calendar;
import java.util.List;

/**
 * <p>作者：黄思程  2018/5/3 15:29
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：日期加载Loader接口
 */
public interface WeekViewLoader {

    /*** Convert a date into a double that will be used to reference when you're loading data.
     ***  All periods that have the same integer part, define one period. Dates that are later in time
     ***  should have a greater return value.
     ***  @param instance the date
     ***  @return The period index in which the date falls (floating point number) */
    double toWeekViewPeriodIndex(Calendar instance);

    /*** Load the events within the period
     *** @param periodIndex the period to load
     *** @return A list with the events of this period */
    List<? extends WeekViewEvent> onLoad(int periodIndex);
}
