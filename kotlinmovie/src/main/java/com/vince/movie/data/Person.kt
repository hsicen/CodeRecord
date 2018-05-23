package com.vince.movie.data

/**
 * <p>作者：黄思程  2017/11/20 15:25
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：后端变量
 */
class Person {
    val size: Int = 2

    var isEmpty: Boolean = false
        get() = this.size == 0
}