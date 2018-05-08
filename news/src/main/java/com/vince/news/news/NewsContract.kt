package com.vince.news.news

import com.vince.news.base.BaseView
import com.vince.news.entity.DataClass

/**
 * <p>作者：黄思程  2017/11/20 10:16
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：新闻详情契约类
 */
interface NewsContract {

    interface View : BaseView {
        /*** 获取数据成功*/
        fun onGetNewsDataSuccess(source: DataClass.DataSource)

        /*** 获取数据失败*/
        fun onGetNewsDataFailed(code: Long, msg: String?)
    }

    interface Presenter {
        /*** 获取电影数据*/
        fun requestNewsData(sortBy: String, page: Int)
    }
}