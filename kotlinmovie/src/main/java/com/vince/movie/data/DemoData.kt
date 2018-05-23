package com.vince.movie.data

import com.google.gson.annotations.SerializedName
import java.io.Serializable


/**
 * <p>作者：黄思程  2017/11/11 0:36
 * <p>邮箱：huangsicheng@gouuse.cn
 * <p>作用：
 * <p>描述：HelloKotlin
 */
class DemoData {

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
}