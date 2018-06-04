package com.night.kotlinaction

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.night.kotlinaction.chapter2.*
import com.night.kotlinaction.chapter4.Secretive2
import com.night.kotlinaction.chapter4.User
import java.lang.Exception
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        testBase()

        val mixColor = mix(Color.RED, Color.YELLOW)
        println("The mix color is: $mixColor")

        val value = Sum(Sum(Num(1), Num(2)), Num(4))
        println("The value of (1+2)+4 is:  $value")

        eval(value)

        for (vvv in 1..100) {
            println("  " + fizzBuzz(vvv))
        }

        for (vvv in 100 downTo 1 step 2) {
            println("  " + fizzBuzz(vvv))
        }

        for (vvv in 0 until 20 step 5) {
            println("   $vvv")
        }


        val binaryReps = TreeMap<Char, String>()
        for (c in 'A'..'F') {
            val b = Integer.toBinaryString(c.toInt())
            binaryReps[c] = b
        }
        for ((key, values) in binaryReps) {
            println("$key = $values")
        }


        val listItem = arrayOf("10", "11", "1001")
        for ((index, elem) in listItem.withIndex()) {
            println("$index:  $elem")
        }

        println("Kotlin" in "Java".."Scala")   //True
        println("Kotlin" in setOf("Java", "Scala")) //False


        val listOf = listOf(1, 2, 3)
        println(listOf.joinToString(separator = "", prefix = "", postfix = "."))

        println("12.345-6.A".split("\\. | -".toRegex()))
        println("12.345-6.A".split(".", "-"))

        val user = User(isSubscribed = false, _nickName = "Alice")

        val secretive2 = Secretive2("Mike", 12)
    }

    private fun testBase() {
        change()

        val shap = Rectange(12, 12)
        println("isSqure:  ${shap.isSqure}")

        println("The Color: ${Color.BLUE.r}")

    }

    fun mix(c1: Color, c2: Color) =
            when (setOf(c1, c2)) {
                setOf(Color.RED, Color.YELLOW) -> Color.ORANGE
                setOf(Color.YELLOW, Color.BLUE) -> Color.GREEN
                setOf(Color.BLUE, Color.VIOLET) -> Color.INDIGO

                else -> throw Exception("Dirty color")
            }

    fun eval(e: Expr): Int =
            when (e) {
                is Num -> e.value
                is Sum -> eval(e.left) + eval(e.right)
                else -> throw IllegalArgumentException("Unknown expression")
            }

    fun fizzBuzz(i: Int) = when {
        i % 15 == 0 -> "FizzBuzz"
        i % 3 == 0 -> "Fizz"
        i % 5 == 0 -> "Buzz"

        else -> "  $i"
    }
}
