package com.example.echartsdemo

/**
 * 作者： 黄思程   2018/6/1 15:55
 * 邮箱： codinghuang@163.com
 * 作用：
 * 描述： 数据文件
 */

data class LineBean(
        val labels: List<String>,
        val readEmail: List<Int>,
        val backEmail: List<Int>,
        val netSearch: List<Int>,
        val blogLead: List<Int>,
        val faceBook: List<Int>,
        val twitter: List<Int>,
        val linkedIn: List<Int>)


data class PieBean(
        val percentage: List<Double>,
        val labels: List<String>
)