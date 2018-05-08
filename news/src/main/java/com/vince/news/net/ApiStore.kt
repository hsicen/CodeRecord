package com.vince.news.net

import com.vince.news.entity.DataClass
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import rx.Observable

/**
 * <p>作者：黄思程  2017/11/17 16:26
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：网络请求仓库
 */
interface ApiStore {

    /*** 获取新闻数据*/
    @POST("/3/discover/movie")
    @FormUrlEncoded
    fun requestNewsData(@Field("sort_by") sortBy: String,
                        @Field("api_key") apiKey: String,
                        @Field("page") page: Int): Observable<DataClass.DataSource>

    companion object {
        /*** 基地址*/
        const val baseUrl: String = "http://api.themoviedb.org"
    }
}