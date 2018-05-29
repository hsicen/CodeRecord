package com.night.kotlinaction.chapter2

/**
 * <p>作者：黄思程  2018/5/25 12:19
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：Kotlin基础
 */

fun max(a: Int, b: Int): Int {

    return if (a > b) a else b
}

//val本身不可变，但是val指向的对象可能是可变的
fun change() {
    val language = arrayListOf("Java")
    println("value:  $language")

    language.add("Kotlin")
    println("value:  $language")


    val value = 7.5e6
    println("value:  $value")
}

class Rectange(val height: Int, val width: Int) {
    val isSqure: Boolean
    //每次调用get方法都会重新判断
        get() = height == width
}

enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0), ORANGE(255, 255, 0),
    YELLOW(255, 255, 0), GREEN(0, 255, 0),
    BLUE(0, 0, 255), INDIGO(75, 0, 130),
    VIOLET(1, 2, 3)
}


interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr, val right: Expr) : Expr

fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'

fun isNotDigit(c: Char) = c !in '0'..'9'

fun recognize(c: Char) = when (c) {
    in '0'..'9' -> "It's a digit"
    in 'a'..'z', in 'A'..'Z' -> "It's a letter"
    else -> "I don't know"
}









