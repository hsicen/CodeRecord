package com.toucheart.library.singleton;

import android.util.Log;

/**
 * 作者： poeticAndroid  2017/5/29 11:47
 * 邮箱： codinghuang@163.com
 * 作用： 懒汉式线程安全
 * 描述： 双重检测机制,效率高  （单例模式的最佳写法）
 */
public class SingleTon3 {
    private static volatile SingleTon3 instance;   //用volatile修饰可避免空地址的问题

    private SingleTon3() {
        Log.d("SingleTon3", "初始化");
    }

    public static synchronized SingleTon3 getInstance(){
        if (null == instance){
            synchronized (SingleTon3.class){
                if (null == instance){
                    instance = new SingleTon3();
                }
            }
        }
        return  instance;
    }
}
