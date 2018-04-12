package com.toucheart.library.util;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 作者：Toucheart  2017/9/2 16:57
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：文件工具类
 */
public class FileUtils {

    private FileUtils() {

        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * @param path       路径
     * @param saveObject 保存对象到文件
     */
    public static void saveObject(String path, Object saveObject) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        File f = new File(path);
        try {
            fos = new FileOutputStream(f);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(saveObject);
        } catch (IOException e) {
            LogUtils.e(e, "");
        } finally {
            IOUtils.closeIO(oos);
            IOUtils.closeIO(fos);
        }
    }

    /**
     * @param path 文件路径
     * @return 根据路径恢复对象
     */
    public static Object restoreObject(String path) {
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        Object object = null;
        File f = new File(path);
        if (!f.exists()) {
            return null;
        }
        try {
            fis = new FileInputStream(f);
            ois = new ObjectInputStream(fis);
            object = ois.readObject();
            return object;
        } catch (IOException | ClassNotFoundException e) {
            LogUtils.e(e, "");
        } finally {
            IOUtils.closeIO(fis);
            IOUtils.closeIO(ois);
        }
        return object;
    }

    /**
     * @param filePath 文件路径
     * @return 根据路径获取文件
     */
    public static File getFileByPath(String filePath) {

        return isSpace(filePath) ? null : new File(filePath);
    }

    /**
     * @param filePath 文件路径
     * @return 文件是否存在
     */
    public static boolean isFileExists(String filePath) {

        return isFileExists(getFileByPath(filePath));
    }

    /**
     * @param file 文件
     * @return 文件是否存在
     */
    public static boolean isFileExists(File file) {

        return file != null && file.exists();
    }

    private static boolean isSpace(String s) {
        if (s == null) return true;
        for (int i = 0, len = s.length(); i < len; ++i) {
            if (!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * @return 判断是否有外部SD卡
     */
    public static boolean isSDCardReady() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * @return 获取内置SD卡路径
     */
    public static String getInnerSDPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * @return 获取外置SD卡路径
     */
    public static List<String> getExtSDPath() {
        List<String> lResult = new ArrayList<>();
        try {
            Runtime rt = Runtime.getRuntime();
            Process proc = rt.exec("mount");
            InputStream is = proc.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String line;
            while ((line = br.readLine()) != null) {
                if (line.contains("extSdCard")) {
                    String[] arr = line.split(" ");
                    String path = arr[1];
                    File file = new File(path);
                    if (file.isDirectory()) {
                        lResult.add(path);
                    }
                }
            }
            isr.close();
        } catch (Exception e) {
            LogUtils.d(">>>", "获取外部SD卡异常");
        }
        return lResult;
    }
}
