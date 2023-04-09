package com.example.mvvm.data.repository

import androidx.paging.*
import com.example.mvvm.data.enumtype.MovieType
import com.example.mvvm.data.mapper.MovieMapper
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.ui.model.MovieResponse
import io.reactivex.Observable

interface MovieRepository {
    fun getMovieList(
        type: MovieType,
        mapper: MovieMapper,
        keyword: String? = null
    ): Observable<PagingData<MovieResponse>>

    fun getMovieDetail(id: Int) : Observable<MovieDetail>
}