package com.night.aroutertest

import android.content.Context
import android.util.Log
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.callback.InterceptorCallback
import com.alibaba.android.arouter.facade.template.IInterceptor

/**
 * <p>作者：Night  2019/3/5 11:21
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：ARouter 拦截器
 */
@Interceptor(priority = 8, name = "测试用拦截器")
class TestInterceptor : IInterceptor {


    //在回调函数的onFound()后执行
    override fun process(postcard: Postcard?, callback: InterceptorCallback?) {
        //处理拦截事件
        if ("/kotlin/activity".equals(postcard?.path)) {

            Log.d("TestInterceptor", "事件拦截")
        }


        //处理完拦截事件, 交换控制权
        //callback?.onContinue(postcard)
        callback?.onInterrupt(RuntimeException("有异常，终端路由"))
    }

    override fun init(context: Context?) {
        //拦截器初始化   在sdk初始化时调用该方法,并且只会调用一次
        Log.d("TestInterceptor", "初始化....")
    }
}