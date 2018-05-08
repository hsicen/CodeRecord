package com.vince.news.news

import android.os.Bundle
import android.view.View
import com.vince.news.base.BaseFragment
import com.vince.news.R
import kotlinx.android.synthetic.main.item_detail.*

/**
 * <p>作者：黄思程  2017/11/16 11:07
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：新闻Item详情Fragment
 */
class NewsDetailFragment : BaseFragment() {
    private var mContent = ""

    override fun initContentView(): Int = R.layout.item_detail

    override fun initVariables() {
        mContent = arguments.getString(ARG_CONTENT)
    }

    override fun initView(view: View?) {
        tvItemDetail.text = mContent
    }

    override fun loadData() {

    }

    companion object {
        const val ARG_CONTENT = "content"

        /*** 单例*/
        fun newInstance(content: String?): NewsDetailFragment {
            val args = Bundle()
            args.putString(ARG_CONTENT, content)
            val fragment = NewsDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }
}