package com.toucheart.library.util;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.File;
import java.util.Locale;

/**
 * 作者：Toucheart  2017/9/2 16:57
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：意图相关工具类
 */
public final class IntentUtils {
    /*** viewAction*/
    private static final String ACTION = "android.intent.action.VIEW";
    /*** 默认的category*/
    private static final String CATEGORY = "android.intent.category.DEFAULT";

    private IntentUtils() {

        throw new UnsupportedOperationException("you can't initial this...");
    }

    /**
     * @param filePath 文件路徑
     * @return 打開文件的Intent
     */
    public static Intent getIntent(String filePath) {

        File file = new File(filePath);
        if (!file.exists())
            return null;
        /* 取得扩展名 */
        String end = file.getName().substring(file.getName().lastIndexOf('.') + 1, file.getName().length()).toLowerCase(Locale.getDefault());
        /* 依扩展名的类型决定MimeType */
        switch (end) {
            case "m4a":
            case "mp3":
            case "mid":
            case "xmf":
            case "ogg":
            case "wav":
                return getAudioIntent(filePath);
            case "3gp":
            case "mp4":
                return getVideoIntent(filePath);
            case "jpg":
            case "gif":
            case "png":
            case "jpeg":
            case "bmp":
                return getImageIntent(filePath);
            case "apk":
                return getApkIntent(filePath);
            case "ppt":
                return getPptIntent(filePath);
            case "xls":
                return getExcelIntent(filePath);
            case "doc":
                return getWordIntent(filePath);
            case "pdf":
                return getPdfIntent(filePath);
            case "chm":
                return getChmIntent(filePath);
            case "txt":
                return getTextIntent(filePath);
            default:
                return getAllIntent(filePath);
        }
    }

    /**
     * @param param 文件路径
     * @return 打开apk文件的Intent
     */
    private static Intent getAllIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "*/*");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return 获取一个用于打开APK文件的intent
     */
    private static Intent getApkIntent(String param) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开VIDEO文件的intent
     */
    private static Intent getVideoIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "video/*");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开AUDIO文件的intent
     */
    private static Intent getAudioIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "audio/*");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开图片文件的intent
     */
    private static Intent getImageIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addCategory(CATEGORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "image/*");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开PPT文件的intent
     */
    private static Intent getPptIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addCategory(CATEGORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-powerpoint");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开Excel文件的intent
     */
    private static Intent getExcelIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addCategory(CATEGORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/vnd.ms-excel");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开Word文件的intent
     */
    private static Intent getWordIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addCategory(CATEGORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开CHM文件的intent
     */
    private static Intent getChmIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addCategory(CATEGORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/x-chm");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开文本文件的intent
     */
    private static Intent getTextIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addCategory(CATEGORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "text/plain");
        return intent;
    }

    /**
     * @param param 文件路径
     * @return Android获取一个用于打开PDF文件的intent
     */
    private static Intent getPdfIntent(String param) {
        Intent intent = new Intent(ACTION);
        intent.addCategory(CATEGORY);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/pdf");
        return intent;
    }

    /**
     * @param packageName 包名
     * @return 获取卸载App的Intent
     */
    public static Intent getUninstallAppIntent(String packageName) {
        Intent intent = new Intent(Intent.ACTION_DELETE);
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 获取打开App的意图
     *
     * @param packageName 包名
     * @return 获取打开App的Intent
     */
    public static Intent getLaunchAppIntent(String packageName) {

        return ContextUtil.withContext().getPackageManager().getLaunchIntentForPackage(packageName);
    }

    /**
     * @param packageName 包名
     * @return 获取App具体设置的Intent
     */
    public static Intent getAppDetailsSettingsIntent(String packageName) {
        Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        intent.setData(Uri.parse("package:" + packageName));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * @param content 分享文本
     * @return 获取分享文本的intent
     */
    public static Intent getShareTextIntent(String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * @param content   文本
     * @param imagePath 图片文件路径
     * @return 获取分享图片的intent
     */
    public static Intent getShareImageIntent(String content, String imagePath) {
        return getShareImageIntent(content, FileUtils.getFileByPath(imagePath));
    }

    /**
     * @param content 文本
     * @param image   图片文件
     * @return 获取分享图片intent
     */
    private static Intent getShareImageIntent(String content, File image) {
        if (!FileUtils.isFileExists(image)) return null;
        return getShareImageIntent(content, Uri.fromFile(image));
    }

    /**
     * @param content 分享文本
     * @param uri     图片uri
     * @return 获取分享图片intent
     */
    private static Intent getShareImageIntent(String content, Uri uri) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        intent.setType("image/*");
        return intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * 需添加权限 {@code <uses-permission android:name="android.permission.SHUTDOWN"/>}
     *
     * @return 获取关机的intent
     */
    public static Intent getShutdownIntent() {
        Intent intent = new Intent(Intent.ACTION_SHUTDOWN);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * @param phoneNumber 电话号码
     * @return 获取跳至拨号界面的Intent
     */
    public static Intent getDialIntent(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.CALL_PHONE"/>}</p>
     *
     * @param phoneNumber 电话号码
     * @return 获取拨打电话的Intent
     */
    public static Intent getCallIntent(String phoneNumber) {
        Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:" + phoneNumber));
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * @param phoneNumber 接收号码
     * @param content     短信内容
     * @return 获取跳至发送短信界面的Intent
     */
    public static Intent getSendSmsIntent(String phoneNumber, String content) {
        Uri uri = Uri.parse("smsto:" + phoneNumber);
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", content);
        return intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    }

    /**
     * @param outUri 输出的uri
     * @return 获取拍照的Intent
     */
    public static Intent getCaptureIntent(Uri outUri) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, outUri);
        return intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_ACTIVITY_NEW_TASK);
    }
}
