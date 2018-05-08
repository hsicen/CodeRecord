package com.vince.news.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.vince.news.R
import com.vince.news.entity.DataClass
import com.vince.news.util.loadImage

/**
 * <p>作者：黄思程  2017/11/16 9:50
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：新闻列表页数据适配器
 */
class NewsAdapter(data: MutableList<DataClass.Movie>?) :
        BaseQuickAdapter<DataClass.Movie, BaseViewHolder>(R.layout.news_item, data) {

    override fun convert(holder: BaseViewHolder, item: DataClass.Movie?) {
        holder.setText(R.id.newsContent, item?.overView)

        val imgView = holder.getView<ImageView>(R.id.newsImage)
        imgView.loadImage(mContext, DataClass.convertUrl(item?.posterPath))
    }
}