package com.hsc.vince.androidmvp.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * <p>作者：黄思程  2018/4/10 16:51
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：懒加载Fragment base类
 */
public abstract class BaseLazyFragment extends Fragment {
    /*** 依附的Activity*/
    protected Activity mActivity;
    /*** 是否已经初始化过，如果页面只刷新一次则在数据刷新完后设置为true
     *     如果每次都需要刷新则设置为false*/
    protected boolean mIsInited;
    /*** view是否绑定完成*/
    protected boolean mIsPrepared;
    protected View view;
    private Unbinder mUnbinder;

    /*** 初始化变量,包括Intent数据*/
    protected abstract void initVariables();

    /***
     *  @return 设置layout*/
    protected abstract int initContentView();

    /***
     * @param v 初始化View和绑定事件*/
    protected abstract void initViews(View v);

    /**
     * fragment是否显示出来,包括viewpager左右滑动、跳转到其他页面返回，从桌面返回。只要是让它显示出来就会调用
     *
     * @param isVisible    是否显示
     * @param isSwitchShow true 是从其他fragment切换后显示的 false为其他情况
     */
    protected abstract void onFragmentVisibleChange(boolean isVisible, boolean isSwitchShow);

    /*** fragment一次显示，可以用于加载只加载一次的数据,
     * 注意：重复加载的请求不要放在这里，需要放置在onFragmentVisibleChange内*/
    protected abstract void onFragmentFirstVisible();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initVariables();
        view = inflater.inflate(initContentView(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initViews(view);
        mIsPrepared = true;
        lazyLoad();
        return view;
    }

    public void lazyLoad() {
        if (getUserVisibleHint() && mIsPrepared && !mIsInited) {
            onFragmentFirstVisible();
            mIsInited = true;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            lazyLoad();
        }
        if (mIsPrepared) {
            onFragmentVisibleChange(isVisibleToUser, true);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            onFragmentVisibleChange(true, false);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mIsPrepared = false;
        mUnbinder.unbind();
    }
}
