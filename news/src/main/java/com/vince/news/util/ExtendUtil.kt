package com.vince.news.util

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * <p>作者：黄思程  2017/11/21 17:42
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：对原生函数的扩展
 */

/*** 根据Url路径加载图片*/
fun ImageView.loadImage(mContext: Context, url: String) {
    Glide.with(mContext)
            .load(url)
            .into(this)
}

