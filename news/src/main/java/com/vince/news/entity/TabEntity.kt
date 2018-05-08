package com.vince.news.entity

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * <p>作者：黄思程  2017/11/15 17:10
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：TableLayout数据类
 */
class TabEntity(
        /*** 标题*/
        var title: String,
        /*** 选中的Icon*/
        var selectedIcon: Int,
        /*** 未选中的Icon*/
        var unSelectedIcon: Int) : CustomTabEntity {

    override fun getTabUnselectedIcon(): Int = unSelectedIcon

    override fun getTabSelectedIcon(): Int = selectedIcon

    override fun getTabTitle(): String = title

}