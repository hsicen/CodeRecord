package com.hsc.vince.androidmvp.net;


import org.reactivestreams.Publisher;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * <p>作者：黄思程  2018/4/11 16:20
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Observa装饰类
 */
public class DecodeTransformer<T> implements FlowableTransformer<T, T> {

    @Override
    public Publisher<T> apply(Flowable<T> upstream) {
        Flowable<T> newFlowable;
        newFlowable = upstream
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

        return newFlowable;
    }
}
