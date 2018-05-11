package com.necer.ncalendar.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.widget.Toast;

import com.necer.ncalendar.R;
import com.necer.ncalendar.adapter.CalendarAdapter;
import com.necer.ncalendar.adapter.MonthAdapter;
import com.necer.ncalendar.listener.OnClickMonthCalendarListener;
import com.necer.ncalendar.listener.OnClickMonthViewListener;
import com.necer.ncalendar.listener.OnMonthCalendarChangedListener;
import com.necer.ncalendar.utils.Utils;
import com.necer.ncalendar.view.CalendarView;
import com.necer.ncalendar.view.MonthView;

import org.joda.time.DateTime;

import static com.necer.ncalendar.utils.Utils.isSameMonth;

/**
 * Created by necer on 2017/8/28.
 * QQ群:127278900
 */

public class MonthCalendar extends CalendarPager implements OnClickMonthViewListener {

    private OnClickMonthCalendarListener onClickMonthCalendarListener;
    private OnMonthCalendarChangedListener onMonthCalendarChangedListener;

    private int lastPosition = -1;

    public MonthCalendar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected CalendarAdapter getCalendarAdapter() {

        mPageSize = Utils.getIntervalMonths(startDateTime, endDateTime) + 1;
        mCurrPage = Utils.getIntervalMonths(startDateTime, mInitialDateTime);

        return new MonthAdapter(getContext(), mPageSize, mCurrPage, mInitialDateTime, this);
    }


    @Override
    protected void initCurrentCalendarView(int position) {

        MonthView currView = (MonthView) calendarAdapter.getCalendarViews().get(position);
        MonthView lastView = (MonthView) calendarAdapter.getCalendarViews().get(position - 1);
        MonthView nextView = (MonthView) calendarAdapter.getCalendarViews().get(position + 1);


        if (currView == null) {
            return;
        }

        if (lastView != null)
            lastView.clear();

        if (nextView != null)
            nextView.clear();


        //只处理翻页
        if (lastPosition == -1) {
            currView.setDateTimeAndPoint(mInitialDateTime, pointList);
            currView.setHolidayList(holidayList);
            currView.setWorkdayList(workdayList);
            currView.isShowLunar(isShowLunar);
            currView.setEventList(eventList);
            mSelectDateTime = mInitialDateTime;
            lastSelectDateTime = mInitialDateTime;
            if (onMonthCalendarChangedListener != null) {
                onMonthCalendarChangedListener.onMonthCalendarChanged(mSelectDateTime);
            }
        } else if (isPagerChanged) {
            int i = position - lastPosition;
            mSelectDateTime = mSelectDateTime.plusMonths(i);

            if (isDefaultSelect) {
                //日期越界
                if (mSelectDateTime.getMillis() > endDateTime.getMillis()) {
                    mSelectDateTime = endDateTime;
                } else if (mSelectDateTime.getMillis() < startDateTime.getMillis()) {
                    mSelectDateTime = startDateTime;
                }

                if (isSameMonth(mSelectDateTime)) {
                    DateTime now = DateTime.now();
                    mSelectDateTime = new DateTime(mSelectDateTime.getYear(), mSelectDateTime.getMonthOfYear(),
                            now.getDayOfMonth(), 0, 0);
                } else {
                    mSelectDateTime = new DateTime(mSelectDateTime.getYear(), mSelectDateTime.getMonthOfYear(),
                            1, 0, 0);
                }

                currView.setDateTimeAndPoint(mSelectDateTime, pointList);
                currView.setHolidayList(holidayList);
                currView.setWorkdayList(workdayList);
                currView.isShowLunar(isShowLunar);
                currView.setEventList(eventList);
                if (onMonthCalendarChangedListener != null) {
                    onMonthCalendarChangedListener.onMonthCalendarChanged(mSelectDateTime);
                }
            } else {
                if (Utils.isEqualsMonth(lastSelectDateTime, mSelectDateTime)) {
                    currView.setDateTimeAndPoint(lastSelectDateTime, pointList);
                    currView.setHolidayList(holidayList);
                    currView.setWorkdayList(workdayList);
                    currView.isShowLunar(isShowLunar);
                    currView.setEventList(eventList);
                }
            }

        }
        lastPosition = position;
    }

