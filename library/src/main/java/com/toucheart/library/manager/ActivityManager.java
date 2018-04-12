package com.toucheart.library.manager;

import android.app.Activity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>作者：黄思程  2018/4/10 15:55
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Activity工具类
 */
@SuppressWarnings("ALL")
public class ActivityManager {
    private ActivityManager() {
    }

    private static final List<Activity> mActivities = Collections
            .synchronizedList(new LinkedList<Activity>());

    /***
     * @param activity 将指定Activity入栈*/
    public static void pushActivity(Activity activity) {
        if (mActivities == null) {
            return;
        }

        mActivities.add(activity);
    }

    /***
     * @param activity 将制定Activity出栈*/
    public static void popActivity(Activity activity) {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }

        mActivities.remove(activity);
    }

    /***
     * @return 获取栈顶Activity*/
    public static Activity currentActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return null;
        }

        return mActivities.get(mActivities.size() - 1);
    }

    /***
     * @param activity  结束指定Activity*/
    public static void finishActivity(Activity activity) {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }

        if (activity != null) {
            mActivities.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /*** 结束当前页面(栈顶Activity)*/
    public static void finishCurrentActivity() {
        if (mActivities == null || mActivities.isEmpty()) {
            return;
        }

        Activity activity = mActivities.get(mActivities.size() - 1);
        finishActivity(activity);
    }

    /**
     * @param activity 指定的类名
     * @return 指定Activity
     */
    public static Activity findActivity(Class<?> activity) {
        Activity target = null;
        if (mActivities != null) {
            for (Activity mActivity : mActivities) {
                if (mActivity.getClass().equals(activity)) {
                    target = mActivity;
                    break;
                }
            }
        }

        return target;
    }

    /*** 结束所有Activity*/
    public static void finishAllActivity() {
        if (mActivities == null) {
            return;
        }
        for (Activity activity : mActivities) {
            activity.finish();
        }
        mActivities.clear();
    }
}
