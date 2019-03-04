package com.night.firstcode

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * <p>作者：黄思程  2019/2/23 14:31
 * <p>邮箱：codinghuang@163.com
 * <p>功能：
 * <p>描述：第一行代码
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        savedInstanceState?.let {
            println("恢复数据")
        }


        supportFragmentManager.beginTransaction()
                .replace(R.id.tv_replace, Fragment(), "replace")
                .commitAllowingStateLoss()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.let {

            it.putString("test", "Hello world!")
            it.putInt("num", 1)
        }
    }
}
