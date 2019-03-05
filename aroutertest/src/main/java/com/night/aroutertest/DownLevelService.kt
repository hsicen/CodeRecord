package com.night.aroutertest

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.service.DegradeService

/**
 * <p>作者：Night  2019/3/5 14:10
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：全局降级，服务接口实现
 *
 * onLost中处理
 */
class DownLevelService : DegradeService {

    override fun onLost(context: Context?, postcard: Postcard?) {

        Log.d("hsc", "全局降级处理, 当前路径为：${postcard?.path}")
    }

    override fun init(context: Context?) {

        Log.d("hsc", "初始化......")
    }

}