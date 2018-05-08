package com.vince.news.base

import android.os.Bundle
import android.view.Window

/**
 * <p>作者：黄思程  2017/11/16 16:48
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：带网络请求的Activity
 */
abstract class MvpActivity<P : BasePresenter<*>> : BaseActivity() {
    protected lateinit var mvpPresenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        mvpPresenter = createPresenter()

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()

        mvpPresenter.detachView()
    }

    override fun onBackPressed() {
        super.onBackPressed()

        mvpPresenter.unsubscribe()
    }

    /**
     * @return 创建Presenter
     * */
    protected abstract fun createPresenter(): P

}