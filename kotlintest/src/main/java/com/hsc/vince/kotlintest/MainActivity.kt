package com.hsc.vince.kotlintest

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val telUser = User.newTelUser(18783979136)
        val weiboUser = User.newWeiboUser("ZhaoWenZhuo")

        Log.d("telUser", "Tel: " + telUser.account)
        Log.d("weiboUser", "Weibo: " + weiboUser.account)

        val instance1 = singleKotlin
        val instance2 = singleKotlin
        instance1.action()
        instance2.action()
        Log.d("singleKotlin", "Equal: " + Objects.equals(instance1, instance2));

        val person = Person()
        println(person.num)
        person.nickName = "Mr.Geniusmart"
        println(person.nickName)


        val hello = "黄思程"
        Log.d("lastChar", "last: " + hello.lastChar())

        //1天前
        val ago = 1.days.ago
        Log.d("date", "时间: " + ago.toString())
        //2天后
        val after = 2.days.after
        Log.d("date", "时间: " + after.toString())

        //lambda表达式
        val sum = { x: Int, y: Int -> x + y }
        printSum(sum)
    }

    /**
     * lambda参数函数
     */
    private fun printSum(sum: (Int, Int) -> Int) {
        val result = sum(1, 2)
        println(result)
    }

    //单例类
    class User private constructor(val account: String) {

        companion object {
            fun newWeiboUser(email: String): User {
                return User(email)
            }

            fun newTelUser(tel: Long): User {
                return User(tel.toString())
            }
        }
    }

    //利用关键字声明单例类^
    object singleKotlin {
        fun action() {
            println(this.hashCode())
        }
    }
}
