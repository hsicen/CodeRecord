package com.vince.news.news

import com.vince.news.base.BasePresenter
import com.vince.news.entity.DataClass
import com.vince.news.net.ApiCallback

/**
 * <p>作者：黄思程  2017/11/20 10:37
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：新闻列表Presenter
 */
class NewsPresenter(view: NewsContract.View) : BasePresenter<NewsContract.View>(view),
        NewsContract.Presenter {

    override fun requestNewsData(sortBy: String, page: Int) {
        /*** 显示加载进度*/
        mvpView?.showLoading()

        addSubscription(apiStores?.requestNewsData(
                sortBy,
                "7e55a88ece9f03408b895a96c1487979",
                page)!!, object : ApiCallback<DataClass.DataSource>() {

            override fun onSuccess(model: DataClass.DataSource) {

                mvpView?.onGetNewsDataSuccess(model)
            }

            override fun onFailure(code: Long, msg: String?) {

                mvpView?.onGetNewsDataFailed(code, msg)
            }

            override fun onFinish() {

                mvpView?.hideLoading()
            }
        })
    }

}