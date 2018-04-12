package com.hsc.vince.androidmvp.net;

import com.hsc.vince.androidmvp.BuildConfig;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * <p>作者：黄思程  2018/4/10 17:02
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：网络请求仓库
 * <p>
 * POST请求时需要添加  @FormUrlEncoded 注解
 * GET 请求不需要添加该注解
 * <p>
 */
public interface ApiStore {
    /*** 加密的key*/
    String APPKEY = "50AC8697E39095A393EA79AE537A06C0";
    /*** 根据gradle配置获取不同环境下的基地址*/
    String API_URL = BuildConfig.SERVICE_BASE_URL;
    /*** Retrofit的基地址*/
    String API_SERVICE_URL = API_URL + "/";

    /***  获取电影列表信息
     * @param startIndex 开始下标
     * @param count      请求条数
     * @return jsonStr   */
    @GET("/v2/movie/top250")
    Flowable<String> requestMovies(
            @Query("start") int startIndex,
            @Query("count") int count);


    /*** get请求
     * @param url  请求地址
     * @param maps 请求参数
     * @return 请求结果json字符串*/
    @GET()
    Flowable<String> get(@Url String url, @QueryMap Map<String, String> maps);

    /*** post请求
     * @param url  请求地址
     * @param maps 请求参数
     * @return 请求结果json字符串*/
    @FormUrlEncoded
    @POST()
    Flowable<String> post(@Url() String url, @FieldMap Map<String, String> maps);

}
