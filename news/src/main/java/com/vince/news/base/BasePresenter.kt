package com.vince.news.base

import com.vince.news.constant.BoreTest
import com.vince.news.net.ApiStore
import com.vince.news.net.HttpManager
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers
import rx.subscriptions.CompositeSubscription

/**
 * <p>作者：黄思程  2017/11/16 16:44
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：Presenter基类
 */
open class BasePresenter<V>(v: V) {
    protected var mvpView: V? = null
    protected lateinit var apiStores: ApiStore
    private var mCompositeSubscription: CompositeSubscription? = null

    init {
        attachView(v)
    }

    /**
     * 绑定view，并创建apiStores
     * @param mvpView view*/
    private fun attachView(mvpView: V) {
        this.mvpView = mvpView
        apiStores = HttpManager.newInstance().create(ApiStore::class.java)
    }

    /*** 重置网络链接*/
    fun resetClient() {
        if (BoreTest.isUnitTest) {
            return
        }
        HttpManager.clear()
        apiStores = HttpManager.newInstance().create(ApiStore::class.java)
    }

    /*** 释放view，并解绑RxJava*/
    fun detachView() {
        unsubscribe()
        this.mvpView = null
    }

    /*** RxJava取消注册，以避免内存泄露*/
    fun unsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription!!.hasSubscriptions()) {
            mCompositeSubscription?.unsubscribe()
        }
    }

    /**
     * RxJava添加注册，使用CompositeSubscription绑定被观察者和观察者，方便管理
     * @param observable 被观察者，会使用到装饰者
     * @param subscriber 观察者   */
    protected fun <T> addSubscription(observable: Observable<T>, subscriber: Subscriber<T>) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = CompositeSubscription()
        }
        val transformer: Observable.Transformer<T, T> = ObservableTransformer()
        val subscription = observable.compose(transformer).subscribe(subscriber)
        mCompositeSubscription?.add(subscription)
    }
}

class ObservableTransformer<T> : Observable.Transformer<T, T> {
    override fun call(observable: Observable<T>): Observable<T> {
        return if (BoreTest.isUnitTest) {
            observable
                    .subscribeOn(Schedulers.immediate())
                    .observeOn(Schedulers.immediate())
        } else {
            observable
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
        }
    }
}