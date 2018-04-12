package com.hsc.vince.androidmvp.base;

import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * <p>作者：黄思程  2018/4/12 10:06
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：带网络请求的Activity
 */
public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        mPresenter = createPresenter();
        if (mPresenter == null) {
            throw new NullPointerException("presenter cannot be nill");
        }

        super.onCreate(savedInstanceState);
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void onBackPressed() {
        finish();

        if (mPresenter != null) {
            mPresenter.onUnsubscribe();
        }
    }
}
