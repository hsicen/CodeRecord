package com.toucheart.library.singleton;

import android.util.Log;

/**
 * 作者： poeticAndroid  2017/5/29 11:37
 * 邮箱： codinghuang@163.com
 * 作用： 懒汉式线程不安全
 * 描述： 在多线程模式下不能达到线程安全的目的
 */
public class SingleTon1 {
    private static SingleTon1  instance;

    private SingleTon1() {
        Log.d("SingleTon1", "初始化");
    }

    public static SingleTon1 getInstance(){
        if (instance == null) {
            instance = new SingleTon1();
        }
        return instance;
    }
}
