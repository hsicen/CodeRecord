package com.necer.ncalendar.listener;

import org.joda.time.DateTime;

/**
 * Created by necer on 2017/7/4.
 */

public interface OnCalendarChangedListener {
    void onClickCalendar(DateTime dateTime);

    void onCalendarPageChanged(DateTime dateTime);

    void onChangeToWeekView();

    void onChangeToMonthView();
}
