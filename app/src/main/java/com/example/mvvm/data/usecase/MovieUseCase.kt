package com.example.mvvm.data.usecase

import com.example.mvvm.data.enum.MovieType
import com.example.mvvm.data.mapper.NowPlayingMapper
import com.example.mvvm.data.mapper.PopularMapper
import com.example.mvvm.data.mapper.UpComingMapper
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.model.Results
import com.example.mvvm.data.model.home.NowPlaying
import com.example.mvvm.data.model.home.Popular
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.data.repository.MovieRepository
import com.example.mvvm.data.repository.PrefRepository
import com.example.mvvm.extension.orEmpty
import com.example.mvvm.util.Constant
import io.reactivex.Observable

object MovieUseCase {

    private val nowPlayingMapper: NowPlayingMapper = NowPlayingMapper()
    private val upComingMapper: UpComingMapper = UpComingMapper()
    private val popularMapper: PopularMapper = PopularMapper()

    private fun getMovie(
        type: MovieType,
        page: Int,
        keyWord: String? = null
    ): Observable<Results> = when (type) {
        MovieType.NOW_PLAYING -> MovieRepository.getNowPlayingMovies(page)
        MovieType.POPULAR -> MovieRepository.getPopularMovies(page)
        MovieType.SEARCH -> MovieRepository.searchMovie(keyWord.orEmpty(), page)
        else -> MovieRepository.getUpComingMovies(page)
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

    fun getMovieDetail(id: Int): Observable<MovieDetail> {
        return MovieRepository.getMovieDetail(id)
    }

    fun getMovieSearch(
        type: MovieType = MovieType.SEARCH,
        keyWord: String? = null,
        page: Int,
    ): Observable<Results> {
        return getMovie(type, page, keyWord)
    }


    fun getPagePopular(popular: Popular?): Int {
        return popular?.let {
            if (it.page < it.totalPage) {
                it.page + 1
            } else {
                it.page
            }
        } ?: 1
    }

    fun getPageNowPlaying(nowPlaying: NowPlaying?): Int {
        return nowPlaying?.let {
            if (it.page < it.totalPage) {
                it.page + 1
            } else {
                it.page
            }
        } ?: 1
    }

    fun getListFavorite(): List<MovieDetail> = PrefRepository.getListMovie(Constant.PREF_FAVORITE).orEmpty()

    fun isFavorite(id: Int): Boolean {
        val listHistory: List<MovieDetail> = PrefRepository.getListMovie(Constant.PREF_FAVORITE).orEmpty()
        listHistory.find { it.id == id }.also {
           return it != null
        }
    }
    fun addFavorite(movieDetail: MovieDetail) = PrefRepository.apply {
        val listHistory: MutableList<MovieDetail> = getListMovie(Constant.PREF_FAVORITE).orEmpty()
        if (listHistory.isEmpty()) {
            listHistory.add(movieDetail)
        } else {
            listHistory.find { it.id == movieDetail.id }.also {
                if (it == null) {
                    listHistory.add(movieDetail)
                }
            }
        }
        putListMovie(Constant.PREF_FAVORITE, listHistory)
    }

    fun removeFavorite(id: Int) = PrefRepository.apply {
        val listHistory: MutableList<MovieDetail> = getListMovie(Constant.PREF_FAVORITE).orEmpty()
        listHistory.find { it.id == id }.also {
            it?.let {
                listHistory.remove(it)
            }
        }
        putListMovie(Constant.PREF_FAVORITE, listHistory)
    }

    fun getHistory(): List<MovieDetail> = PrefRepository.getListMovie(Constant.PREF_HISTORY).orEmpty()

    fun addHistory(movieDetail: MovieDetail) = PrefRepository.apply {
        val listHistory: MutableList<MovieDetail> = getListMovie(Constant.PREF_HISTORY).orEmpty()
        if (listHistory.isEmpty()) {
            listHistory.add(movieDetail)
        } else {
            listHistory.find { it.id == movieDetail.id }.also {
                if (it == null) {
                    listHistory.add(movieDetail)
                }
            }
        }
        putListMovie(Constant.PREF_HISTORY, listHistory)
    }

    fun clearAllHistory() = PrefRepository.removeListMovie(Constant.PREF_HISTORY)
}