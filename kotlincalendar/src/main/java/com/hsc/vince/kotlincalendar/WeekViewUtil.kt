package com.hsc.vince.kotlincalendar

import android.content.Context
import org.joda.time.DateTime
import org.joda.time.Months
import org.joda.time.Weeks

/**
 * Author ：NightYoung  2018/5/11 14:54
 * Email    ：codinghuang@163.com
 * Func     ：
 * Desc     ：tools for weekView
 */

/**
 * @param context context
 * @param dpValue dpValue
 * @return dp value to sp value
 */
fun dp2px(context: Context, dpValue: Float): Float {
    val scale = context.resources.displayMetrics.density
    return (dpValue * scale + 0.5f)
}

/**
 * @param context context
 * @param spValue sp value
 * @return sp value to px value
 */
fun sp2px(context: Context, spValue: Float): Float {
    val fontScale = context.resources.displayMetrics.scaledDensity
    return (spValue * fontScale + 0.5f)
}

/**
 * @param context context
 * @return the width of screen
 */
fun getDisplayWidth(context: Context): Int {
    return context.resources.displayMetrics.widthPixels
}

/**
 * @param context context
 * @return the height of screen
 */
fun getDisplayHeight(context: Context): Int {
    return context.resources.displayMetrics.heightPixels
}

/**
 * @param dateTime  dateTime
 * @return boolean isToday
 */
fun isToday(dateTime: DateTime): Boolean {
    return DateTime().withTimeAtStartOfDay() == dateTime
}

/**
 * @param dateTime  dateTime
 * @return boolean/ is the month of current date
 */
fun isToMonth(dateTime: DateTime): Boolean {
    val current = DateTime.now()

    return current.year == dateTime.year
            && current.monthOfYear == dateTime.monthOfYear
}

/**
 * @param dateTime    dateTime
 * @param dateTime2 dateTime2
 * @return boolean/ the two date is same day
 */
fun isSameDay(dateTime: DateTime, dateTime2: DateTime): Boolean {

    return (dateTime2.year == dateTime.year
            && dateTime2.monthOfYear == dateTime.monthOfYear
            && dateTime2.dayOfMonth == dateTime.dayOfMonth)
}

/**
 * @param dateTime dateTime
 * @return boolean/ whether the date is after tomonth
 */
fun isNextDateWithMonth(dateTime: DateTime): Boolean {
    val isNext: Boolean
    val current = DateTime.now()

    isNext = dateTime.year > current.year
            || dateTime.year == current.year
            && dateTime.monthOfYear > current.monthOfYear

    return isNext
}

/**
 * @param dateTime dateTime
 * @return boolean/ whether the date is after today
 */
fun isNextDateWithDay(dateTime: DateTime): Boolean {
    val isNext: Boolean
    val current = DateTime.now()

    isNext = when {
        dateTime.year > current.year -> true
        dateTime.year == current.year -> when {
            dateTime.monthOfYear > current.monthOfYear -> true
            dateTime.monthOfYear == current.monthOfYear ->
                dateTime.dayOfMonth > current.dayOfMonth
            else -> false
        }
        else -> false
    }

    return isNext
}

/**
 * @param dateTime1 dateTime1
 * @param dateTime2 dateTime2
 * @return boolean/ is equal month
 */
fun isEqualsMonth(dateTime1: DateTime, dateTime2: DateTime): Boolean {
    return dateTime1.year == dateTime2.year
            && dateTime1.monthOfYear == dateTime2.monthOfYear
}

/**
 * @param dateTime1 dateTime1
 * @param dateTime2 dateTime2
 * @return boolean/ whether the first param is the send param's last month
 */
fun isLastMonth(dateTime1: DateTime, dateTime2: DateTime): Boolean {
    val dateTime = dateTime2.plusMonths(-1)
    return dateTime1.monthOfYear == dateTime.monthOfYear
}

/**
 * @param dateTime1 dateTime1
 * @param dateTime2 dateTime2
 * @return boolean/ whether the first param is the send param's next month
 */
fun isNextMonth(dateTime1: DateTime, dateTime2: DateTime): Boolean {
    val dateTime = dateTime2.plusMonths(1)
    return dateTime1.monthOfYear == dateTime.monthOfYear
}

/**
 *@param dateTime1 dateTime1
 * @param dateTime2 dateTime2
 * @return int / months between the two date
 */
fun getIntervalMonths(dateTime1: DateTime, dateTime2: DateTime): Int {
    return Months.monthsBetween(
            dateTime1.withDayOfMonth(1).withTimeAtStartOfDay(),
            dateTime2.withDayOfMonth(1).withTimeAtStartOfDay())
            .months
}

/**
 * @param dateTime1 dateTime1
 * @param dateTime2 dateTime2
 * @param type      week
 * @return weeks between the two date
 */
fun getIntervalWeek(dateTime1: DateTime, dateTime2: DateTime, type: Int): Int {
    var dateOne = dateTime1
    var dateTwo = dateTime2

    if (type == 0) {
        dateOne = getFirstDayBeginSun(dateOne)
        dateTwo = getFirstDayBeginSun(dateTwo)
    } else {
        dateOne = getFirstDayBeginMon(dateOne)
        dateTwo = getFirstDayBeginMon(dateTwo)
    }

    return Weeks.weeksBetween(dateOne, dateTwo).weeks

}

/**
 *@param dateTime dateTime
 * @return dateTime
 */
fun getFirstDayBeginSun(dateTime: DateTime): DateTime {
    return if (dateTime.dayOfWeek().get() == 7) {
        dateTime
    } else {
        dateTime.minusWeeks(1).withDayOfWeek(7)
    }
}

/**
 * @param dateTime dateTime
 * @return dateTime
 */
fun getFirstDayBeginMon(dateTime: DateTime): DateTime {
    return dateTime.dayOfWeek().withMinimumValue()
}


/**
 * @param year year
 * @param month month
 * @return the start week with first day of month
 */
fun getFirstDayOfWeekOfMonth(year: Int, month: Int): Int {
    val dayOfWeek = DateTime(year, month, 1, 0, 0, 0).dayOfWeek
    return if (dayOfWeek == 7) {
        0
    } else
        dayOfWeek
}
