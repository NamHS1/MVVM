package com.example.mvvm.usecase

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.example.mvvm.data.api.ApiConfig
import com.example.mvvm.data.database.DBConfig
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.MovieType
import com.example.mvvm.data.mapper.*
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.preference.PrefConfig
import com.example.mvvm.data.repository.*
import com.example.mvvm.extension.orEmpty
import com.example.mvvm.ui.model.MovieResponse
import com.example.mvvm.util.Constant
import io.reactivex.Observable

class MovieUseCase private constructor() {

    private object Holder {
        val instance = MovieUseCase()
    }

    companion object {
        @JvmStatic
        fun getInstance() = Holder.instance
    }

    private val movieMapper: MovieMapper = MovieMapper()

    private val favoriteRepository: FavoriteRepository = FavoriteRepositoryImpl(
        favoriteDao = DBConfig().favoriteDao
    )

    private val prefRepository: PrefRepository = PrefRepositoryImpl(
        prefService = PrefConfig().prefService
    )

    private val movieRepository = MovieRepositoryImpl(
        apiService = ApiConfig().apiService
    )

    fun getMovieDetail(id: Int): Observable<MovieDetail> {
        return movieRepository.getMovieDetail(id)
    }

    fun getMovieSearch(
        keyword: String
    ): Observable<PagingData<MovieResponse>> = movieRepository.getMovieList(
        keyword = keyword,
        mapper = movieMapper,
        type = MovieType.SEARCH
    )

    fun getPopularMovie(): Observable<PagingData<MovieResponse>> = movieRepository.getMovieList(
        mapper = movieMapper,
        type = MovieType.POPULAR
    )

    fun getNowPlayingMovie(): Observable<PagingData<MovieResponse>> = movieRepository.getMovieList(
        mapper = movieMapper,
        type = MovieType.NOW_PLAYING
    )

    fun getUpComingMovie(): Observable<PagingData<MovieResponse>> = movieRepository.getMovieList(
        mapper = movieMapper,
        type = MovieType.UPCOMING
    )

    fun getFavorites(): LiveData<List<Favorite>> = favoriteRepository.getFavorites()

    fun getFavorite(id: Int): LiveData<Favorite> = favoriteRepository.getFavorite(id)
    suspend fun insertFavorite(favorite: Favorite) = favoriteRepository.insert(favorite)

    suspend fun deleteFavorite(favorite: Favorite) = favoriteRepository.delete(favorite)

    fun getHistory(): List<MovieDetail> =
        prefRepository.getListMovie(Constant.PREF_HISTORY).orEmpty().reversed()

    fun addHistory(movieDetail: MovieDetail) = prefRepository.apply {
        val listHistory: MutableList<MovieDetail> = getListMovie(Constant.PREF_HISTORY).orEmpty()
        if (listHistory.isEmpty()) {
            listHistory.add(movieDetail)
        } else {
            val movieFirst: MovieDetail = listHistory.last()
            if (movieFirst.id != movieDetail.id) {
                listHistory.add(movieDetail)
            }
        }
        putListMovie(Constant.PREF_HISTORY, listHistory)
    }

    fun clearAllHistory() = prefRepository.removeListMovie(Constant.PREF_HISTORY)
}