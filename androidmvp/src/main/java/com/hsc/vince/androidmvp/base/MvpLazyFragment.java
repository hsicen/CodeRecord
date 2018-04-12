package com.hsc.vince.androidmvp.base;

import android.content.Context;

/**
 * <p>作者：黄思程  2018/4/12 10:21
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：带网络请求的懒加载Fragment
 */
public abstract class MvpLazyFragment<P extends BasePresenter> extends BaseLazyFragment {
    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        mPresenter = createPresenter();
    }

    protected abstract P createPresenter();

    @Override
    public void onDetach() {
        super.onDetach();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
