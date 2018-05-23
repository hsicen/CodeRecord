package com.vince.movie.detail

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.vince.movie.R
import com.vince.movie.data.DemoData
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*
import java.net.URL

/**
 * <p>作者：黄思程  2017/11/10 13:55
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：电影详情Fragment
 */
class MovieDetailFragment : Fragment() {
    private var mItem: DemoData.Movie? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments!!.containsKey(ARG_ITEM)) {

            mItem = arguments!!.getSerializable(ARG_ITEM) as DemoData.Movie?
            val url = arguments!!.getString(ARG_URL)
            mItem?.let {
                activity!!.toolbar_layout?.title = it.title
                Thread {
                    val createFromStream = Drawable.createFromStream(URL(url).openStream(), "123.jpg")
                    activity!!.toolbar_layout.post {
                        activity!!.toolbar_layout.background = createFromStream
                    }
                }.start()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        mItem?.let {
            rootView.item_detail.text = it.overView
        }

        return rootView
    }

    companion object {

        const val ARG_ITEM = "movie"
        const val ARG_URL = "url"
    }

    fun maxofTwo(a: Int, b: Int) = if (a > b) a else b
}
