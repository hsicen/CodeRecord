package com.night.aroutertest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.callback.NavigationCallback
import com.alibaba.android.arouter.launcher.ARouter


/**
 * <p>作者：黄思程  2019/3/5 14:04
 * <p>邮箱：codinghuang@163.com
 * <p>功能：
 * <p>描述：降级策略的实现方式
 *
 * 单独降级 - 回调方式
 * 全局降级 - 服务接口方式
 */
@Route(path = "/kotlin/down")
class DownLevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_down_level)

        //单独降级
        ARouter.getInstance()
                .build("/kotlin/single")
                .navigation(this, object : NavigationCallback {
                    override fun onLost(postcard: Postcard?) {
                        //跳转失败， 在这里进行相关处理
                    }

                    override fun onFound(postcard: Postcard?) {
                    }

                    override fun onInterrupt(postcard: Postcard?) {
                    }

                    override fun onArrival(postcard: Postcard?) {
                    }
                })


        //全局降级，服务接口方式
        ARouter.getInstance()
                .build("/kotlin/global")
                .navigation()
    }
}
