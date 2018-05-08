package com.vince.news.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

/**
 * Create by 黄思程 on 2017/4/13  17:12
 * Function：
 * Desc：不会重复提示的ToastUtil
 */
public class ToastUtil {
    private static final int TIME_SHORT = Toast.LENGTH_SHORT;
    private static final int TIME_LONG = Toast.LENGTH_LONG;

    private static Toast mToast;

    /**
     * 显示short message
     *
     * @param resId string string资源id
     */
    public static void showShort(int resId) {
        showToast(ContextUtil.withContext(), resId, TIME_SHORT);
    }

    /**
     * 显示short message
     *
     * @param message 显示msg
     */
    public static void showShort(String message) {
        showToast(ContextUtil.withContext(), message, TIME_SHORT);
    }

    /**
     * 显示long message
     *
     * @param resId string string资源id
     */
    public static void showLong(int resId) {
        showToast(ContextUtil.withContext(), resId, TIME_LONG);
    }

    /**
     * 显示long message
     *
     * @param message 显示msg
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
