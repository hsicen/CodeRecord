package com.toucheart.library.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * <p>作者：黄思程  2018/4/13 10:57
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：解决图片缩放抛出的各种异常
 */
public class HackyViewPager extends ViewPager {

    /**
     * @param context context
     */
    public HackyViewPager(Context context) {
        super(context);
    }

    /**
     * @param context context
     * @param attrs   attrs
     */
    public HackyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
