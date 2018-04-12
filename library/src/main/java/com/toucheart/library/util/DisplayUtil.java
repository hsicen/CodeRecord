package com.toucheart.library.util;

import android.view.View;
import android.view.ViewGroup;


/**
 * 作者：Toucheart  2017/9/2 17:00
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：尺寸相关工具类
 */
public final class DisplayUtil {
    private DisplayUtil() {

        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * @param dpValue dp值
     * @return dp转px的值
     */
    public static int dp2px(float dpValue) {
        final float scale = ContextUtil.withContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * @param pxValue px值
     * @return px转dp的值
     */
    public static int px2dp(float pxValue) {
        final float scale = ContextUtil.withContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * @param spValue sp值
     * @return sp转px的值
     */
    public static int sp2px(float spValue) {
        final float fontScale = ContextUtil.withContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * @param pxValue px值
     * @return px转sp的值
     */
    public static int px2sp(float pxValue) {
        final float fontScale = ContextUtil.withContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 测量视图尺寸
     *
     * @param view 视图
     * @return arr[0]: 视图宽度, arr[1]: 视图高度
     */
    public static int[] measureView(View view) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            );
        }
        int widthSpec = ViewGroup.getChildMeasureSpec(0, 0, lp.width);
        int lpHeight = lp.height;
        int heightSpec;
        if (lpHeight > 0) {
            heightSpec = View.MeasureSpec.makeMeasureSpec(lpHeight, View.MeasureSpec.EXACTLY);
        } else {
            heightSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        }
        view.measure(widthSpec, heightSpec);
        return new int[]{view.getMeasuredWidth(), view.getMeasuredHeight()};
    }

    /**
     * @param view 视图
     * @return 获取测量视图宽度
     */
    public static int getMeasuredWidth(View view) {

        return measureView(view)[0];
    }

    /**
     * @param view 视图
     * @return 获取测量视图高度
     */
    public static int getMeasuredHeight(View view) {

        return measureView(view)[1];
    }

    /**
     * @return 屏幕宽度
     */
    public static int getScreenWidth() {

        return ContextUtil.withContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * @return 屏幕高度
     */
    public static int getScreenHeight() {

        return ContextUtil.withContext().getResources().getDisplayMetrics().heightPixels;
    }
}
