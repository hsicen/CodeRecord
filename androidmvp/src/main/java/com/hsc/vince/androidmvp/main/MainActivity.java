package com.hsc.vince.androidmvp.main;

import android.widget.TextView;

import com.hsc.vince.androidmvp.R;
import com.hsc.vince.androidmvp.base.MvpActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <p>作者：黄思程  2018/4/12 13:48
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：电影列表
 */
public class MainActivity extends MvpActivity<MainPresenter>
        implements MainContract.View {
    @BindView(R.id.tvMsg)
    TextView mTvMsg;

    int count = 0;

    @Override
    protected MainPresenter createPresenter() {

        return new MainPresenter(this);
    }

    @Override
    protected void initVariables() {

        //initVar
    }

    @Override
    protected int initContentView() {

        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

        mTvMsg.setText("请求网络");
    }

    @Override
    protected void loadData() {

        //loaddata
    }

    @Override
    public void showLoading() {

        mTvMsg.setText("开始请求");
    }

    @Override
    public void hideLoading() {

        //hide
    }

    @Override
    public void onGetMoviesSuccess(String jsonStr) {

        mTvMsg.setText(jsonStr);
    }

    @Override
    public void onGetMoviesFailed(long code, String msg) {

        mTvMsg.setText(msg);
    }

    @OnClick(R.id.tvMsg)
    public void onViewClicked() {
        count += 1;
        mPresenter.requestMovies(count - 1, count);
    }
}
