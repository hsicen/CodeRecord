package com.toucheart.library.singleton;

import android.util.Log;

import static android.content.ContentValues.TAG;

/**
 * 作者：poeticAndroid  2017/5/29 12:17
 * 邮箱：codinghuang@163.com
 * 作用：单例模式(枚举实现，线程安全)
 * 描述：通过枚举类型来实现单例模式
 */
public enum  EnumSingleton {
    INSTANCE;

    private EnumSingleton() {
        Log.d("EnumSingleton", "初始化");
    }

    public void doSomething(){
        Log.d(TAG, "doSomething: 调用单例方法");
    }
}
