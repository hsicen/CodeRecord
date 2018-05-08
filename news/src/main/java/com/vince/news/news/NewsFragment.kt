package com.vince.news.news

import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener
import com.vince.news.DetailActivity
import com.vince.news.R
import com.vince.news.adapter.NewsAdapter
import com.vince.news.base.MvpLazyFragment
import com.vince.news.entity.DataClass
import com.vince.news.util.LogUtil
import com.vince.news.util.ToastUtil
import kotlinx.android.synthetic.main.layout_fragment_news_list.*

/**
 * <p>作者：黄思程  2017/11/15 11:20
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：新闻列表页
 */
class NewsFragment : MvpLazyFragment<NewsPresenter>(), BaseQuickAdapter.OnItemClickListener,
        OnRefreshLoadmoreListener, NewsContract.View {
    private var page = 1
    private var sortBy = "popularity.desc"

    private var mDataList: MutableList<DataClass.Movie> = mutableListOf()
    private var mAdapter: NewsAdapter = NewsAdapter(mDataList)
    private var mContent = ""
    private var isLoadMore = false

    override fun initContentView(): Int = R.layout.layout_fragment_news_list

    override fun createPresenter(): NewsPresenter = NewsPresenter(this)

    override fun initVariables() {

        mContent = arguments.getString(CONTENT)
    }

    override fun initView(view: View?) {
        rvNews.adapter = mAdapter
        mAdapter.isFirstOnly(true)
        mAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM)

        srlNewsList.autoRefresh()
        mAdapter.onItemClickListener = this
        srlNewsList.setOnRefreshLoadmoreListener(this)
    }

    override fun showLoading() {

    }

    override fun hideLoading() {
        srlNewsList.finishRefresh(true)
        srlNewsList.finishLoadmore(true)
    }

    override fun loadData() {

        mvpPresenter?.requestNewsData(sortBy, page)
    }

    override fun onGetNewsDataSuccess(source: DataClass.DataSource) {
        if (!isLoadMore) {
            mDataList.clear()
        }

        mDataList.addAll(source.movies)
        mAdapter.notifyDataSetChanged()
    }

    override fun onGetNewsDataFailed(code: Long, msg: String?) {
        LogUtil.d(code)

        ToastUtil.showShort(msg)
    }

    override fun onLoadmore(refreshlayout: RefreshLayout?) {
        page += 1
        isLoadMore = true
        loadData()
    }

    override fun onRefresh(refreshlayout: RefreshLayout?) {
        page = 1
        isLoadMore = false
        loadData()
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>?, view: View?, position: Int) {
        val movie = mDataList[position]
        DetailActivity.start(context, movie)
    }

    /*** 静态方法和静态常量的定义*/
    companion object {
        const val CONTENT = "content"

        /*** 单例*/
        fun newInstance(content: String): NewsFragment {
            val args = Bundle()
            args.putString(CONTENT, content)
            val fragment = NewsFragment()
            fragment.arguments = args
            return fragment
        }
    }
}