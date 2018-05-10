package com.hsc.vince.kotlindate

import org.junit.Assert
import org.junit.Test

/**
 * Author ：NightYoung  2018/5/10 10:12
 * Email    ：codinghuang@163.com
 * Func     ：
 * Desc     ：test Date
 */
class DateTest {

    @Test
    fun testDate() {

        //yesterday
        val yesterday1 = 1.days.ago
        val yesterday2 = 1 days ago

        println("yesterday1: $yesterday1")
        println("yesterday2: $yesterday2")

        Assert.assertEquals(yesterday1, yesterday2)


        //tomorrow
        val tomorrow1 = 1.days.after
        val tomorrow2 = 1 days after

        println("tomorrow1:  $tomorrow1")
        println("tomorrow2:  $tomorrow2")
        Assert.assertEquals(tomorrow1, tomorrow2)


        val agoDate = 30.minutes(ago)
        println("30 minutes ago:  $agoDate")
    }
}