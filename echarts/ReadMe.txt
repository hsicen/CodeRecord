echarts  官网下了一堆js,无语了，难道要用webview 加载？一百度，果不其然
 android 中图表展示的实现——echarts方式实现 http://blog.csdn.net/huozhonbin/article/details/44781867

 关于在Android Studio中使用Assets目录下的资源的问题 http://blog.csdn.net/shifuhetudi/article/details/45006605 是直读取文件，我要的是路径

 手把手教你用android studio创建第一个安卓程序加载html5页面 http://www.codes51.com/article/detail_130962.html
 上面这篇很详细呀，原来是我没有开网！！！
android中webview控件和javascript交互实例 http://www.jb51.net/article/52031.htm
但是不交互。
WebView中的JavaScript为什么不执行呢？ http://q.cnblogs.com/q/47060/  好像不足以解决，因为那个方法不存在啦
额 参考这个 http://www.tuicool.com/articles/2IVfMbf
再使用官方的例子，先在电脑端测试，总算是勉强弄出来啦 具体在test里
一共可以使用的是这些图
'line'，'bar','scatter','k','pie','radar','chord','force''map'，'gauge','funnel'

createLineChart(createBarChart(createCatterChart(createKChart(createPieChart
createRadarChart(createChordChart(createForceChart(createMapChart(createGaugeChart(createFunnelChart);

只能说源码有问题，删掉所有一下这些就好啦
color: (function (){
    var zrColor = require('zrender/tool/color');
    return zrColor.getLinearGradient(
        0, 0, 1000, 0,
        [[0, 'rgba(255,0,0,0.8)'],[0.8, 'rgba(255,255,0,0.8)']]
    )
})(),
发现显示的容太多了 ，想缩放
WebView的缩放功能 http://blog.csdn.net/guang564610/article/details/8088433





看着不错，没有仔细验证：
Android之WebView仿微信中图片操作（含二维码识别）http://www.codes51.com/article/detail_276450.html
