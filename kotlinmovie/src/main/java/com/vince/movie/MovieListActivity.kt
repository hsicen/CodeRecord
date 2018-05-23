package com.vince.movie

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vince.movie.adapter.MovieAdapter
import com.vince.movie.data.DemoData
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list.*
import java.net.URL
import java.nio.charset.Charset

/**
 * <p>作者：黄思程  2017/11/10 13:54
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：电影列表页
 */
class MovieListActivity : AppCompatActivity() {
    private var page = 1
    private val mAPI = "http://api.themoviedb.org//3/discover/movie?sort_by=popularity." +
            "desc&api_key=7e55a88ece9f03408b895a96c1487979&page=$page"

    private var adapter: MovieAdapter? = null
    private var movieList: ArrayList<DemoData.Movie> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        fab.setOnClickListener { view ->
            Snackbar.make(view, getText(R.string.movieList), Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show()
        }

        adapter = MovieAdapter(movieList)
        item_list.layoutManager = LinearLayoutManager(this)
        item_list.adapter = adapter

        loadMovieData()

        srlLayout.setOnRefreshListener { refreshData() }
    }

    /**
     * 加载电影数据
     */
    private fun loadMovieData() {
        showDialog()

        Thread {
            val jsonStr = URL(mAPI).readText(Charset.defaultCharset())

            val demoData = Gson().fromJson<DemoData.DataSource>(jsonStr, object : TypeToken<DemoData.DataSource>() {

            }.type)

            movieList.addAll(demoData.movies)

            runOnUiThread {
                adapter?.notifyDataSetChanged()
                hideDialog()
            }
        }.start()
    }

    /**
     * 加载更多
     */
    private fun loadMoreData() {
        page += 1
        loadMovieData()
    }

    /**
     * 下拉刷新
     */
    private fun refreshData() {
        page = 1
        movieList.clear()
        loadMovieData()
    }

    /**
     * 隐藏弹窗
     */
    private fun showDialog() {
        srlLayout.isRefreshing = true
    }

    /**
     * 隐藏弹窗
     */
    private fun hideDialog() {
        srlLayout.isRefreshing = false
    }
}
