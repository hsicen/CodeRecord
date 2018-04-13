package com.toucheart.library.singleton;

import android.util.Log;

/**
 * 作者： poeticAndroid  2017/5/29 11:58
 * 邮箱： codinghuang@163.com
 * 作用： 饿汉式
 * 描述： 静态块代码实现
 */
public class SingleTon5 {
    private static SingleTon5  instance = null;

    static {
        instance = new SingleTon5();
    }

    private SingleTon5() {
        Log.d("SingleTon5", "初始化");
    }

    public static SingleTon5 getInstance() {
        return instance;
    }
}
