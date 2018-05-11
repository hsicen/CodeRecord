package com.necer.ncalendar.view;

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
import com.necer.ncalendar.listener.OnClickWeekViewListener;
import com.necer.ncalendar.utils.Attrs;
import com.necer.ncalendar.utils.Utils;

import org.joda.time.DateTime;

import java.util.List;


/**
 * Created by necer on 2017/8/25.
 * QQ群:127278900
 */

public class WeekView extends CalendarView {

    private OnClickWeekViewListener mOnClickWeekViewListener;
    private List<String> lunarList;
    private Context mContext;
    private Rect mTextRect = new Rect();

    public WeekView(Context context, DateTime dateTime, OnClickWeekViewListener onClickWeekViewListener) {
        super(context);

        this.mContext = context;
        this.mInitialDateTime = dateTime;
        Utils.NCalendar weekCalendar2 = Utils.getWeekCalendar2(dateTime, Attrs.firstDayOfWeek);

        dateTimes = weekCalendar2.dateTimeList;
        lunarList = weekCalendar2.lunarList;
        mOnClickWeekViewListener = onClickWeekViewListener;
    }

    @Override
    public void setFirstDayOfWeek(int firstDayOfWeek) {
        super.setFirstDayOfWeek(firstDayOfWeek);

        Utils.NCalendar weekCalendar2 = Utils.getWeekCalendar2(mInitialDateTime, firstDayOfWeek);

        dateTimes = weekCalendar2.dateTimeList;
        lunarList = weekCalendar2.lunarList;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getWidth();
        //为了与月日历保持一致，往上压缩一下,5倍的关系
        mHeight = (int) (getHeight() - Utils.dp2px(getContext(), 15));
        mRectList.clear();

        for (int i = 0; i < 7; i++) {
            Rect rect = new Rect(i * mWidth / 7, 0, i * mWidth / 7 + mWidth / 7, mHeight);
            mRectList.add(rect);
            DateTime dateTime = dateTimes.get(i);
            Paint.FontMetricsInt fontMetrics = mSorlarPaint.getFontMetricsInt();

            int baseline;
            if (isSchedule) {
                //如果是日程，整体向上移动3dp
                baseline = (int) ((rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2 - dp2px(mContext, 2));
            } else {
                baseline = (rect.bottom + rect.top - fontMetrics.bottom - fontMetrics.top) / 2;
            }

            if (Utils.isToday(dateTime)) {
                if (isAttendance && mSelectDateTime != null && !Utils.isToday(mSelectDateTime)) {
                    mSorlarPaint.setColor(mSelectCircleColor);
                    canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                } else {
                    mSorlarPaint.setColor(mSelectCircleColor);
                    if (isSchedule) {

                        if (isShowLunar || (eventList != null && eventList.containsKey(dateTime.toLocalDate().toString()))) {
                            canvas.drawCircle(rect.centerX(), rect.centerY() + Utils.dp2px(mContext, 5),
                                    mSelectCircleRadius, mSorlarPaint);
                        } else {
                            canvas.drawCircle(rect.centerX(), rect.centerY() - Utils.dp2px(mContext, 2),
                                    mSelectCircleRadius, mSorlarPaint);
                        }
                    } else {
                        canvas.drawCircle(rect.centerX(), rect.centerY(), mSelectCircleRadius, mSorlarPaint);
                    }

                    mSorlarPaint.setColor(Color.WHITE);
                    canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                }
            } else if (mSelectDateTime != null && dateTime.equals(mSelectDateTime)) {
                //绘制选中日期的圆圈
                if (isAttendance) {
                    mSorlarPaint.setColor(mSelectCircleColor);
                    if (isSchedule) {
                        if (isShowLunar || (eventList != null && eventList.containsKey(dateTime.toLocalDate().toString()))) {
                            canvas.drawCircle(rect.centerX(), rect.centerY() + Utils.dp2px(mContext, 5),
                                    mSelectCircleRadius, mSorlarPaint);
                        } else {
                            canvas.drawCircle(rect.centerX(), rect.centerY() - Utils.dp2px(mContext, 2),
                                    mSelectCircleRadius, mSorlarPaint);
                        }
                    } else {
                        canvas.drawCircle(rect.centerX(), rect.centerY(), mSelectCircleRadius, mSorlarPaint);
                    }

                    mSorlarPaint.setColor(Color.WHITE);
                    canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                } else {
                    mSorlarPaint.setColor(mSelectCircleColor);
                    canvas.drawCircle(rect.centerX(), rect.centerY(), mSelectCircleRadius, mSorlarPaint);
                    mSorlarPaint.setColor(mHollowCircleColor);
                    canvas.drawCircle(rect.centerX(), rect.centerY(), mSelectCircleRadius - mHollowCircleStroke, mSorlarPaint);
                    mSorlarPaint.setColor(mSolarTextColor);
                    canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
                }
            } else {
                mSorlarPaint.setColor(mSolarTextColor);
                canvas.drawText(dateTime.getDayOfMonth() + "", rect.centerX(), baseline, mSorlarPaint);
            }

            //绘制农历
            drawLunar(canvas, rect, baseline, i, dateTime);
            //绘制节假日
            drawHolidays(canvas, rect, dateTime, baseline);
            //绘制圆点
            drawPoint(canvas, rect, dateTime);
            //绘制事件项数
            drawEventNum(canvas, rect, dateTime, baseline);
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

                    if (isShowLunar) {
                        //显示了农历

                        //绘制圆角方形
                        mLunarPaint.setColor(mContext.getResources().getColor(R.color.lightGreen));
                        RectF rectF = new RectF(
                                rect.centerX() - dp2px(mContext, 17),
                                baseline + getHeight() / 4 + dp2px(mContext, 0.5f),
                                rect.centerX() + dp2px(mContext, 17),
                                baseline + getHeight() / 4 + dp2px(mContext, 14.5f));
                        canvas.drawRoundRect(rectF,
                                dp2px(mContext, 7), dp2px(mContext, 7), mLunarPaint);

                        //绘制字
                        mLunarPaint.setColor(mSelectCircleColor);
                        canvas.drawText(eventNum, rect.centerX(),
                                baseline + getHeight() / 4 + dp2px(mContext, 11.5f),
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
                                baseline + getHeight() / 4 + dp2px(mContext, 1),
                                mLunarPaint);
                    }
                } else {
                    //当天选中时的状态
                    if (isShowLunar) {
                        //显示了农历
                        mLunarPaint.setColor(mSelectCircleColor);
                        canvas.drawText(eventNum, rect.centerX(),
                                baseline + getHeight() / 4 + dp2px(mContext, 11),
                                mLunarPaint);
                    } else {
                        //没显示农历
                        mLunarPaint.setColor(Color.WHITE);
                        canvas.drawText(eventNum, rect.centerX(),
                                baseline + getHeight() / 4 - dp2px(mContext, 4),
                                mLunarPaint);
                    }
                }
            } else if (mSelectDateTime != null && dateTime.equals(mSelectDateTime)) {
                //选中时的状态
                if (isShowLunar) {
                    //显示了农历
                    mLunarPaint.setColor(mSelectCircleColor);
                    canvas.drawText(eventNum, rect.centerX(),
                            baseline + getHeight() / 4 + dp2px(mContext, 12),
                            mLunarPaint);
                } else {
                    //没显示农历
                    mLunarPaint.setColor(Color.WHITE);
                    canvas.drawText(eventNum, rect.centerX(),
                            baseline + getHeight() / 4 - dp2px(mContext, 4),
                            mLunarPaint);
                }
            } else {
                //没选中时的状态

                if (isShowLunar) {
                    //显示了农历

                    //绘制圆角方形
                    mLunarPaint.setColor(mContext.getResources().getColor(R.color.lightGreen));
                    RectF rectF = new RectF(
                            rect.centerX() - dp2px(mContext, 17),
                            baseline + getHeight() / 4 + dp2px(mContext, 0.5f),
                            rect.centerX() + dp2px(mContext, 17),
                            baseline + getHeight() / 4 + dp2px(mContext, 14.5f));
                    canvas.drawRoundRect(rectF,
                            dp2px(mContext, 7), dp2px(mContext, 7), mLunarPaint);

                    //绘制字
                    mLunarPaint.setColor(mSelectCircleColor);
                    canvas.drawText(eventNum, rect.centerX(),
                            baseline + getHeight() / 4 + dp2px(mContext, 11.5f),
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
                            baseline + getHeight() / 4 + dp2px(mContext, 1),
                            mLunarPaint);
                }
            }
        }
    }

    /*** 绘制农历*/
    private void drawLunar(Canvas canvas, Rect rect, int baseline, int i, DateTime dateTime) {
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

            String lunar = lunarList.get(i);

            if (isSchedule) {
                canvas.drawText(lunar, rect.centerX(), baseline + getHeight() / 4 - dp2px(mContext, 7.8f),
                        mLunarPaint);
            } else {
                canvas.drawText(lunar, rect.centerX(), baseline + getHeight() / 4, mLunarPaint);
            }
        }
    }

    /*** 绘制休息日和加班日*/
    private void drawHolidays(Canvas canvas, Rect rect, DateTime dateTime, int baseline) {
        if (isShowHoliday) {
            float xRaw;
            float yRow;
            float textRaw;

            xRaw = dp2px(mContext, 4);  //X轴的偏移量
            if (isShowLunar) {
                yRow = dp2px(mContext, 17);
                textRaw = dp2px(mContext, 16);
            } else {
                yRow = dp2px(mContext, 14);
                textRaw = dp2px(mContext, 13);
            }

            mLunarPaint.setTextSize(Utils.sp2px(mContext, 8));
            if (holidayList != null && holidayList.contains(dateTime.toLocalDate().toString())) {
                String mText = "休";
                mLunarPaint.getTextBounds(mText, 0, 1, mTextRect);

                //画圆  白色外圆
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getHeight() / 4 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 4) + mTextRect.height() / 2, mLunarPaint);

                //画圆  底色内圆
                mLunarPaint.setColor(mPointColor);
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getHeight() / 4 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 3) + mTextRect.height() / 2, mLunarPaint);

                //画字  圆内的字
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawText(mText,
                        rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getHeight() / 4 + textRaw,
                        mLunarPaint);
            } else if (workdayList != null && workdayList.contains(dateTime.toLocalDate().toString())) {
                String mText = "班";
                mLunarPaint.getTextBounds(mText, 0, 1, mTextRect);

                //画圆  白色外圆
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getHeight() / 4 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 4) + mTextRect.height() / 2, mLunarPaint);

                //画圆  底色内圆
                mLunarPaint.setColor(mContext.getResources().getColor(R.color.workDay));
                canvas.drawCircle(rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getHeight() / 4 - mTextRect.height() / 2 + yRow,
                        Utils.dp2px(mContext, 3) + mTextRect.height() / 2, mLunarPaint);

                //画字  圆内的字
                mLunarPaint.setColor(Color.WHITE);
                canvas.drawText(mText,
                        rect.centerX() + rect.width() / 4 + xRaw,
                        baseline - getHeight() / 4 + textRaw,
                        mLunarPaint);
            }
        }
    }

    /*** 绘制异常圆点*/
    public void drawPoint(Canvas canvas, Rect rect, DateTime dateTime) {
        if (pointList != null && pointList.contains(dateTime.toLocalDate().toString())) {
            mLunarPaint.setColor(mPointColor);
            canvas.drawCircle(rect.centerX(), rect.bottom - mPointSize, mPointSize, mLunarPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        return mGestureDetector.onTouchEvent(event);
    }

    private GestureDetector mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
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
                    mOnClickWeekViewListener.onClickCurrentWeek(selectDateTime);
                    break;
                }
            }
            return true;
        }
    });

    public boolean contains(DateTime dateTime) {

        return dateTimes.contains(dateTime);
    }

    private static float dp2px(Context context, float dpVal) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }
}
