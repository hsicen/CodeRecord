package com.vince.news;

import android.app.Application;

import com.vince.news.util.ContextUtil;
import com.vince.news.util.LogUtil;

/**
 * <p>作者：黄思程  2017/11/20 11:02
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：全局初始化
 * <p>描述：BaseApp
 */
public class BaseApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ContextUtil.init(this);
        LogUtil.init(true);
    }
}
