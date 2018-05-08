package com.vince.news

import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.MenuItem
import com.vince.news.base.BaseActivity
import com.vince.news.entity.DataClass
import com.vince.news.news.NewsDetailFragment
import com.vince.news.util.LogUtil
import com.vince.news.util.ToastUtil
import kotlinx.android.synthetic.main.activity_item_detail.*
import java.net.URL

/**
 * <p>作者：黄思程  2017/11/16 11:04
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <>描述：列表Item详情页
 */
class DetailActivity : BaseActivity() {
    /** 确定mItem不会为空，延迟初始化*/
    private lateinit var mItem: DataClass.Movie

    override fun initContentView(): Int = R.layout.activity_item_detail

    override fun initVariables() {
        mItem = intent.getSerializableExtra(ARG_ITEM) as DataClass.Movie
    }

    override fun initViews() {
        setSupportActionBar(detailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragment = NewsDetailFragment.newInstance(mItem.overView)
        supportFragmentManager.beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit()

        fab.setOnClickListener {
            ToastUtil.showShort(mItem.title)
            LogUtil.d("<<<", mItem.voteAverage)
        }
    }

    override fun loadData() {
        mItem.let {
            toolbarLayout.title = it.title
            setBackImg(DataClass.convertUrl(it.posterPath))
        }
    }

    private fun setBackImg(url: String) {
        Thread {
            val createFromStream = Drawable.createFromStream(URL(url).openStream(), "123.jpg")
            toolbarLayout.post {
                toolbarLayout.background = createFromStream
            }
        }.start()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    companion object {
        const val ARG_ITEM = "item"

        /*** 跳转当前页面*/
        fun start(context: Context, movie: DataClass.Movie) {
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra(ARG_ITEM, movie)
            }
            context.startActivity(intent)
        }
    }
}
