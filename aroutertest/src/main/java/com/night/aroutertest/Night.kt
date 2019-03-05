package com.night.aroutertest

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter

/**
 * <p>作者：Night  2019/3/4 17:11
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：CodeRecord
 */
class Night : Application() {

    override fun onCreate() {
        super.onCreate()


        if (BuildConfig.DEBUG) {

            ARouter.openLog()
            ARouter.openDebug()
        }

        ARouter.init(this)
    }

}