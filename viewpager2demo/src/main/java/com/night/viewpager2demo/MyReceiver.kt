package com.night.viewpager2demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

/**
 * <p>作者：Night  2019/3/2 17:38
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：CodeRecord
 */
class MyReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        abortBroadcast()
    }
}