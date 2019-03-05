package com.night.aroutertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter

/**
 * <p>作者：黄思程  2019/3/5 10:13
 * <p>邮箱：codinghuang@163.com
 * <p>功能：
 * <p>描述：新建Activity用于监听Schema事件
 *                     之后把url传递给ARouter
 */
@Route(path = "/kotlin/filter")
class SchemeFilterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schame_filter)


        val uri = intent.data
        ARouter.getInstance().build(uri).navigation()
        finish()
    }
}
