package com.toucheart.library.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import androidx.core.content.FileProvider;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * <p>作者：黄思程  2018/4/12 14:45
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：安装APK，兼容了7.0及以上和root方式安装
 */
public class InstallUtil {


    /*** 安装工具类，不允许初始化*/
    private InstallUtil() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /***
     * @param context  上下文
     * @param apkPath  要安装的APK
     * @param rootMode 是否是Root模式*/
    private static void install(Context context, String apkPath, boolean rootMode) {
        if (rootMode) {
            installRoot(context, apkPath);
        } else {
            installNormal(context, apkPath);
        }
    }

    /**
     * 通过非Root模式安装
     *
     * @param context 上下文
     * @param apkPath 安装路径
     */
    public static void install(Context context, String apkPath) {
        install(context, apkPath, false);
    }

    /*** 普通安装*
     * @param context 上下文
     * @param apkPath apk文件路径  */
    private static void installNormal(Context context, String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            //authority  为新版本访问文件特性
            Uri apkUri = FileProvider.getUriForFile(context, "com.gouuse.gouuse.apk", file);
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(new File(apkPath)),
                    "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /***   通过Root方式安装
     * @param context Context
     * @param apkPath 安装包路径   */
    @SuppressLint("CheckResult")
    private static void installRoot(final Context context, final String apkPath) {
        Observable.just(apkPath)
                .map(mApkPath -> "pm install -r " + mApkPath)
                .map(InstallUtil::rootCommand)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == 0) {
                        Toast.makeText(context, "安装成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "root权限获取失败,尝试普通安装", Toast.LENGTH_SHORT).show();
                        install(context, apkPath);
                    }
                });
    }

    /*** 应用程序运行命令获取 Root权限，设备必须已破解(获得ROOT权限)*
     * @param command 命令：String apkRoot="chmod 777 "+getPackageCodePath();
     * @return 0 命令执行成功   */
    public static int rootCommand(String command) {
        Process process = null;
        DataOutputStream os = null;
        int i;
        try {
            process = Runtime.getRuntime().exec("su");
            os = new DataOutputStream(process.getOutputStream());
            os.writeBytes(command + "\n");
            os.writeBytes("exit\n");
            os.flush();
            i = process.waitFor();
            return i;
        } catch (Exception e) {
            LogUtils.e(e, "出错");
            i = -1;
        }

        try {
            if (os != null) {
                os.close();
            }
            if (process != null) {
                process.destroy();
            }
        } catch (Exception e) {
            LogUtils.e(e, "出错");
        }
        return i;
    }

    /*** 提升读写权限*
     * @param filePath 文件路径
     * @throws IOException exception  */
    public static void setPermission(String filePath) {
        String command = "chmod " + "777" + " " + filePath;
        Runtime runtime = Runtime.getRuntime();
        try {
            runtime.exec(command);
        } catch (IOException e) {
            LogUtils.e(e, "安装出错");
        }
    }

    /**
     * 该方法执行具体的静默安装逻辑,需要手机ROOT
     *
     * @param apkPath 需要安装的apk路径
     * @return 安装成功返回true，安装失败返回false
     */
    public boolean install(String apkPath) {
        boolean result = false;
        DataOutputStream dataOutputStream = null;
        BufferedReader errorStream = null;

        try {
            //申请su权限
            Process process = Runtime.getRuntime().exec("su");
            dataOutputStream = new DataOutputStream(process.getOutputStream());

            //执行pm install 命令
            String command = "pm install -r " + apkPath + "\n";
            dataOutputStream.write(command.getBytes(Charset.forName("utf-8")));
            dataOutputStream.flush();
            dataOutputStream.writeBytes("exit\n");
            dataOutputStream.flush();
            process.waitFor();

            errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String msg = "";
            String line;

            //读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }

            //如果执行结果中包含Failure字样就认为安装失败,否则安装成功
            if (!msg.contains("Failure")) {
                result = true;
            }
        } catch (IOException | InterruptedException e) {
            LogUtils.e(e, "出错");
        } finally {
            IOUtils.closeIO(dataOutputStream);
            IOUtils.closeIO(errorStream);
        }
        return result;
    }

    /**
     * 该方法用来判断用户是否有ROOT权限
     *
     * @return 返回是否有ROOT权限
     */
    public boolean isRoot() {
        boolean bool = false;

        try {
            bool = new File("/system/bin/su").exists() || new File("/system/xbin/su").exists();
        } catch (Exception e) {
            LogUtils.e(e, "出错");
        }

        return bool;
    }

    /**
     * 删除之前的apk
     *
     * @param apkName apk名字
     * @return
     */
    public static File clearApk(Context context, String apkName) {
        File apkFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), apkName);
        if (apkFile.exists()) {
            apkFile.delete();
        }
        return apkFile;
    }

    /**
     * 查找apk
     *
     * @param context context
     * @param apkName apkName
     * @return apkPath
     */
    public static String getApk(Context context, String apkName) {
        File apkFile = new File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), apkName);
        if (apkFile.exists()) {
            return apkFile.getAbsolutePath();
        }
        return null;
    }

    /*** 安装一个apk文件*/
    public static void install(Context context, File uriFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /*** 卸载一个app*/
    public static void uninstall(Context context, String packageName) {
        //通过程序的包名创建URI
        Uri packageURI = Uri.parse("package:" + packageName);
        //创建Intent意图
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        //执行卸载程序
        context.startActivity(intent);
    }

    /*** 检查手机上是否安装了指定的软件*/
    public static boolean isAvailable(Context context, String packageName) {
        // 获取packagemanager
        final PackageManager packageManager = context.getPackageManager();
        // 获取所有已安装程序的包信息
        List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);
        // 用于存储所有已安装程序的包名
        List<String> packageNames = new ArrayList<>();
        // 从pinfo中将包名字逐一取出，压入pName list中
        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                String packName = packageInfos.get(i).packageName;
                packageNames.add(packName);
            }
        }
        // 判断packageNames中是否有目标程序的包名，有TRUE，没有FALSE
        return packageNames.contains(packageName);
    }

    /*** 检查手机上是否安装了指定的软件*/
    public static boolean isAvailable(Context context, File file) {
        return isAvailable(context, getPackageName(context, file.getAbsolutePath()));
    }

    /*** 根据文件路径获取包名*/
    public static String getPackageName(Context context, String filePath) {
        PackageManager packageManager = context.getPackageManager();
        PackageInfo info = packageManager.getPackageArchiveInfo(filePath, PackageManager.GET_ACTIVITIES);
        if (info != null) {
            ApplicationInfo appInfo = info.applicationInfo;
            return appInfo.packageName;  //得到安装包名称
        }
        return null;
    }

    /*** 从apk中获取版本信息*/
    public static String getChannelFromApk(Context context, String channelPrefix) {
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + channelPrefix;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split(channelPrefix);
        String channel = "";
        if (split.length >= 2) {
            channel = ret.substring(key.length());
        }
        return channel;
    }
}
