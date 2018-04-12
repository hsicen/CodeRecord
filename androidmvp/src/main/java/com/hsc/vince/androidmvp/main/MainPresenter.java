package com.hsc.vince.androidmvp.main;

import com.hsc.vince.androidmvp.base.BasePresenter;
import com.hsc.vince.androidmvp.net.ApiCallback;

import org.reactivestreams.Subscription;

/**
 * <p>作者：黄思程  2018/4/12 13:36
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：获取电影列表  presenter层
 */
public class MainPresenter extends BasePresenter<MainContract.View>
        implements MainContract.Presenter {

    /*** 创建并注入View
     * @param view view  */
    public MainPresenter(MainContract.View view) {
        super(view);
    }

    @Override
    public void requestMovies(int startIndex, int count) {
        mMvpView.showLoading();

        addSubscription(mApiStore.requestMovies(startIndex, count),
                new ApiCallback<String>() {
                    @Override
                    public void onSubscribe(Subscription s) {

                        //on
                    }

                    @Override
                    public void onSuccess(String model) {

                        mMvpView.onGetMoviesSuccess(model);
                    }

                    @Override
                    public void onFailed(long code, String errorMsg) {

                        mMvpView.onGetMoviesFailed(code, errorMsg);
                    }

                    @Override
                    public void onFinish() {

                        mMvpView.hideLoading();
                    }
                });
    }
}
