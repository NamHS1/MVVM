package com.example.mvvm.data.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mvvm.data.enumtype.MovieType
import com.example.mvvm.data.mapper.MovieMapper
import com.example.mvvm.data.service.ApiService
import com.example.mvvm.ui.model.MovieResponse
import com.example.mvvm.util.Constant
import retrofit2.HttpException
import java.io.IOException

class MoviePagingSource(
    private val apiService: ApiService,
    private val mapper: MovieMapper,
    private val type: MovieType,
    private val keyword: String?
) : PagingSource<Int, MovieResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResponse> {
        return try {
            val nextPage = params.key ?: 1
            val response = when (type) {
                MovieType.POPULAR -> {
                    apiService.getPopularMovies(nextPage)
                }
                MovieType.NOW_PLAYING -> {
                    apiService.getNowPlayingMovies(nextPage)
                }
                MovieType.UPCOMING -> {
                    apiService.getUpComingMovies(nextPage)
                }
                MovieType.SEARCH -> {
                    apiService.searchMovie(page = nextPage, query = keyword ?: Constant.EMPTY)
                }
            }
            val movieResponse = mapper.mapFrom(response)
            LoadResult.Page(
                data = movieResponse,
                prevKey = null,
                nextKey = if (nextPage >= (response.totalPages ?: 0) || type == MovieType.UPCOMING)
                    null
                else
                    nextPage.plus(1)
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieResponse>): Int? {
        return null
    }
}