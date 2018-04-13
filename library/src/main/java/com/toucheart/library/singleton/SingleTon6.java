package com.toucheart.library.singleton;

import android.util.Log;

/**
 * 作者：poeticAndroid  2017/5/29 12:03
 * 邮箱：codinghuang@163.com
 * 作用：饿汉式
 * 描述：静态内部内实现方式
 */
public class SingleTon6 {
    private static class SingletonHolder{
        private static final SingleTon6 instance = new SingleTon6();
    }

    private SingleTon6() {
        Log.d("SingleTon6", "初始化");
    }

    public static final SingleTon6 getinstance(){
        return SingletonHolder.instance;
    }

}
