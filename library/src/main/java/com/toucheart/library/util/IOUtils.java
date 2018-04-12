package com.toucheart.library.util;

import java.io.Closeable;
import java.io.IOException;

/**
 * 作者：Toucheart  2017/9/2 16:57
 * 邮箱：codinghuang@163.com
 * 作用：
 * 描述：IO相关工具
 */
public class IOUtils {

    private IOUtils() {

        throw new UnsupportedOperationException("you can't initial me...");
    }

    /**
     * @param closeables 要关闭的IO
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                if (closeable != null) {
                    try {
                        closeable.close();
                    } catch (IOException e) {
                        LogUtils.e(e, "IO关闭出错");
                    }
                }
            }
        }
    }
}
