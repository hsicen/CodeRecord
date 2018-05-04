package com.alamkanak.weekview;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static com.alamkanak.weekview.WeekViewUtil.isSameDay;

/**
 * <p>作者：黄思程  2018/5/3 16:23
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：日程事件实体类
 */
public class WeekViewEvent {
    /*** 事件id*/
    private long mId;
    /*** 事件开始时间*/
    private Calendar mStartTime;
    /*** 事件结束时间*/
    private Calendar mEndTime;
    /*** 事件标题*/
    private String mName;
    /*** 事件位置*/
    private String mLocation;
    /*** 事件颜色*/
    private int mColor;
    /*** 事件类型*/
    private int mEventType;
    /*** 是否全天事件*/
    private boolean mAllDay;

    /*** 无参构造方法*/
    public WeekViewEvent() {
    }

    /*** Initializes the event for week view
     *** @param id        The id of the event.
     *** @param name      Name of the event.
     *** @param location  The location of the event.
     *** @param startTime The time when the event starts.
     *** @param endTime   The time when the event ends.
     *** @param allDay    Is the event an all day event */
    public WeekViewEvent(long id, String name, String location,
                         Calendar startTime, Calendar endTime, boolean allDay) {
        this.mId = id;
        this.mName = name;
        this.mLocation = location;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mAllDay = allDay;
    }

    /*** Initializes the event for week view
     ***  @param id        The id of the event
     ***  @param name      Name of the event
     ***  @param location  The location of the event
     ***  @param startTime The time when the event start.
     ***  @param endTime   The time when the event ends
     ***  @param allDay    Is the event an all day event */
    public WeekViewEvent(long id, String name, String location,
                         Calendar startTime, Calendar endTime, boolean allDay, int mEventType) {
        this.mId = id;
        this.mName = name;
        this.mLocation = location;
        this.mStartTime = startTime;
        this.mEndTime = endTime;
        this.mEventType = mEventType;
        this.mAllDay = allDay;
    }

    /*** Initializes the event for week view
     *** @param id        The id of the event
     *** @param name      Name of the event
     *** @param location  The location of the event
     *** @param startTime The time when the event starts
     *** @param endTime   The time when the event ends */
    public WeekViewEvent(long id, String name, String location, Calendar startTime, Calendar endTime) {

        this(id, name, location, startTime, endTime, false);
    }

    public Calendar getStartTime() {
        return mStartTime;
    }

    public void setStartTime(Calendar startTime) {
        this.mStartTime = startTime;
    }

    public Calendar getEndTime() {
        return mEndTime;
    }

    public void setEndTime(Calendar endTime) {
        this.mEndTime = endTime;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLocation() {
        return mLocation;
    }

    public void setLocation(String location) {
        this.mLocation = location;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int color) {
        this.mColor = color;
    }

    public boolean isAllDay() {
        return mAllDay;
    }

    public void setAllDay(boolean allDay) {
        this.mAllDay = allDay;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        this.mId = id;
    }

    public int getEventType() {
        return mEventType;
    }

    public void setEventType(int mEventType) {
        this.mEventType = mEventType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WeekViewEvent that = (WeekViewEvent) o;

        return mId == that.mId;
    }

    @Override
    public int hashCode() {

        return (int) (mId ^ (mId >>> 32));
    }

    /*** 跨天事件拆分
     *** @return 拆分跨天事件 */
    public List<WeekViewEvent> splitWeekViewEvents() {
        //This function splits the WeekViewEvent in WeekViewEvents by day
        List<WeekViewEvent> events = new ArrayList<>();
        // The first millisecond of the next day is still the same day. (no need to split events for this).
        Calendar endTime = (Calendar) this.getEndTime().clone();
        endTime.add(Calendar.MILLISECOND, -1);
        if (!isSameDay(this.getStartTime(), endTime)) {
            endTime = (Calendar) this.getStartTime().clone();
            endTime.set(Calendar.HOUR_OF_DAY, 23);
            endTime.set(Calendar.MINUTE, 59);
            WeekViewEvent event1 = new WeekViewEvent(this.getId(),
                    this.getName(), this.getLocation(), this.getStartTime(),
                    endTime, this.isAllDay(), this.mEventType);
            event1.setColor(this.getColor());
            events.add(event1);

            // Add other days.
            Calendar otherDay = (Calendar) this.getStartTime().clone();
            otherDay.add(Calendar.DATE, 1);
            while (!isSameDay(otherDay, this.getEndTime())) {
                Calendar overDay = (Calendar) otherDay.clone();
                overDay.set(Calendar.HOUR_OF_DAY, 0);
                overDay.set(Calendar.MINUTE, 0);
                Calendar endOfOverDay = (Calendar) overDay.clone();
                endOfOverDay.set(Calendar.HOUR_OF_DAY, 23);
                endOfOverDay.set(Calendar.MINUTE, 59);
                WeekViewEvent eventMore = new WeekViewEvent(this.getId(),
                        this.getName(), this.getLocation(), overDay, endOfOverDay,
                        this.isAllDay(), this.mEventType);
                eventMore.setColor(this.getColor());
                eventMore.setAllDay(true);
                events.add(eventMore);

                // Add next day.
                otherDay.add(Calendar.DATE, 1);
            }

            // Add last day.
            Calendar startTime = (Calendar) this.getEndTime().clone();
            startTime.set(Calendar.HOUR_OF_DAY, 0);
            startTime.set(Calendar.MINUTE, 0);
            WeekViewEvent event2 = new WeekViewEvent(this.getId(),
                    this.getName(), this.getLocation(), startTime,
                    this.getEndTime(), this.isAllDay(), this.mEventType);
            event2.setColor(this.getColor());
            events.add(event2);
        } else {
            events.add(this);
        }

        return events;
    }
}
