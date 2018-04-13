package com.toucheart.library.singleton;

import android.util.Log;

/**
 * 作者： poeticAndroid  2017/5/29 11:42
 * 邮箱： codinghuang@163.com
 * 作用：懒汉式线程安全
 * 描述：在高并发情况下,效率低下
 */
public class SingleTon2 {
    private static SingleTon2 instance;

    private SingleTon2() {
        Log.d("SingleTon2", "初始化");
    }

    public static synchronized SingleTon2 getInstance() {
        if (instance == null) {
            instance = new SingleTon2();
        }

        return instance;
    }
}
