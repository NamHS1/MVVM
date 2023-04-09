package com.example.mvvm.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.paging.rxjava2.observable
import com.example.mvvm.data.enumtype.MovieType
import com.example.mvvm.data.mapper.MovieMapper
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.pagingsource.MoviePagingSource
import com.example.mvvm.data.service.ApiService
import com.example.mvvm.ui.model.MovieResponse
import io.reactivex.Observable

private const val NETWORK_PAGE_SIZE = 20

class MovieRepositoryImpl(
    private val apiService: ApiService
) : MovieRepository {

    override fun getMovieList(
        type: MovieType,
        mapper: MovieMapper,
        keyword: String?
    ): Observable<PagingData<MovieResponse>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                MoviePagingSource(
                    apiService = apiService,
                    mapper = mapper,
                    type = type,
                    keyword = keyword
                )
            }
        ).observable
    }

    override fun getMovieDetail(id: Int): Observable<MovieDetail> = apiService.getMovieDetail(id)

}