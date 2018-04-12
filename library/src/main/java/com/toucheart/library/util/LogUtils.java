package com.toucheart.library.util;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

/**
 * 作者：vince  2017/9/28 15:16
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：Logger的二次封装
 */
public class LogUtils {
    private static final String TAG = "<<<Vince";

    private LogUtils() {
        throw new UnsupportedOperationException("You can't initialize this object");
    }

    public static void init(final boolean openLog) {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)
                .tag(TAG)
                .build();

        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return openLog;
            }
        });
    }

    public static void d(String msg, Object... args) {
        Logger.d(msg, args);
    }

    public static void d(String tag, String msg, Object... args) {
        Logger.t(tag).d(msg, args);
    }

    public static void d(Object args) {
        Logger.d(args);
    }

    public static void d(String tag, Object args) {
        Logger.t(tag).d(args);
    }

    public static void e(String msg, Object... args) {
        Logger.e(msg, args);
    }

    public static void e(String tag, String msg, Object... args) {
        Logger.t(tag).e(msg, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void e(String tag, Throwable throwable, String message, Object... args) {
        Logger.t(tag).e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void i(String tag, String message, Object... args) {
        Logger.t(tag).i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void v(String tag, String message, Object... args) {
        Logger.t(tag).v(message, args);
    }

    public static void w(String tag, String message, Object... args) {
        Logger.t(tag).w(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    public static void wtf(String tag, String message, Object... args) {
        Logger.t(tag).wtf(message, args);
    }

    public static void json(String json) {
        Logger.json(json);
    }

    public static void json(String tag, String json) {
        Logger.t(tag).json(json);
    }

    public static void xml(String xml) {
        Logger.xml(xml);
    }

    public static void xml(String tag, String xml) {
        Logger.t(tag).xml(xml);
    }

}