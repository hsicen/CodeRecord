package android.cl.com.echarts;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


public class MainActivity extends Activity implements View.OnClickListener {

	private Button linechart_bt;
	private Button barchart_bt;
	private Button piechart_bt;
	private WebView chartshow_wb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_main);
		initView();
	}

	/**
	 * 初始化页面元素
	 */
	private void initView() {

		findViewById(R.id.linechart_bt).setOnClickListener(this);
		findViewById(R.id.barchart_bt).setOnClickListener(this);
		findViewById(R.id.scatterchart_bt).setOnClickListener(this);
		findViewById(R.id.kchart_bt).setOnClickListener(this);
		findViewById(R.id.piechart_bt).setOnClickListener(this);
		findViewById(R.id.radarchart_bt).setOnClickListener(this);
		findViewById(R.id.chordchart_bt).setOnClickListener(this);
		findViewById(R.id.forcechart_bt).setOnClickListener(this);
		findViewById(R.id.mapchart_bt).setOnClickListener(this);
		findViewById(R.id.gaugechart_bt).setOnClickListener(this);
		findViewById(R.id.funnelchart_bt).setOnClickListener(this);
		chartshow_wb = (WebView) findViewById(R.id.chartshow_wb);
		//进行webwiev的一堆设置
		//开启本地文件读取（默认为true，不设置也可以）
		chartshow_wb.getSettings().setAllowFileAccess(true);
		//设置编码
		chartshow_wb.getSettings().setDefaultTextEncodingName("utf-8");
		// 设置可以支持缩放
		chartshow_wb.getSettings().setSupportZoom(true);
		// 设置出现缩放工具
		chartshow_wb.getSettings().setBuiltInZoomControls(true);
		// 清除浏览器缓存
		chartshow_wb.clearCache(true);
		//开启脚本支持
		chartshow_wb.getSettings().setJavaScriptEnabled(true);
		//  放在 assets目录
		//获取Assets目录下的文件
		chartshow_wb.loadUrl("file:///android_asset/echart/echarts.html");
//        chartshow_wb.loadUrl("file:///android_asset/test/echarts.html");
//        chartshow_wb.loadUrl("file:///android_asset/test/index.html");
//        chartshow_wb.loadUrl("http://www.baidu.com");
		//在当前页面打开链接了
		chartshow_wb.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				view.loadUrl(url);
				return true;
			}
		});
		//js加上这个就好啦！
		chartshow_wb.setWebChromeClient(new WebChromeClient() {
			@Override
			public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
				return super.onJsAlert(view, url, message, result);
			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.linechart_bt:
				chartshow_wb.loadUrl("javascript:createChart('line',[]);");
//                chartshow_wb.loadUrl("javascript:createChart('line',[89,78,77]);");
//                chartshow_wb.loadUrl("javascript:wave();");
				break;
			case R.id.barchart_bt:
				chartshow_wb.loadUrl("javascript:createChart('bar',[]);");
//                chartshow_wb.loadUrl("javascript:createChart('bar',[89,78,77]);");
				break;
			case R.id.scatterchart_bt:
				chartshow_wb.loadUrl("javascript:createChart('scatter',[]);");
				break;
			case R.id.kchart_bt:
				chartshow_wb.loadUrl("javascript:createChart('k',[]);");
				break;
			case R.id.piechart_bt:
				chartshow_wb.loadUrl("javascript:createChart('pie',[]);");
				break;
			case R.id.radarchart_bt:
				chartshow_wb.loadUrl("javascript:createChart('radar',[]);");
				break;
			case R.id.chordchart_bt:
				chartshow_wb.loadUrl("javascript:createChart('chord',[]);");
				break;
			case R.id.forcechart_bt:
				chartshow_wb.loadUrl("javascript:createChart('force',[]);");
				break;
			case R.id.mapchart_bt:
				chartshow_wb.loadUrl("javascript:createChart('map',[]);");
				break;
			case R.id.gaugechart_bt:
				chartshow_wb.loadUrl("javascript:createChart('gauge',[]);");
				break;
			case R.id.funnelchart_bt:
				chartshow_wb.loadUrl("javascript:createChart('funnel',[]);");
				break;
			default:
				break;
		}
	}

	@Override
//设置回退 在页面内回退
//覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && chartshow_wb.canGoBack()) {
			chartshow_wb.goBack(); //goBack()表示返回WebView的上一页面
			return true;
		}
//        finish();//结束退出程序
//        return false;
		return super.onKeyDown(keyCode, event);
	}


}
