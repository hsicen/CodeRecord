package com.necer.ncalendar.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.MotionEvent;

import com.necer.ncalendar.R;
import com.necer.ncalendar.listener.OnClickMonthViewListener;
import com.necer.ncalendar.utils.Attrs;
import com.necer.ncalendar.utils.Utils;

import org.joda.time.DateTime;

import java.util.List;

/*** 月日历*/
public class MonthView extends CalendarView {

    private List<String> lunarList;
    private int mRowNum;
    private OnClickMonthViewListener mOnClickMonthViewListener;
    private Context mContext;
    private Rect mTextRect = new Rect();
    private Rect mEventREct = new Rect();

    public MonthView(Context context, DateTime dateTime, OnClickMonthViewListener onClickMonthViewListener) {
        super(context);
        this.mInitialDateTime = dateTime;
        this.mContext = context;

        //0周日，1周一
        Utils.NCalendar nCalendar2 = Utils.getMonthCalendar2(dateTime, Attrs.firstDayOfWeek);
        mOnClickMonthViewListener = onClickMonthViewListener;

        lunarList = nCalendar2.lunarList;
        dateTimes = nCalendar2.dateTimeList;

        mRowNum = dateTimes.size() / 7;
    }

    @Override
    public void setFirstDayOfWeek(int firstDayOfWeek) {
        super.setFirstDayOfWeek(firstDayOfWeek);

        Utils.NCalendar nCalendar2 = Utils.getMonthCalendar2(mInitialDateTime, firstDayOfWeek);
        lunarList = nCalendar2.lunarList;
        dateTimes = nCalendar2.dateTimeList;

        mRowNum = dateTimes.size() / 7;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        //绘制高度
        mHeight = getDrawHeight();
        mRectList.clear();
        for (int i = 0; i < mRowNum; i++) {
            for (int j = 0; j < 7; j++) {
                Rect rect = new Rect(j * mWidth / 7, i * mHeight / mRowNum, j * mWidth / 7 + mWidth / 7,
                        i * mHeight / mRowNum + mHeight / mRowNum);
                mRectList.add(rect);
                DateTime dateTime = dateTimes.get(i * 7 + j);
                Paint.FontMetricsInt fontMetrics = mSorlarPaint.getFontMetricsInt();

                int baseline;//让6行的第一行和5行的第一行在同一直线上，处理选中第一行的滑动
                if (mRowNum == 5) {
                    baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
                } else {
                    baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2 + (mHeight / 5 - mHeight / 6) / 2;
                }

                //当月和上下月的颜色不同
                if (Utils.isEqualsMonth(dateTime, mInitialDateTime)) {
                    //当天和选中的日期不绘制农历
                    if (Utils.isToday(dateTime)) {
                        //当天
                        if (isAttendance && mSelectDateTime != null && !Utils.isToday(mSelectDateTime)) {
                            mSorlarPaint.setColor(mSelectCircleColor);
                            canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                        } else {
                            mSorlarPaint.setColor(mSelectCircleColor);
                            int centerY = mRowNum == 5 ? rect.centerY() : (rect.centerY() + (mHeight / 5 - mHeight / 6) / 2);
                            if (isSchedule) {
                                if (isShowLunar || (eventList != null && eventList.containsKey(dateTime.toLocalDate().toString()))) {
                                    canvas.drawCircle(rect.centerX(), centerY + Utils.dp2px(mContext, 7),
                                            mSelectCircleRadius, mSorlarPaint);
                                } else {
                                    canvas.drawCircle(rect.centerX(), centerY, mSelectCircleRadius, mSorlarPaint);
                                }
                            } else {
                                canvas.drawCircle(rect.centerX(), centerY, mSelectCircleRadius, mSorlarPaint);
                            }

                            mSorlarPaint.setColor(Color.WHITE);
                            canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                        }
                    } else if (mSelectDateTime != null && dateTime.equals(mSelectDateTime)) {
                        //选中日期 圆圈的绘制
                        if (isAttendance) {
                            mSorlarPaint.setColor(mSelectCircleColor);
                            int centerY = mRowNum == 5 ? rect.centerY() : (rect.centerY() + (mHeight / 5 - mHeight / 6) / 2);
                            if (isSchedule) {
                                if (isShowLunar || (eventList != null && eventList.containsKey(dateTime.toLocalDate().toString()))) {
                                    canvas.drawCircle(rect.centerX(), centerY + Utils.dp2px(mContext, 7),
                                            mSelectCircleRadius, mSorlarPaint);
                                } else {
                                    canvas.drawCircle(rect.centerX(), centerY, mSelectCircleRadius, mSorlarPaint);
                                }
                            } else {
                                canvas.drawCircle(rect.centerX(), centerY, mSelectCircleRadius, mSorlarPaint);
                            }

                            mSorlarPaint.setColor(Color.WHITE);
                            canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                        } else {
                            mSorlarPaint.setColor(mSelectCircleColor);
                            int centerY = mRowNum == 5 ? rect.centerY() : (rect.centerY() + (mHeight / 5 - mHeight / 6) / 2);
                            canvas.drawCircle(rect.centerX(), centerY, mSelectCircleRadius, mSorlarPaint);
                            mSorlarPaint.setColor(mHollowCircleColor);
                            canvas.drawCircle(rect.centerX(), centerY, mSelectCircleRadius - mHollowCircleStroke, mSorlarPaint);

                            mSorlarPaint.setColor(mSolarTextColor);
                            canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                        }

                    } else {
                        //未选中日期
                        mSorlarPaint.setColor(mSolarTextColor);
                        canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                    }

                    drawLunar(canvas, rect, baseline, mLunarTextColor, i, j, dateTime);
                    //绘制节假日
                    drawHolidays(canvas, rect, dateTime, baseline);
                    //绘制圆点
                    drawPoint(canvas, rect, dateTime);
                    //绘制事件项数
                    drawEventNum(canvas, rect, dateTime, baseline);
                } else {
                    mSorlarPaint.setColor(mHintColor);
                    canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                    drawLunar(canvas, rect, baseline, mHintColor, i, j, dateTime);
                    //绘制节假日
                    drawHolidays(canvas, rect, dateTime, baseline);
                    //绘制圆点
                    drawPoint(canvas, rect, dateTime);
                    //绘制事件项数
                    drawEventNum(canvas, rect, dateTime, baseline);
                }
            }
        }
    }

