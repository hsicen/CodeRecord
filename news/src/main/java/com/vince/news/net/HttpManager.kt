package com.vince.news.net

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.vince.news.BuildConfig
import com.vince.news.constant.BoreTest
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/**
 * <p>作者：黄思程  2017/11/17 16:27
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：Retrofit网络请求封装类
 */
class HttpManager {

    /*** 静态属性和方法定义*/
    companion object {
        var mRetrofit: Retrofit? = null

        /*** new一个单例*/
        fun newInstance(): Retrofit {
            val mHttpClient = getHttpClient()

            mRetrofit = Retrofit.Builder()
                    .baseUrl(ApiStore.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(mHttpClient)
                    .build()

            return mRetrofit!!
        }

        /*** 获取HttpClient*/
        private fun getHttpClient(): OkHttpClient {
            val builder = OkHttpClient.Builder()

            val interceptor = ParamsInterceptor.Builder()
                    .addHeaderParamsMap(addHeader())
                    .addParamsMap(addBody())
                    .build()

            builder.addInterceptor(interceptor)

            // Log信息拦截器
            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                //设置 Debug Log 模式
                builder.addInterceptor(loggingInterceptor)
            }

            //设置超时和重连
            builder.connectTimeout(15, TimeUnit.SECONDS)
            builder.readTimeout(20, TimeUnit.SECONDS)
            builder.writeTimeout(20, TimeUnit.SECONDS)

            //错误重连
            builder.retryOnConnectionFailure(true)
            return if (BuildConfig.DEBUG) {
                builder.addNetworkInterceptor(StethoInterceptor())
                        .build()
            } else {
                builder.build()
            }
        }

        /*** 在该方法中添加统一的头信息*/
        fun addHeader(): MutableMap<String, String> {
            val property: MutableMap<String, String> = mutableMapOf()

            if (BoreTest.isUnitTest) {
                /*property.put()*/
            } else {
                /*property.put()*/
            }

            return property
        }

        /*** 在该方法中添加统一的body信息*/
        fun addBody(): MutableMap<String, String> {
            val property: MutableMap<String, String> = mutableMapOf()

            if (BoreTest.isUnitTest) {
                /*property.put()*/
            } else {
                /*property.put()*/
            }

            return property
        }

        /*** 置空retrofit*/
        fun clear() {
            mRetrofit = null
        }
    }
}