package com.hsc.vince.androidmvp.net;

import android.support.annotation.NonNull;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.hsc.vince.androidmvp.BuildConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * <p>作者：黄思程  2018/4/11 11:53
 * <p>邮箱：codinghuang@163.com
 * <p>作用：Retrofit
 * <p>描述：网络请求单例实现
 */
public class NetClient {
    private static volatile Retrofit mInstance = null;
    private static OkHttpClient mHttpClient = null;

    NetClient() {
        throw new Error("illegle operation!");
    }

    /*** Retrofit 单例实现
     * @return Retrofit单例*/
    public static Retrofit newInstance() {
        if (null == mInstance) {
            synchronized (Retrofit.class) {
                if (mInstance == null) {

                    mHttpClient = getOkHttpClient();
                    mInstance = new Retrofit.Builder()
                            .baseUrl(ApiStore.API_SERVICE_URL)
                            .addConverterFactory(DecodeConvertFactory.create())
                            //.addConverterFactory(GsonConverterFactory.create())
                            .addConverterFactory(ScalarsConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(mHttpClient)
                            .build();
                }
            }
        }

        return mInstance;
    }

    /*** 自定义Client
     * @return 自定义Client*/
    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = getBuilder();
        if (BuildConfig.DEBUG) {
            return builder
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        } else {

            return builder.build();
        }
    }

    /***  获取自定义Builder
     * @return 自定义Builder*/
    @NonNull
    private static OkHttpClient.Builder getBuilder() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        //拦截器，添加统一头信息
        BaseParamsInterceptor basicParamsInterceptor =
                new BaseParamsInterceptor.Builder()
                        .addHeaderParamsMap(appHeader())
                        .addParamsMap(appBody())
                        .build();
        builder.addInterceptor(basicParamsInterceptor);

        //调试添加拦截器
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置 Debug Log 模式
            builder.addInterceptor(loggingInterceptor);
        }

        //设置超时和重连
        builder.connectTimeout(15, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        return builder;
    }

    /*** 添加统一头信息
     * @return 头信息*/
    static Map<String, String> appHeader() {
        Map<String, String> property = new HashMap<>();
        //property.put("key", "value")
        //property.put("key", "value")
        //property.put("key", "value")
        return property;
    }

    /*** 统一的body
     *  @return body信息*/
    public static Map<String, String> appBody() {
        Map<String, String> preperty = new HashMap<>();

        /*preperty.put("app_lang", "zh_cn");
        preperty.put("source", "android");
        preperty.put("sn", "");
        preperty.put("version", BuildConfig.VERSION_NAME);
        preperty.put("device_type", android.os.Build.BRAND);
        preperty.put("device_version", android.os.Build.MODEL);
        preperty.put("device_system", android.os.Build.VERSION.SDK_INT + "");*/
        return preperty;
    }
}