    /*** 绘制事件标识*/
    private void drawEventNum(Canvas canvas, Rect rect, DateTime dateTime, int baseline) {
        if (eventList != null && eventList.containsKey(dateTime.toLocalDate().toString())) {
            String eventNum = eventList.get(dateTime.toLocalDate().toString());
            mLunarPaint.setTextSize(Utils.sp2px(mContext, 10));
            if (Utils.isToday(dateTime)) {
                if (mSelectDateTime != null && !Utils.isToday(mSelectDateTime)) {
                    //当天没选中时的状态
                    //没选中时的状态
                    mLunarPaint.getTextBounds(eventNum, 0, eventNum.length(), mEventREct);

                    if (isShowLunar) {
                        //显示了农历

                        //绘制圆角方形
                        mLunarPaint.setColor(mContext.getResources().getColor(R.color.lightGreen));
                        RectF rectF = new RectF(
                                rect.centerX() - dp2px(mContext, 17),
                                baseline + getMonthHeight() / 20 + dp2px(mContext, 4),
                                rect.centerX() + dp2px(mContext, 17),
                                baseline + getMonthHeight() / 20 + dp2px(mContext, 18));
                        canvas.drawRoundRect(rectF,
                                dp2px(mContext, 7), dp2px(mContext, 7), mLunarPaint);

                        //绘制字
                        mLunarPaint.setColor(mSelectCircleColor);
                        canvas.drawText(eventNum, rect.centerX(),
                                baseline + getMonthHeight() / 20 + dp2px(mContext, 15),
                                mLunarPaint);
                    } else {
                        //没显示农历

                        //绘制圆角方形
                        mLunarPaint.setColor(mContext.getResources().getColor(R.color.lightGreen));
                        RectF rectF = new RectF(
                                rect.centerX() - dp2px(mContext, 17),
                                baseline + dp2px(mContext, 8),
                                rect.centerX() + dp2px(mContext, 17),
                                baseline + dp2px(mContext, 22));
                        canvas.drawRoundRect(rectF,
                                dp2px(mContext, 7), dp2px(mContext, 7), mLunarPaint);

                        //绘制字
                        mLunarPaint.setColor(mSelectCircleColor);
                        canvas.drawText(eventNum, rect.centerX(),
                                baseline + getMonthHeight() / 20 + dp2px(mContext, 4),
                                mLunarPaint);
                    }
                } else {
                    //当天选中时的状态
                    if (isShowLunar) {
                        //显示了农历
                        mLunarPaint.setColor(mSelectCircleColor);
                        canvas.drawText(eventNum, rect.centerX(),
                                baseline + getMonthHeight() / 20 + dp2px(mContext, 15),
                                mLunarPaint);
                    } else {
                        //没显示农历
                        mLunarPaint.setColor(Color.WHITE);
                        canvas.drawText(eventNum, rect.centerX(),
                                baseline + getMonthHeight() / 20 - dp2px(mContext, 0),
                                mLunarPaint);
                    }
                }
            } else if (mSelectDateTime != null && dateTime.equals(mSelectDateTime)) {
                //选中时的状态
                if (isShowLunar) {
                    //显示了农历
                    mLunarPaint.setColor(mSelectCircleColor);
                    canvas.drawText(eventNum, rect.centerX(),
                            baseline + getMonthHeight() / 20 + dp2px(mContext, 15),
                            mLunarPaint);
                } else {
                    //没显示农历
                    mLunarPaint.setColor(Color.WHITE);
                    canvas.drawText(eventNum, rect.centerX(),
                            baseline + getMonthHeight() / 20 - dp2px(mContext, 0),
                            mLunarPaint);
                }
            } else {
                //没选中时的状态
                mLunarPaint.getTextBounds(eventNum, 0, eventNum.length(), mEventREct);

                if (isShowLunar) {
                    //显示了农历

                    //绘制圆角方形
                    mLunarPaint.setColor(mContext.getResources().getColor(R.color.lightGreen));
                    RectF rectF = new RectF(
                            rect.centerX() - dp2px(mContext, 17),
                            baseline + getMonthHeight() / 20 + dp2px(mContext, 4),
                            rect.centerX() + dp2px(mContext, 17),
                            baseline + getMonthHeight() / 20 + dp2px(mContext, 18));
                    canvas.drawRoundRect(rectF,
                            dp2px(mContext, 7), dp2px(mContext, 7), mLunarPaint);

                    //绘制字
                    mLunarPaint.setColor(mSelectCircleColor);
                    canvas.drawText(eventNum, rect.centerX(),
                            baseline + getMonthHeight() / 20 + dp2px(mContext, 15),
                            mLunarPaint);
                } else {
                    //没显示农历

                    //绘制圆角方形
                    mLunarPaint.setColor(mContext.getResources().getColor(R.color.lightGreen));
                    RectF rectF = new RectF(
                            rect.centerX() - dp2px(mContext, 17),
                            baseline + dp2px(mContext, 8),
                            rect.centerX() + dp2px(mContext, 17),
                            baseline + dp2px(mContext, 22));
                    canvas.drawRoundRect(rectF,
                            dp2px(mContext, 7), dp2px(mContext, 7), mLunarPaint);

                    //绘制字
                    mLunarPaint.setColor(mSelectCircleColor);
                    canvas.drawText(eventNum, rect.centerX(),
                            baseline + getMonthHeight() / 20 + dp2px(mContext, 4f),
                            mLunarPaint);
                }
            }
        }
    }

