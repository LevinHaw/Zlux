package com.submission.core.data.datasource.remote.retrofit


import com.submission.core.BuildConfig
import com.submission.core.data.datasource.remote.response.DetailPopularMovieResponse
import com.submission.core.data.datasource.remote.response.ListPopularMovieResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular?language=en-US&page=1")
    suspend fun getPopularMovies(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
    ): ListPopularMovieResponse

    @GET("movie/{movie_id}")
    suspend fun getDetailMovie(
        @Path("movie_id") movieId: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): DetailPopularMovieResponse
}