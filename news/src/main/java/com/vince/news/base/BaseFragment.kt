package com.vince.news.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * <p>作者：黄思程  2017/11/15 11:21
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：基类Fragment
 */
abstract class BaseFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initVariables()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(initContentView(), container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
    }

    override fun onStart() {
        super.onStart()
        loadData()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        hideProgress()
    }

    /**
     * 设置资源文件
     */
    protected abstract fun initContentView(): Int

    /**
     * 初始化变量，bundle数据
     */
    protected abstract fun initVariables()

    /**
     * 初始化View
     */
    protected abstract fun initView(view: View?)

    /**
     * 加载数据
     */
    protected abstract fun loadData()

    /**
     * 显示进度
     */
    protected fun showProgress() {

    }

    /**
     * 隐藏进度
     */
    protected fun hideProgress() {

    }
}