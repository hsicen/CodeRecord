package com.hsc.vince.androidmvp.main;

import com.hsc.vince.androidmvp.base.BaseView;

/**
 * <p>作者：黄思程  2018/4/12 13:35
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：获取电影列表约束层
 */
public interface MainContract {

    interface View extends BaseView {
        void onGetMoviesSuccess(String jsonStr);

        void onGetMoviesFailed(long code, String msg);
    }

    interface Presenter {

        /***  获取电影列表
         * @param startIndex startIndex
         * @param count  count    */
        void requestMovies(int startIndex, int count);
    }
}
