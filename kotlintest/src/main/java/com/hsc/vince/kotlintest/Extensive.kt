package com.hsc.vince.kotlintest

/**
 * <p>作者：黄思程  2018/5/9 11:48
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：扩展函数
 */

/*** 获取字符串的最后一个字符*/
fun String.lastChar(): Char = this[this.length - 1]