    public void setOnMonthCalendarChangedListener(OnMonthCalendarChangedListener onMonthCalendarChangedListener) {
        this.onMonthCalendarChangedListener = onMonthCalendarChangedListener;
    }

    public void setOnClickMonthCalendarListener(OnClickMonthCalendarListener onClickMonthCalendarListener) {
        this.onClickMonthCalendarListener = onClickMonthCalendarListener;
    }

    @Override
    protected void setDateTime(DateTime dateTime) {
        if (dateTime.getMillis() > endDateTime.getMillis() || dateTime.getMillis() < startDateTime.getMillis()) {
            Toast.makeText(getContext(), R.string.illegal_date, Toast.LENGTH_SHORT).show();
            return;
        }

        SparseArray<CalendarView> calendarViews = calendarAdapter.getCalendarViews();
        if (calendarViews.size() == 0) {
            return;
        }

        isPagerChanged = false;

        MonthView currectMonthView = getCurrectMonthView();
        DateTime initialDateTime = currectMonthView.getInitialDateTime();

        //不是当月
        if (!Utils.isEqualsMonth(initialDateTime, dateTime)) {
            int months = Utils.getIntervalMonths(initialDateTime, dateTime);
            int i = getCurrentItem() + months;
            setCurrentItem(i, Math.abs(months) < 2);
            currectMonthView = getCurrectMonthView();
        }

        try {
            currectMonthView.setDateTimeAndPoint(dateTime, pointList);
            currectMonthView.setHolidayList(holidayList);
            currectMonthView.setWorkdayList(workdayList);
            currectMonthView.isShowLunar(isShowLunar);
            currectMonthView.setEventList(eventList);
            mSelectDateTime = dateTime;
            lastSelectDateTime = dateTime;

            isPagerChanged = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (onMonthCalendarChangedListener != null) {
            onMonthCalendarChangedListener.onMonthCalendarChanged(mSelectDateTime);
        }
    }

    @Override
    public void onClickCurrentMonth(DateTime dateTime) {
        dealClickEvent(dateTime, getCurrentItem());
    }

    @Override
    public void onClickLastMonth(DateTime dateTime) {
        int currentItem = getCurrentItem() - 1;
        dealClickEvent(dateTime, currentItem);
    }

    @Override
    public void onClickNextMonth(DateTime dateTime) {
        int currentItem = getCurrentItem() + 1;
        dealClickEvent(dateTime, currentItem);
    }

    private void dealClickEvent(DateTime dateTime, int currentItem) {

        if (dateTime.getMillis() > endDateTime.getMillis() || dateTime.getMillis() < startDateTime.getMillis()) {
            Toast.makeText(getContext(), R.string.illegal_date, Toast.LENGTH_SHORT).show();
            return;
        }

        isPagerChanged = false;
        setCurrentItem(currentItem, true);
        MonthView nMonthView = getCurrectMonthView();
        nMonthView.setDateTimeAndPoint(dateTime, pointList);
        nMonthView.setWorkdayList(workdayList);
        nMonthView.setEventList(eventList);
        nMonthView.setHolidayList(holidayList);
        nMonthView.isShowLunar(isShowLunar);
        mSelectDateTime = dateTime;
        lastSelectDateTime = dateTime;

        isPagerChanged = true;

        if (onClickMonthCalendarListener != null) {
            onClickMonthCalendarListener.onClickMonthCalendar(dateTime);
        }
    }


    public MonthView getCurrectMonthView() {
        return (MonthView) calendarAdapter.getCalendarViews().get(getCurrentItem());
    }

}
