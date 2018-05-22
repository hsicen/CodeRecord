package com.vince.news.base

import android.os.Bundle
import android.view.View

/**
 * <p>作者：黄思程  2017/11/16 17:00
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：带网络请求的Fragment
 */
abstract class MvpFragment<P : BasePresenter<*>> : BaseFragment() {
    protected lateinit var mvpPresenter: P

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        mvpPresenter = createPresenter()

        super.onViewCreated(view, savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

        mvpPresenter.detachView()
    }

    /**
     * @return 创建Presenter
     * */
    protected abstract fun createPresenter(): P
}