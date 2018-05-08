package com.vince.news.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

/**
 * <p>作者：黄思程  2017/11/15 16:45
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：ViewPager适配器
 */
class PagerAdapter(manager: FragmentManager?) : FragmentPagerAdapter(manager) {
    private var dataList = mutableListOf<Fragment>()

    constructor(manager: FragmentManager?, dataList: MutableList<Fragment>) : this(manager) {
        this.dataList = dataList
    }

    override fun getItem(position: Int): Fragment = dataList[position]

    override fun getCount(): Int = dataList.size
}