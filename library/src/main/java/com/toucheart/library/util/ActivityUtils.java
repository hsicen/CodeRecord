package com.toucheart.library.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

/**
 * 作者：Toucheart  2017/9/2 16:54
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：Activity跳转工具类
 */
public class ActivityUtils {

    private ActivityUtils() {

        throw new UnsupportedOperationException("you can't instantiate me...");
    }

    private static void startActivity(Context context, Bundle extras, String pkg, String cls) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (extras != null)
            intent.putExtras(extras);
        intent.setComponent(new ComponentName(pkg, cls));
        if (!(context instanceof Activity)) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }

    private static void startActivity(Context context, Intent intent) {
        if (null != context && null != intent) {
            try {
                context.startActivity(intent);
            } catch (Exception var3) {
                LogUtils.e(">>>", var3.toString(), var3);
            }
        }
    }

    private static void startActivity(Context context, Class<? extends Activity> activityClass) {
        if (null != context && null != activityClass) {
            try {
                Intent e = new Intent(context, activityClass);
                context.startActivity(e);
            } catch (Exception var3) {
                LogUtils.e(">>>", var3.toString(), var3);
            }
        }
    }

    /**
     * @param extras    bundle参数
     * @param activity  activity
     * @param cls       activity类
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void start(Bundle extras, Activity activity, Class<?> cls, int enterAnim, int exitAnim) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName());
        activity.overridePendingTransition(enterAnim, exitAnim);
    }

    /**
     * @param extras   bundle参数
     * @param activity activity
     * @param cls      activity类
     */
    public static void start(Bundle extras, Activity activity, Class<?> cls) {
        startActivity(activity, extras, activity.getPackageName(), cls.getName());
    }

    /**
     * 使用Intent进行跳转（可以将参数放在Intent中）
     *
     * @param context 上下文
     * @param intent  跳转意图
     */
    public static void start(Context context, Intent intent) {
        startActivity(context, intent);
    }

    /**
     * 使用Intent进行跳转（可以将参数放在Intent中）
     *
     * @param context   上下文
     * @param intent    跳转意图
     * @param enterAnim 入场动画
     * @param exitAnim  出场动画
     */
    public static void start(Activity context, Intent intent, int enterAnim, int exitAnim) {
        startActivity(context, intent);
        if (context != null) {
            context.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    /**
     * 不携带参数的跳转
     *
     * @param context       上下文
     * @param activityClass Activity类名
     */
    public static void start(Context context, Class<? extends Activity> activityClass) {
        startActivity(context, activityClass);
    }

    /**
     * 不携带参数的跳转
     *
     * @param context       上下文
     * @param activityClass Activity类名
     * @param enterAnim     入场动画
     * @param exitAnim      出场动画
     */
    public static void start(Activity context, Class<? extends Activity> activityClass, int enterAnim, int exitAnim) {
        startActivity(context, activityClass);
        if (context != null) {
            context.overridePendingTransition(enterAnim, exitAnim);
        }
    }

    public static void start(Context context, String packageName) {
        if (null != packageName) {
            try {
                PackageManager e = context.getPackageManager();
                Intent intent = e.getLaunchIntentForPackage(packageName);
                String className = intent.getComponent().getClassName();
                Intent newIntent = new Intent("android.intent.action.VIEW");
                ComponentName cn = new ComponentName(packageName, className);
                newIntent.setComponent(cn);
                newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(newIntent);
            } catch (Exception var7) {
                LogUtils.e(var7, "出错");
            }
        }
    }
}
