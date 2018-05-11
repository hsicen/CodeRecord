package com.hsc.vince.kotlincalendar

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import java.util.*

/**
 * Author ：NightYoung  2018/5/11 11:39
 * Email    ：codinghuang@163.com
 * Func     ：
 * Desc     ：a view show events  at  a day range
 */
class TimeWeekView @JvmOverloads constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        defAttrStyle: Int = 0) :
        View(context, attributeSet, defAttrStyle) {

    /* scroll direction*/
    private enum class Direction {
        NONE, LEFT,
        RIGHT, VERTICAL
    }

    private val mContext: Context = context

    //first day of week
    private var mFirstDayOfWeek: Int = Calendar.MONDAY
    private var mVisibleDays = 7
    private var mShowWeekend = true
    private var mShowFirstDayOfWeekFirst = true

    //hour separator params
    private var mHourHeight = 50f
    private var mMaxHourHeight = 100f
    private var mMinHourHeight = 25f
    private var mHourSeparatorHeight = 5f
    private var mHourSeparatorColor = Color.parseColor("#8f8d80")

    //head text params
    private var mHeadDateWeekTextSize = 10f
    private var mHeadDateWeekTextTopColor = Color.parseColor("#8f8d80")
    private var mHeadDateWeekTextBottomColor = Color.parseColor("#8f8d80")
    private var mHeadDateDayTextSize = 10f
    private var mHeadDateDayTextColor = Color.parseColor("#8f8d80")
    private var mHeadDateBackground = Color.parseColor("#8f8d80")

    //head time
    private var mHeadTimeTextSize = 10f
    private var mHeadTimeTextColor = Color.parseColor("#8f8d80")

    //head free params
    private var mHeadFreeText = "闲"
    private var mHeadFreeTextSize = 10f
    private var mHeadFreeTextColor = Color.parseColor("#8f8d80")
    private var mHeadFreeCircleRadius = 10f
    private var mHeadFreeCircleColor = Color.parseColor("#8f8d80")

    //head all day params
    private var mAllDayText = "全天"
    private var mAllDayTextColor = Color.parseColor("#8f8d80")
    private var mAllDayTextSize = 10f
    private var mAllDayEventTextSize = 10f
    private var mAllDayEventTextColor = Color.parseColor("#8f8d80")
    private var mAllDayEventHeight = 50f

    //head row and col params
    private var mHeadRowPadding = 10f
    private var mHeadColPadding = 10f
    private var mHeadRowBackground = Color.parseColor("#8f8d80")
    private var mHeadColBackground = Color.parseColor("#8f8d80")

    //event params
    private var mEventTextSize = 10f
    private var mEventTextColor = Color.parseColor("#8f8d80")
    private var mEventPadding = 10f
    private var mEventMarginVertical = 10f
    private var mEventCornerRadius = 5f
    private var mEventOverlapGap = 5f
    private var mColumnGap = 5f

    //today params
    private var mTodayHeadTextColor = Color.parseColor("#8f8d80")
    private var mTodayBackground = Color.parseColor("#8f8d80")
    private var mDayBackground = Color.parseColor("#8f8d80")

    //past future color
    private var mShowPastFutureColor = true
    private var mFutureColor = Color.parseColor("#8f8d80")
    private var mPastColor = Color.parseColor("#8f8d80")

    //now line params
    private var mShowNowLine = true
    private var mNowLineColor = Color.parseColor("#8f8d80")
    private var mNowLineThickness = 5f

    //touch scroll params
    private var mScrollDuration = 200
    private var mHorizontalScrollSpeed = 1.0f
    private var mVerticalScrollSpeed = 1.0f
    private var mHorizontalFlingEnable = false
    private var mVerticalFlingEnable = false
    private var mHorizontalScrollEnable = true
    private var mVerticalScrollEnable = true

    //draw tools


    //temp var


    init {
        initAttrs(context, attributeSet, defAttrStyle)

        initDrawTools()
    }

    private fun initAttrs(context: Context, attributeSet: AttributeSet?, defAttrStyle: Int) {
        val array = context.obtainStyledAttributes(attributeSet,
                R.styleable.TimeWeekView, defAttrStyle, 0)

        (0..array.indexCount)
                .asSequence()
                .map { array.getIndex(it) }
                .forEach {
                    when (it) {
                    //first day of week
                        R.styleable.TimeWeekView_first_dayOfWeek ->
                            mFirstDayOfWeek = array.getInt(it, mFirstDayOfWeek)
                        R.styleable.TimeWeekView_visible_days ->
                            mVisibleDays = array.getInt(it, mVisibleDays)
                        R.styleable.TimeWeekView_show_weekend ->
                            mShowWeekend = array.getBoolean(it, mShowWeekend)
                        R.styleable.TimeWeekView_show_firstDayOfWeekFirst ->
                            mShowFirstDayOfWeekFirst = array.getBoolean(it, mShowFirstDayOfWeekFirst)

                    //hour separator params
                        R.styleable.TimeWeekView_hour_height ->
                            mHourHeight = array.getDimension(it, dp2px(context, 60f))
                        R.styleable.TimeWeekView_hour_maxHeight ->
                            mMaxHourHeight = array.getDimension(it, dp2px(context, 60f))
                        R.styleable.TimeWeekView_hour_minHeight ->
                            mMinHourHeight = array.getDimension(it, dp2px(context, 60f))
                        R.styleable.TimeWeekView_hour_separatorHeight ->
                            mHourSeparatorHeight = array.getDimension(it, dp2px(context, 1f))
                        R.styleable.TimeWeekView_hour_separatorColor ->
                            mHourSeparatorColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHourSeparator))

                    //head text params
                        R.styleable.TimeWeekView_head_dateWeekTextSize ->
                            mHeadDateWeekTextSize = array.getDimension(it, sp2px(context, 12f))
                        R.styleable.TimeWeekView_head_dateWeekTextTopColor ->
                            mHeadDateWeekTextTopColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHeadTopText))
                        R.styleable.TimeWeekView_head_dateWeekTextBottomColor ->
                            mHeadDateWeekTextBottomColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHeadBottomText))
                        R.styleable.TimeWeekView_head_dateDayTextSize ->
                            mHeadDateDayTextSize = array.getDimension(it, sp2px(context, 16f))
                        R.styleable.TimeWeekView_head_dateDayTextColor ->
                            mHeadDateDayTextColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHeadBottomText))
                        R.styleable.TimeWeekView_head_dateBackground ->
                            mHeadDateBackground = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHeadDate))

                    //head time
                        R.styleable.TimeWeekView_head_timeTextSize ->
                            mHeadTimeTextSize = array.getDimension(it, sp2px(context, 12f))
                        R.styleable.TimeWeekView_head_timeTextColor ->
                            mHeadTimeTextColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHeadTime))

                    //head free params
                        R.styleable.TimeWeekView_head_freeText ->
                            mHeadFreeText = array.getString(it)
                        R.styleable.TimeWeekView_head_freeTextSize ->
                            mHeadFreeTextSize = array.getDimension(it, sp2px(context, 10f))
                        R.styleable.TimeWeekView_head_freeTextColor ->
                            mHeadFreeTextColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorFreeText))
                        R.styleable.TimeWeekView_head_freeCircleRadius ->
                            mHeadFreeCircleRadius = array.getDimension(it, dp2px(context, 6f))
                        R.styleable.TimeWeekView_head_freeCircleColor ->
                            mHeadFreeCircleColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorFreeCircle))

                    //head all day params
                        R.styleable.TimeWeekView_head_allDayText ->
                            mAllDayText = array.getString(it)
                        R.styleable.TimeWeekView_head_allDayTextColor ->
                            mAllDayTextColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorAllDayText))
                        R.styleable.TimeWeekView_head_allDayTextSize ->
                            mAllDayTextSize = array.getDimension(it, sp2px(context, 16f))
                        R.styleable.TimeWeekView_head_allDayEventTextSize ->
                            mAllDayEventTextSize = array.getDimension(it, sp2px(context, 14f))
                        R.styleable.TimeWeekView_head_allDayEventTextColor ->
                            mAllDayEventTextColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorAllDayEventText))
                        R.styleable.TimeWeekView_head_allDayEventHeight ->
                            mAllDayEventHeight = array.getDimension(it, dp2px(context, 35f))

                    //head row and col params
                        R.styleable.TimeWeekView_head_rowPadding ->
                            mHeadRowPadding = array.getDimension(it, dp2px(context, 2f))
                        R.styleable.TimeWeekView_head_colPading ->
                            mHeadColPadding = array.getDimension(it, dp2px(context, 8f))
                        R.styleable.TimeWeekView_head_rowBackground ->
                            mHeadRowBackground = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHeadRow))
                        R.styleable.TimeWeekView_head_colBackground ->
                            mHeadColBackground = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorHeadCol))

                    //event params
                        R.styleable.TimeWeekView_event_textSize ->
                            mEventTextSize = array.getDimension(it, dp2px(context, 14f))
                        R.styleable.TimeWeekView_event_textColor ->
                            mEventTextColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorEventText))
                        R.styleable.TimeWeekView_event_padding ->
                            mEventPadding = array.getDimension(it, dp2px(context, 8f))
                        R.styleable.TimeWeekView_event_marginVertical ->
                            mEventMarginVertical = array.getDimension(it, dp2px(context, 0.2f))
                        R.styleable.TimeWeekView_event_cornerRadius ->
                            mEventCornerRadius = array.getDimension(it, dp2px(context, 8f))
                        R.styleable.TimeWeekView_event_overlappingGap ->
                            mEventOverlapGap = array.getDimension(it, dp2px(context, 1f))
                        R.styleable.TimeWeekView_column_gap ->
                            mColumnGap = array.getDimension(it, dp2px(context, 0.5f))

                    //today params
                        R.styleable.TimeWeekView_today_headTextColor ->
                            mTodayHeadTextColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorTodayHeadText))
                        R.styleable.TimeWeekView_today_background ->
                            mTodayBackground = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorTodayBackground))
                        R.styleable.TimeWeekView_day_background ->
                            mDayBackground = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorDayBackground))

                    //past future color
                        R.styleable.TimeWeekView_show_pastFutureColor ->
                            mShowPastFutureColor = array.getBoolean(it, mShowPastFutureColor)
                        R.styleable.TimeWeekView_future_color ->
                            mFutureColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorFutute))
                        R.styleable.TimeWeekView_past_color ->
                            mPastColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorPast))

                    //now line params
                        R.styleable.TimeWeekView_show_nowLine ->
                            mShowNowLine = array.getBoolean(it, mShowNowLine)
                        R.styleable.TimeWeekView_now_lineColor ->
                            mNowLineColor = array.getColor(it,
                                    ContextCompat.getColor(context, R.color.colorNowLine))
                        R.styleable.TimeWeekView_now_lineThickness ->
                            mNowLineThickness = array.getDimension(it, dp2px(context, 1f))

                    //touch scroll params
                        R.styleable.TimeWeekView_scroll_duration ->
                            mScrollDuration = array.getInt(it, mScrollDuration)
                        R.styleable.TimeWeekView_xScroll_speed ->
                            mHorizontalScrollSpeed = array.getFloat(it, mHorizontalScrollSpeed)
                        R.styleable.TimeWeekView_yScroll_speed ->
                            mVerticalScrollSpeed = array.getFloat(it, mVerticalScrollSpeed)
                        R.styleable.TimeWeekView_vertical_flingEnable ->
                            mVerticalScrollEnable = array.getBoolean(it, mVerticalScrollEnable)
                        R.styleable.TimeWeekView_horizontal_flingEnable ->
                            mHorizontalFlingEnable = array.getBoolean(it, mHorizontalFlingEnable)
                        R.styleable.TimeWeekView_horizontal_scrollEnable ->
                            mHorizontalScrollEnable = array.getBoolean(it, mHorizontalScrollEnable)
                        R.styleable.TimeWeekView_vertical_scrollEnable ->
                            mVerticalScrollEnable = array.getBoolean(it, mVerticalScrollEnable)
                    }
                }

        array.recycle()
    }

    private fun initDrawTools() {

    }
}