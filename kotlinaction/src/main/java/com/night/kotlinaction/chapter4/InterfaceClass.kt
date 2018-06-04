package com.night.kotlinaction.chapter4

import android.content.Context
import android.util.AttributeSet
import java.io.Serializable
import java.util.*
import kotlin.collections.HashSet

/**
 * 作者： 黄思程   2018/5/29 15:26
 * 邮箱： codinghuang@163.com
 * 作用：
 * 描述： 类和接口
 * 1.Kotlin 默认类为final的，如果要被子类继承，需要声明为open
 * 2.
 */

interface Clickable {
    fun click()
    fun showOff() = println("I'm clickable!")
}

interface Focusable {
    fun setFocus(b: Boolean) =
            println("I ${if (b) "got" else "lost"} focus.")

    fun showOff() = println("I'm focusable !")
}

class Button : Clickable, Focusable {

    override fun click() = println("I was clicked")

    override fun showOff() {
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}


open class RichButton : Clickable {
    final override fun click() {}

    fun disable() {}

    open fun animate() {}
}


abstract class Animated {
    abstract fun animate()

    open fun stopAnimating() {}

    fun animateTwice() {}
}


open class TalkativeButton : Focusable {
    fun yell() = println("Hey !")
    fun whisper() = println("Let's talk !")
}

fun TalkativeButton.giveSpeech() {
    yell()
    whisper()
}

interface State : Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}

class Button1 : View {
    override fun getCurrentState(): State = ButtonState()

    override fun restoreState(state: State) {}

    class ButtonState : State {}
}


class Outter {
    inner class Inner {
        fun getOutterReference(): Outter = this@Outter
    }
}


sealed class Expr {
    class Num(val value: Int) : com.night.kotlinaction.chapter4.Expr()

    class Sum(val left: com.night.kotlinaction.chapter4.Expr,
              val right: com.night.kotlinaction.chapter4.Expr) :
            com.night.kotlinaction.chapter4.Expr()
}

fun eval(expr: com.night.kotlinaction.chapter4.Expr): Int =
        when (expr) {
            is com.night.kotlinaction.chapter4.Expr.Num -> expr.value
            is com.night.kotlinaction.chapter4.Expr.Sum -> eval(expr.right) + eval(expr.left)
        }


open class Person(val nickName: String) {
    //do something
}

class TwitterPerson(nickName: String) : Person(nickName) {
    //do something
}


open class SwitchButton

class Secretive private constructor() {
    //do something
}

class Secretive2 {
    private constructor()

    constructor(name: String)

    constructor(name: String, age: Int)
}


//次构造函数
open class Text {
    constructor(ctx: Context) {
        // some code
    }

    constructor(ctx: Context, attr: AttributeSet) {
        //some code
    }
}

class SuperText : Text {

    constructor(ctx: Context) : super(ctx) {
        // some code
    }

    constructor(ctx: Context, attr: AttributeSet) : super(ctx, attr) {
        //some code
    }
}


interface Users {
    val nickName: String
}

class PrivateUser(override val nickName: String) : Users

class SubscribUser(val email: String) : Users {
    override val nickName: String
        get() = email.substringBefore('@')
}

class FacebookUser(val accountId: Int) : Users {
    override val nickName: String
        get() = getFacebookName(accountId)

    private fun getFacebookName(accountId: Int) = accountId.toString()
}


interface User1 {
    val email: String
    val nickName: String
        get() = email.substringBefore('@')
}

class User2(val name: String) {
    var address: String = "unspecified"
        set(value) {
            println("""
                Address was changed for $name:
                "$field" -> "$value".""".trimIndent())
            field = value
        }
}


class LengthCounter {
    var counter: Int = 0
        private set

    fun addword(word: String) {
        counter += word.length
    }
}


class Client(val name: String, val postalCode: Int) {

    override fun toString() = "Client(name=$name, postalCode=$postalCode)"

    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Client)
            return false
        return name == other.name
                && postalCode == other.postalCode
    }

    override fun hashCode(): Int = name.hashCode() * 31 + postalCode

    fun copy(name: String = this.name,
             postalCode: Int = this.postalCode) {
        Client(name, postalCode)
    }
}


class DelegatingCollection<T> : Collection<T> {
    private val innerList = arrayListOf<T>()

    override val size: Int get() = innerList.size

    override fun contains(element: T): Boolean = innerList.contains(element)

    override fun containsAll(elements: Collection<T>): Boolean = innerList.containsAll(elements)

    override fun isEmpty(): Boolean = innerList.isEmpty()

    override fun iterator(): Iterator<T> = innerList.iterator()
}


class DelegateCollection<T>(innerList: Collection<T> = ArrayList()) :
        Collection<T> by innerList {
    //do something

}


class CountSet<T>(val innerSet: MutableCollection<T> = HashSet())
    : MutableCollection<T> by innerSet {

    var objectAdd = 0;

    override fun add(element: T): Boolean {
        objectAdd++
        return innerSet.add(element)
    }

    override fun addAll(elements: Collection<T>): Boolean {
        objectAdd += elements.size
        return innerSet.addAll(elements)
    }
}


data class Person2(val name: String) {
    object NameComparator : Comparator<Person2> {
        override fun compare(o1: Person2, o2: Person2):
                Int = o1.name.compareTo(o2.name)
    }
}