package com.vince.movie.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.vince.movie.R
import com.vince.movie.data.DemoData
import com.vince.movie.detail.MovieDetailActivity
import com.vince.movie.detail.MovieDetailFragment
import kotlinx.android.synthetic.main.item_list_content.view.*

/**
 * <p>作者：黄思程  2017/11/10 14:44
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：Adapter适配器
 */
class MovieAdapter(private val mValues: ArrayList<DemoData.Movie>) :
        RecyclerView.Adapter<MovieAdapter.ViewHolder>() {
    private val mOnClickListener: View.OnClickListener
    private var mContext: Context? = null

    init {
        mOnClickListener = View.OnClickListener { v ->
            val item = v.tag as DemoData.Movie
            val intent = Intent(v.context, MovieDetailActivity::class.java).apply {
                putExtra(MovieDetailFragment.ARG_ITEM, item)
                putExtra(MovieDetailFragment.ARG_URL, getPosterUrl(item.posterPath))
            }
            v.context.startActivity(intent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_list_content, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        Glide.with(mContext).load(getPosterUrl(item.posterPath)).into(holder.mMovieImage)
        holder.mMovieName.text = item.title
        holder.mMovieDate.text = String.format(mContext!!.getString(R.string.date), item.releaseDate)
        holder.mMovieStar.text = String.format(mContext!!.getString(R.string.star), item.voteAverage)
        holder.mMovieDesc.text = item.overView

        with(holder.itemView) {
            tag = item
            setOnClickListener(mOnClickListener)
        }
    }

    override fun getItemCount(): Int {
        return mValues.size
    }

    /**
     * 拼接图片地址
     */
    private fun getPosterUrl(posterPath: String): String {
        return "https://image.tmdb.org/t/p/w185_and_h278_bestv2$posterPath"
    }

    inner class ViewHolder(mView: View) : RecyclerView.ViewHolder(mView) {
        val mMovieName: TextView = mView.movieName
        val mMovieDate: TextView = mView.movieDate
        val mMovieStar: TextView = mView.movieStar
        val mMovieDesc: TextView = mView.movieDesc
        val mMovieImage: ImageView = mView.image
    }
}