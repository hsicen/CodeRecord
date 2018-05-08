package com.vince.news.entity

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * <p>作者：黄思程  2017/11/16 10:38
 * <p>邮箱：codinghuang@163.com
 * <p>作用：
 * <p>描述：新闻列表实体类
 */
object DataClass : Serializable {

    data class DataSource(
            @SerializedName("page") val page: Int,
            @SerializedName("total_results") val totalResults: Int,
            @SerializedName("total_pages") val totalPages: Int,
            @SerializedName("results") val movies: List<Movie>
    ) : Serializable

    data class Movie(@SerializedName("id") val id: String,
                     @SerializedName("title") val title: String,
                     @SerializedName("overview") val overView: String,
                     @SerializedName("poster_path") val posterPath: String,
                     @SerializedName("release_date") val releaseDate: String,
                     @SerializedName("vote_average") val voteAverage: Float
    ) : Serializable

    fun convertUrl(posterPath: String?): String =
            "https://image.tmdb.org/t/p/w185_and_h278_bestv2$posterPath"
}
