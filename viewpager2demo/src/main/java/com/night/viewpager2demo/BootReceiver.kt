package com.night.viewpager2demo

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

@SuppressLint("UnsafeProtectedBroadcastReceiver")
class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        "开机完成".toast(context)
    }
}
