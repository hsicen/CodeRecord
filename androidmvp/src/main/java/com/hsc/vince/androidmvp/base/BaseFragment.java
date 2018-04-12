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
 * <p>作者：黄思程  2018/4/10 16:40
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Fragment  base类
 */
public abstract class BaseFragment extends Fragment {
    protected Activity mActivity;
    private Unbinder mUnbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initVariables();
        View view = inflater.inflate(initContentView(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initViews(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadData();
    }

    /*** 初始化变量,包括Intent数据*/
    protected abstract void initVariables();

    /***
     *  @return 设置layout*/
    protected abstract int initContentView();

    /*** 初始化View和绑定事件
     ** @param v fragment的View*/
    protected abstract void initViews(View v);

    /*** 加载数据*/
    protected abstract void loadData();

    @Override
    public void onDestroyView() {
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        super.onDestroyView();
    }
}
