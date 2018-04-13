package com.toucheart.library.singleton;

import android.util.Log;

/**
 * 作者： poeticAndroid  2017/5/29 11:54
 * 邮箱： codinghuang@163.com
 * 作用： 饿汉式
 * 描述： 线程安全，效率高，但是初始化时就加载会很浪费内存
 */
public class SingleTon4 {
    private static SingleTon4 instance = new SingleTon4();

    private SingleTon4() {
        Log.d("SingleTon4", "初始化");
    }

    public static SingleTon4 getInstance() {
        return instance;
    }
}
