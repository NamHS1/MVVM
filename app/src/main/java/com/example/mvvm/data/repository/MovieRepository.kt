package com.example.mvvm.data.repository

import com.example.mvvm.data.api.ApiConfig.apiService
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.model.Results
import io.reactivex.Observable

object MovieRepository {
    fun getPopularMovies(page: Int): Observable<Results> = apiService.getPopularMovies(page)
    fun getUpComingMovies(page: Int): Observable<Results> = apiService.getUpComingMovies(page)
    fun getNowPlayingMovies(page: Int): Observable<Results> = apiService.getNowPlayingMovies(page)
    fun getMovieDetail(id: Int): Observable<MovieDetail> = apiService.getMovieDetail(id)
    fun searchMovie(query: String, page: Int): Observable<Results> = apiService.searchMovie(query, page)
}