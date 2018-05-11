package com.hsc.vince.kotlincalendar

import android.content.Context
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

    private val mContext: Context? = null

    var mFirstDayOfWeek: Int = Calendar.MONDAY
        set(value) {
            field = value
            invalidate()
        }




}