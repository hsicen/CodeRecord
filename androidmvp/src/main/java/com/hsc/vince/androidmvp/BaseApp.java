package com.hsc.vince.androidmvp;

import android.app.Application;

import com.toucheart.library.util.ContextUtil;
import com.toucheart.library.util.LogUtils;

/**
 * <p>作者：黄思程  2018/4/10 15:45
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：全局Application
 */
public class BaseApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //初始化Logger
        ContextUtil.init(this);
        LogUtils.init(true);
    }
}
