package com.example.mvvm.data.service

import com.example.mvvm.data.model.Results
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("movie/popular")
    fun getPopularMovies(@Query("page") page: Int): Observable<Results>

    @GET("movie/upcoming")
    fun getUpComingMovies(@Query("page") page: Int): Observable<Results>

    @GET("movie/now_playing")
    fun getNowPlayingMovies(@Query("page") page: Int): Observable<Results>

//    @GET("movie/{movie_id}")
//    fun getMovieDetail(@Path("movie_id") movieId: Int): Observable<MovieDetailReponse>

    @GET("movie/{movie_id}/recommendations")
    fun getRecommendationMovies(@Path("movie_id") movieId: Int): Observable<Results>

    @GET("search/movie")
    fun searchMovie(@Query("query") query: String, @Query("page") page: Int): Observable<Results>
}