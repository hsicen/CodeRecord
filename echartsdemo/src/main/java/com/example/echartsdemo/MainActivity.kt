package com.example.echartsdemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

/**
 * <p>作者：黄思程  2018/6/1  11:44
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：EChart测试
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewLine()
        initViewPie()
    }

    private fun loadLine() {
        val labels = listOf("阅读邮件 ", "回复邮件", "网站搜索", "博客线索", "Facebook", "Twitter   ", "Linkedin")
        val readEmail = listOf(1, 1, 4, 6, 4, 2, 4, 4, 5, 5, 3, 3, 1, 4, 5, 5, 5, 3, 3, 3, 2, 2, 3, 4, 4)
        val backEmail = listOf(0, 0, 1, 0, 0, 0, 2, 2, 1, 3, 0, 0, 0, 0, 3, 2, 2, 0, 0, 0, 1, 1, 0, 0, 0)
        val netSearch = listOf(4, 4, 0, 1, 1, 1, 0, 0, 0, 0, 9, 9, 9, 9, 7, 7, 7, 5, 5, 5, 0, 2, 3, 0, 0)
        val blogLead = listOf(3, 3, 5, 5, 7, 7, 7, 7, 7, 7, 6, 3, 2, 0, 0, 0, 1, 1, 1, 8, 8, 7, 5, 5)
        val faceBook = listOf(30, 10, 36, 20, 8, 5, 5, 5, 5, 6, 6, 1, 2, 3, 4, 5, 4, 3, 2, 1, 0, 0, 7, 7, 7)
        val twitter = listOf(6, 8, 30, 20, 10, 20, 23, 21, 7, 23, 12, 11, 30, 6, 6, 9, 9, 9, 8, 1, 1, 1, 0, 5)
        val linkedIn = listOf(5, 5, 6, 4, 5, 5, 6, 7, 8, 8, 9, 9, 5, 5, 5, 5, 7, 12, 6, 6, 6, 6, 6, 2, 2)
        val lineBean = Gson().toJson(LineBean(labels, readEmail, backEmail, netSearch,
                blogLead, faceBook, twitter, linkedIn))

        Log.d("pieData", lineBean)

        webLineChart.loadUrl("javascript:createChart('line','$lineBean');")
    }

    private fun loadPie() {
        val piePercent = listOf(0.5, 0.4, 0.44, 0.24, 0.26, 0.16)
        val pieTitle = listOf("邮件", "电话", "社交媒体", "网站", "博客", "其他")
        val pieBean = Gson().toJson(PieBean(piePercent, pieTitle))

        Log.d("pieData", pieBean)

        webPieChart.loadUrl("javascript:createChart('pie','$pieBean');")
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViewLine() {
        webLineChart.settings.allowFileAccess = true
        webLineChart.settings.defaultTextEncodingName = "utf-8"
        webLineChart.settings.setSupportZoom(true)
        webLineChart.settings.builtInZoomControls = false
        webLineChart.clearCache(true)
        webLineChart.settings.javaScriptEnabled = true
        webLineChart.loadUrl("file:///android_asset/echarts.html")

        webLineChart.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                loadLine()
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initViewPie() {
        webPieChart.settings.allowFileAccess = true
        webPieChart.settings.defaultTextEncodingName = "utf-8"
        webPieChart.settings.setSupportZoom(false)
        webPieChart.settings.builtInZoomControls = false
        webPieChart.clearCache(true)
        webPieChart.settings.javaScriptEnabled = true
        webPieChart.loadUrl("file:///android_asset/echarts.html")

        webPieChart.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                view.loadUrl(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)

                loadPie()
            }
        }
    }
}
