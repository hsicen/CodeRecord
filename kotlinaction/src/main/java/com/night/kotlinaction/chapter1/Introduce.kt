package com.night.kotlinaction.chapter1

/**
 * <p>作者：黄思程  2018/5/25 12:08
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Kotlin简介
 */

data class Person(
        val userName: String,
        val userAge: Int,
        val userSex: String? = null
)