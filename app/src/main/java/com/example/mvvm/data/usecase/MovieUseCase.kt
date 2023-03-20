package com.example.mvvm.data.usecase

import com.example.mvvm.data.enum.MovieType
import com.example.mvvm.data.mapper.NowPlayingMapper
import com.example.mvvm.data.mapper.PopularMapper
import com.example.mvvm.data.mapper.UpComingMapper
import com.example.mvvm.data.model.Results
import com.example.mvvm.data.model.home.NowPlaying
import com.example.mvvm.data.model.home.Popular
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.data.repository.MovieRepository
import io.reactivex.Observable

class MovieUseCase(
    private val movieRepository: MovieRepository = MovieRepository(),
    private val nowPlayingMapper: NowPlayingMapper = NowPlayingMapper(),
    private val upComingMapper: UpComingMapper  = UpComingMapper(),
    private val popularMapper: PopularMapper = PopularMapper()
) {

    private fun getMovie(
        type: MovieType,
        page: Int,
        keyWord: String? = null
    ): Observable<Results> = when (type) {
        MovieType.NOW_PLAYING -> movieRepository.getNowPlayingMovies(page)
        MovieType.POPULAR -> movieRepository.getPopularMovies(page)
        MovieType.SEARCH -> movieRepository.searchMovie(keyWord.orEmpty(), page)
        else -> movieRepository.getUpComingMovies(page)
    }

    fun getMoviesNowPlaying(
        type: MovieType = MovieType.NOW_PLAYING,
        page: Int
    ): Observable<NowPlaying> {
        return getMovie(type, page).map {
            nowPlayingMapper.mapFrom(it)
        }
    }

    fun getMoviesPopular(
        type: MovieType = MovieType.POPULAR,
        page: Int
    ): Observable<Popular> {
        return getMovie(type, page).map {
            popularMapper.mapFrom(it)
        }
    }

    fun getMoviesUpComing(
        type: MovieType = MovieType.UPCOMING,
        page: Int
    ): Observable<UpComing> {
        return getMovie(type, page).map {
            upComingMapper.mapFrom(it)
        }
    }

    fun getMovieSearch(
        type: MovieType = MovieType.SEARCH,
        keyWord: String? = null,
        page: Int,
    ): Observable<Results> {
        return getMovie(type, page, keyWord)
    }

}