package com.toucheart.library.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import java.io.File;
import java.net.URLConnection;

/**
 * 作者：Toucheart  2017/9/2 16:58
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：查找文件工具类
 */
public class OpenFileUtil {
    /*** 文件权限字符串*/
    public static final String FILE_AUTHORITIES = "com.android.provider.file";

    private OpenFileUtil() {

        throw new UnsupportedOperationException("you can't initial this...");
    }

    /**
     * @param filesPath 文件路径
     * @param context   显示打开方式
     */
    public static void openFile(String filesPath, Context context) {
        try {
            context.startActivity(showOpenTypeDialog(filesPath, context));
        } catch (Exception e) {
            ToastUtils.showShort("无法打开");
            LogUtils.e(e, "打开文件出错");
        }
    }

    /**
     * @param param   param
     * @param context context
     * @return showOpenTypeDialog
     */
    private static Intent showOpenTypeDialog(String param, Context context) {
        Intent intent = new Intent();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setAction(android.content.Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(context, FILE_AUTHORITIES, new File(param));
            intent.setDataAndType(uri, getFileType(param));
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setAction(android.content.Intent.ACTION_VIEW);
            Uri uri = Uri.fromFile(new File(param));
            intent.setDataAndType(uri, getFileType(param));
        }

        return intent;
    }

    /**
     * @param fileUrl 文件路径
     * @return 文件类型
     */
    public static String getFileType(String fileUrl) {
        String type;

        //首先根据MimeType类来获取
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        type = mimeTypeMap.getMimeTypeFromExtension
                (MimeTypeMap.getFileExtensionFromUrl(fileUrl));

        //然后再根据URLConnection获取
        if (type == null || TextUtils.isEmpty(type)) {
            type = URLConnection.guessContentTypeFromName(fileUrl);
        }

        //最后再根据文件后缀名设置文件类型
        if (type == null || TextUtils.isEmpty(type)) {
            File file = new File(fileUrl);
            if (file.exists()) {
                int lastDot = file.getName().lastIndexOf('.');
                if (lastDot >= 0) {
                    String extension = file.getName().substring(lastDot + 1);
                    type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
                }
            }
        }

        return type != null && !TextUtils.isEmpty(type) ? type : "*/*";
    }
}