package com.night.viewpager2demo

import android.content.Context
import android.widget.Toast

/**
 * <p>作者：Night  2019/2/25 21:10
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：CodeRecord
 */

fun String.toast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}