    //月日历高度
    public int getMonthHeight() {

        return Attrs.monthCalendarHeight;
    }

    // 月日历的绘制高度 为了月日历6行时，绘制农历不至于太靠下，绘制区域网上压缩一下
    public int getDrawHeight() {

        if (isSchedule) {
            return (int) (getMonthHeight() - Utils.dp2px(getContext(), 20));
        } else {
            return (int) (getMonthHeight() - Utils.dp2px(getContext(), 10));
        }
    }

    //绘制农历
    private void drawLunar(Canvas canvas, Rect rect, int baseline, int color, int i, int j, DateTime dateTime) {
        if (isShowLunar) {
            mLunarPaint.setTextSize(Utils.sp2px(mContext, 9));
            if (Utils.isToday(dateTime)) {
                if (mSelectDateTime != null && !Utils.isToday(mSelectDateTime)) {
                    mLunarPaint.setColor(mSelectCircleColor);
                } else {
                    mLunarPaint.setColor(Color.WHITE);
                }
            } else if (mSelectDateTime != null && dateTime.equals(mSelectDateTime)) {
                mLunarPaint.setColor(Color.WHITE);
            } else {
                mLunarPaint.setColor(mLunarTextColor);
            }

            String lunar = lunarList.get(i * 7 + j);

            if (isSchedule) {
                canvas.drawText(lunar, rect.centerX(), baseline + getMonthHeight() / 20 - dp2px(mContext, 4),
                        mLunarPaint);
            } else {
                canvas.drawText(lunar, rect.centerX(), baseline + getMonthHeight() / 20, mLunarPaint);
            }
        }
    }

