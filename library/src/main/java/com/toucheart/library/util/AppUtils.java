package com.toucheart.library.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Toucheart  2017/9/2 16:55
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：应用程序工具类
 */
public class AppUtils {

    private AppUtils() {

        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * 判断App是否安装
     *
     * @param packageName 包名
     * @return {@code true}: 已安装<br>{@code false}: 未安装
     */
    public static boolean isInstallApp(String packageName) {

        return !isSpace(packageName) && IntentUtils.getLaunchAppIntent(packageName) != null;
    }

    /**
     * 得到当前应用版本号名称
     *
     * @return 版本名，如：1.0.0
     */
    public static String getVersionName() {
        Context context = ContextUtil.withContext();
        String versionName = "";
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context
                    .getPackageName(), 0);
            versionName = pi.versionName;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }

    /**
     * 得到当前应用版本号
     *
     * @return 版本名，如：1
     */
    public static int getVersionCode() {
        Context context = ContextUtil.withContext();
        int versionCode = 0;
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context
                    .getPackageName(), 0);
            versionCode = pi.versionCode;
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionCode;
    }

    /**
     * 根据包名获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @param packageName 包名
     * @return 当前应用的AppInfo
     */
    public static AppInfo getAppInfo(String packageName) {
        try {
            PackageManager pm = ContextUtil.withContext().getPackageManager();
            PackageInfo pi = pm.getPackageInfo(packageName, 0);
            return getBean(pm, pi);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取App信息
     * <p>AppInfo（名称，图标，包名，版本号，版本Code，是否系统应用）</p>
     *
     * @return 当前应用的AppInfo
     */
    public static AppInfo getCurrentAppInfo() {

        return getAppInfo(ContextUtil.withContext().getPackageName());
    }

    /**
     * @return 获取安装的应用
     */
    public static List<AppInfo> getInstallApps() throws PackageManager.NameNotFoundException {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager pm = ContextUtil.withContext().getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo app : apps) {

            appList.add(getBean(pm, pm.getPackageInfo(app.packageName, 0)));
        }

        return appList;
    }

    /**
     * @return 获取系统应用
     */
    public static List<AppInfo> getSystemApps() throws PackageManager.NameNotFoundException {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager pm = ContextUtil.withContext().getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo app : apps) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                appList.add(getBean(pm, pm.getPackageInfo(app.packageName, 0)));
            }
        }

        return appList;
    }

    /**
     * @return 获取第三方应用
     */
    public static List<AppInfo> getThirdApps() throws PackageManager.NameNotFoundException {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager pm = ContextUtil.withContext().getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo app : apps) {
            if ((app.flags & ApplicationInfo.FLAG_SYSTEM) <= 0) {
                appList.add(getBean(pm, pm.getPackageInfo(app.packageName, 0)));
            }
        }

        return appList;
    }

    /**
     * @return 获取安装在SD卡的应用
     */
    public static List<AppInfo> getSdCardApps() throws PackageManager.NameNotFoundException {
        List<AppInfo> appList = new ArrayList<>();
        PackageManager pm = ContextUtil.withContext().getPackageManager();
        List<ApplicationInfo> apps = pm.getInstalledApplications(PackageManager.GET_UNINSTALLED_PACKAGES);
        for (ApplicationInfo app : apps) {
            if ((app.flags & ApplicationInfo.FLAG_EXTERNAL_STORAGE) != 0) {
                appList.add(getBean(pm, pm.getPackageInfo(app.packageName, 0)));
            }
        }

        return appList;
    }

    /**
     * 得到AppInfo的Bean
     *
     * @param pm 包的管理
     * @param pi 包的信息
     * @return AppInfo类
     */
    private static AppInfo getBean(PackageManager pm, PackageInfo pi) {
        if (pm == null || pi == null) return null;
        ApplicationInfo ai = pi.applicationInfo;
        String packageName = pi.packageName;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        boolean isThird = (ApplicationInfo.FLAG_SYSTEM & ai.flags) < 0;
        boolean isSystemUpdate = (ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & ai.flags) != 0;
        return new AppInfo(name, icon, packageName,
                packagePath, versionName, versionCode,
                isSystem, isThird, isSystemUpdate);
    }

    /**
     * 封装App信息的Bean类
     */
    public static class AppInfo {

        /*** 应用名字*/
        private String name;
        /*** 应用icon*/
        private Drawable icon;
        /*** 应用包名*/
        private String packageName;
        /*** 应用包路径*/
        private String packagePath;
        /*** versionName*/
        private String versionName;
        /*** appCode*/
        private int versionCode;
        /*** 是否系统应用*/
        private boolean isSystem;
        /*** 是否第三方应用*/
        private boolean isThird;
        /*** 是否系统应用升级*/
        private boolean isSystemUpdate;

        public AppInfo() {
        }

        public AppInfo(String name, Drawable icon, String packageName,
                       String packagePath, String versionName, int versionCode,
                       boolean isSystem, boolean isThird, boolean isSystemUpdate) {
            this.name = name;
            this.icon = icon;
            this.packageName = packageName;
            this.packagePath = packagePath;
            this.versionName = versionName;
            this.versionCode = versionCode;
            this.isSystem = isSystem;
            this.isThird = isThird;
            this.isSystemUpdate = isSystemUpdate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Drawable getIcon() {
            return icon;
        }

        public void setIcon(Drawable icon) {
            this.icon = icon;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public String getPackagePath() {
            return packagePath;
        }

        public void setPackagePath(String packagePath) {
            this.packagePath = packagePath;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public boolean isSystem() {
            return isSystem;
        }

        public void setSystem(boolean system) {
            isSystem = system;
        }

        public boolean isThird() {
            return isThird;
        }

        public void setThird(boolean third) {
            isThird = third;
        }

        public boolean isSystemUpdate() {
            return isSystemUpdate;
        }

        public void setSystemUpdate(boolean systemUpdate) {
            isSystemUpdate = systemUpdate;
        }
    }

    /**
     * 是否为空白字符串
     *
     * @param s 文本
     * @return true：文本为空白字符串
     */
    private static boolean isSpace(String s) {
        if (s == null) {
            return true;
        }
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}