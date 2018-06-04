package com.night.kotlinaction.chapter4

/**
 * 作者： 黄思程   2018/5/29 18:11
 * 邮箱： codinghuang@163.com
 * 作用：
 * 描述：带主构造函数的类
 */
class User(val _nickName: String, val isSubscribed: Boolean = true) {
    val nickName: String = _nickName
}