package com.night.viewpager2demo

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var changeReceiver: NetworkChangeReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //代码动态注册
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
        changeReceiver = NetworkChangeReceiver()
        registerReceiver(changeReceiver, intentFilter)

        //发送自定义广播
        val intent = Intent("com.night.myreceiver")
        sendBroadcast(intent)
    }

    override fun onDestroy() {
        super.onDestroy()

        //取消注册
        unregisterReceiver(changeReceiver)
    }


    class NetworkChangeReceiver : BroadcastReceiver() {

        override fun onReceive(context: Context, intent: Intent) {

            val cm = context.getSystemService(
                    Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo

            if (info != null && info.isConnected) {
                "网络已连接".toast(context)
            } else {
                "网络已断开".toast(context)
            }

        }
    }
}
