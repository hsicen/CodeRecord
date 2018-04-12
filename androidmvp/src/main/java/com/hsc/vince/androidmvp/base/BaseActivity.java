package com.hsc.vince.androidmvp.base;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;

import com.toucheart.library.manager.ActivityManager;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>作者：黄思程  2018/4/10 15:40
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：所有Activity的base类
 */
public abstract class BaseActivity extends SwipeBackActivity {
    protected Activity mActivity;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.pushActivity(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mActivity = this;
        initVariables();
        int layoutId = initContentView();
        if (layoutId != 0) {
            setContentView(layoutId);
        }

        //强制竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mUnbinder = ButterKnife.bind(this);
        initViews();
        loadData();
    }

    /*** 初始化变量,包括Intent数据*/
    protected abstract void initVariables();

    /***
     * @return 设置layout资源*/
    protected abstract int initContentView();

    /*** 初始化View和绑定事件*/
    protected abstract void initViews();

    /*** 加载数据*/
    protected abstract void loadData();

    @Override
    protected void onDestroy() {
        ActivityManager.popActivity(this);
        mUnbinder.unbind();

        super.onDestroy();
    }
}
