package com.example.mvvm.data.service

import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.model.Results
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): Results

    @GET("movie/upcoming")
    suspend fun getUpComingMovies(@Query("page") page: Int): Results

    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(@Query("page") page: Int): Results

    @GET("movie/{movie_id}")
    fun getMovieDetail(@Path("movie_id") movieId: Int): Observable<MovieDetail>

    @GET("search/movie")
    suspend fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Results
}