    //绘制加班和调休
    private void drawHolidays(Canvas canvas, Rect rect, DateTime dateTime, int baseline) {
        if (isShowHoliday) {
            float xRaw;
            float yRow;
            float textRaw;

            xRaw = dp2px(mContext, 4);  //X轴的偏移量
            if (isShowLunar) {
                yRow = dp2px(mContext, 13);
                textRaw = dp2px(mContext, 12);
            } else {
                yRow = dp2px(mContext, 10);
                textRaw = dp2px(mContext, 9);
            }

            mLunarPaint.setTextSize(Utils.sp2px(mContext, 8));
            if (holidayList != null && holidayList.contains(dateTime.toLocalDate().toString())) {
                String mText = "休";
                mLunarPaint.getTextBounds(mText, 0, 1, mTextRect);

                //画圆  白色外圆
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getMonthHeight() / 20 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 4) + mTextRect.height() / 2, mLunarPaint);

                //画圆  底色内圆
                mLunarPaint.setColor(mPointColor);
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getMonthHeight() / 20 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 3) + mTextRect.height() / 2, mLunarPaint);

                //画字  圆内的字
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawText(mText,
                        rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getMonthHeight() / 20 + textRaw,
                        mLunarPaint);

            } else if (workdayList != null && workdayList.contains(dateTime.toLocalDate().toString())) {
                String mText = "班";
                mLunarPaint.getTextBounds(mText, 0, 1, mTextRect);

                //画圆  白色外圆
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getMonthHeight() / 20 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 4) + mTextRect.height() / 2, mLunarPaint);

                //画圆  底色内圆
                mLunarPaint.setColor(mContext.getResources().getColor(R.color.workDay));
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getMonthHeight() / 20 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 3) + mTextRect.height() / 2, mLunarPaint);

                //画字  圆内的字
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawText(mText,
                        rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getMonthHeight() / 20 + textRaw,
                        mLunarPaint);
            }
        }
    }

    //绘制圆点
    public void drawPoint(Canvas canvas, Rect rect, DateTime dateTime) {
        if (pointList != null && pointList.contains(dateTime.toLocalDate().toString())) {
            mLunarPaint.setColor(mPointColor);
            if (mRowNum == 6) {
                canvas.drawCircle(rect.centerX(), rect.bottom - mPointSize + Utils.dp2px(mContext, 2),
                        mPointSize, mLunarPaint);
            } else {
                canvas.drawCircle(rect.centerX(), rect.bottom - mPointSize - dp2px(mContext, 7),
                        mPointSize, mLunarPaint);
            }
        }
    }

    private GestureDetector mGestureDetector = new GestureDetector(getContext(),
            new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    return true;
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    for (int i = 0; i < mRectList.size(); i++) {
                        Rect rect = mRectList.get(i);
                        if (rect.contains((int) e.getX(), (int) e.getY())) {
                            DateTime selectDateTime = dateTimes.get(i);
                            if (Utils.isLastMonth(selectDateTime, mInitialDateTime)) {
                                mOnClickMonthViewListener.onClickLastMonth(selectDateTime);
                            } else if (Utils.isNextMonth(selectDateTime, mInitialDateTime)) {
                                mOnClickMonthViewListener.onClickNextMonth(selectDateTime);
                            } else {
                                mOnClickMonthViewListener.onClickCurrentMonth(selectDateTime);
                            }
                            break;
                        }
                    }
                    return true;
                }
            });

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }

    public int getRowNum() {

        return mRowNum;
    }

    public int getSelectRowIndex() {
        if (mSelectDateTime == null) {
            return 0;
        }
        int indexOf = dateTimes.indexOf(mSelectDateTime);
        return indexOf / 7;
    }

    private static float dp2px(Context context, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

}
