package com.toucheart.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * 作者：Toucheart  2017/9/2 21:05
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：单例Toast
 */
public class ToastUtils {
    private static final int TIME_SHORT = Toast.LENGTH_SHORT;
    private static final int TIME_LONG = Toast.LENGTH_LONG;

    private static Toast mToast;

    /*** private*/
    private ToastUtils() {

        throw new UnsupportedOperationException("you can't initial this...");
    }

    /**
     * @param resId 显示short message
     */
    public static void showShort(int resId) {
        showToast(ContextUtil.withContext(), resId, TIME_SHORT);
    }

    /**
     * @param message 显示short message
     */
    public static void showShort(String message) {
        showToast(ContextUtil.withContext(), message, TIME_SHORT);
    }

    /**
     * @param resId 显示long message
     */
    public static void showLong(int resId) {
        showToast(ContextUtil.withContext(), resId, TIME_LONG);
    }

    /**
     * @param message 显示long message
     */
    public static void showLong(String message) {
        showToast(ContextUtil.withContext(), message, TIME_LONG);
    }

    @SuppressLint("ShowToast")
    private static void showToast(Context mContext, String text, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, text, duration);
        } else {
            mToast.setText(text);
        }
        mToast.show();
    }

    @SuppressLint("ShowToast")
    private static void showToast(Context mContext, int resId, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(mContext, resId, duration);
        } else {
            mToast.setText(resId);
        }
        mToast.show();
    }
}
