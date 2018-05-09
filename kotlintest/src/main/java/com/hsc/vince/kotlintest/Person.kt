package com.hsc.vince.kotlintest

/**
 * <p>作者：黄思程  2018/5/8 17:55
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：AndroidUtils
 */
public class Person {
    val num = 10
    var age = ""
    var sex = "男"
    var email = ""

    var nickName = "geniuSmart"
        get() = field.plus("@gmail.com")
}