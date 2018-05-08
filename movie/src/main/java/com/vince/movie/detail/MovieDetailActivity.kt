package com.vince.movie.detail

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.vince.movie.R
import kotlinx.android.synthetic.main.activity_item_detail.*

/**
 * <p>作者：黄思程  2017/11/10 13:56
 * <p>邮箱：codinghuang@163.com
 * <p>作用：用于手机显示电影详情
 * <p>描述：电影详情页
 */
class MovieDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_detail)
        setSupportActionBar(detail_toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        if (savedInstanceState == null) {
            val arguments = Bundle()
            arguments.putSerializable(MovieDetailFragment.ARG_ITEM,
                    intent.getSerializableExtra(MovieDetailFragment.ARG_ITEM))
            arguments.putString(MovieDetailFragment.ARG_URL,
                    intent.getStringExtra(MovieDetailFragment.ARG_URL))
            val fragment = MovieDetailFragment()
            fragment.arguments = arguments
            supportFragmentManager.beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {

                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}
