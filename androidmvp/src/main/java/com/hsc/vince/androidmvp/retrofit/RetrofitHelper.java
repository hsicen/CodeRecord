package com.hsc.vince.androidmvp.retrofit;

import com.hsc.vince.androidmvp.net.ApiStore;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * <p>作者：黄思程  2018/4/13 11:47
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：获取Retrofit实例
 * <p>
 * 使用时需要修改BaseUrl
 */
public class RetrofitHelper {
    private static volatile Retrofit sRetrofit;
    private static volatile RetrofitHelper sHelper;

    private RetrofitHelper() {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    return chain.proceed(request);
                })
                .retryOnConnectionFailure(true)
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .build();

        sRetrofit = new Retrofit.Builder()
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(ApiStore.API_SERVICE_URL)
                .build();
    }

    private static RetrofitHelper getIns() {
        if (sHelper == null) {
            synchronized (RetrofitHelper.class) {
                if (sHelper == null) {
                    sHelper = new RetrofitHelper();
                }
            }
        }
        return sHelper;
    }

    public static <T> T create(Class<T> apiService) {

        return sHelper.getIns().sRetrofit.create(apiService);
    }
}
