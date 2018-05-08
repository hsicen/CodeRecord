package com.vince.news.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * <p>作者：黄思程  2017/11/15 11:02
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：基类Activity
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initVariables()
        setContentView(initContentView())

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        initViews()
        loadData()
    }

    /** @return 设置layout资源*/
    protected abstract fun initContentView(): Int

    /** 初始化变量,包括Intent数据*/
    protected abstract fun initVariables()

    /** 初始化View和绑定事件*/
    protected abstract fun initViews()

    /** 加载数据*/
    protected abstract fun loadData()

    /** 显示进度*/
    protected fun showProgress() {

    }

    /** 隐藏进度*/
    protected fun hideProgress() {

    }

    override fun onDestroy() {
        super.onDestroy()
        hideProgress()
    }
}