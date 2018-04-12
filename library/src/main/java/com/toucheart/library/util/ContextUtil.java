package com.toucheart.library.util;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * <p>作者：黄思程  2017/11/8 14:52
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：获取全局Context
 * <p>
 * 需要在Application中初始化
 */
public final class ContextUtil {
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    private ContextUtil() {

        throw new UnsupportedOperationException("you can't initial this...");
    }

    /**
     * @param context 初始化工具类
     */
    public static void init(Context context) {

        ContextUtil.context = context.getApplicationContext();
    }

    /**
     * @return 获取ApplicationContext
     */
    public static Context withContext() {
        if (context != null) {
            return context;
        }

        throw new NullPointerException("u should init first");
    }
}
