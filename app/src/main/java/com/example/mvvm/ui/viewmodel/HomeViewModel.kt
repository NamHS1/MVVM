package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.ui.model.MovieResponse
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.util.Constant
import com.example.mvvm.util.Event
import kotlinx.coroutines.*

class HomeViewModel(
    private val movieUseCase: MovieUseCase = MovieUseCase.getInstance()
) : BaseViewModel() {

    init {
        getMoviesNowPlaying()
        getMoviesPopular()
        getMoviesUpComing()
    }

    private var jobAutoScroll: Job? = null
    private var hasBeenHandled = false
    var indexAutoScroll: Int = 0
    var bannerSize: Int = 0
        set(value) {
            field = value
            hasBeenHandled = true
            startAutoScroll()
        }

    private var _autoScroll: MutableLiveData<Event<Int>> = MutableLiveData<Event<Int>>()
    val autoScroll: LiveData<Event<Int>>
        get() = _autoScroll

    fun startAutoScroll() {
        if (hasBeenHandled) {
            jobAutoScroll = viewModelScope.launch {
                delay(Constant.DELAY_TIME)
                if (bannerSize > 0 && (indexAutoScroll == bannerSize - 1)) {
                    indexAutoScroll = 0
                } else {
                    indexAutoScroll++
                }
                _autoScroll.value = Event(indexAutoScroll)
            }
            hasBeenHandled = false
        }
    }

    fun cancelAutoScroll() {
        jobAutoScroll?.cancel()
        hasBeenHandled = true
    }

    private var _moviesNowPlaying: MutableLiveData<Event<PagingData<MovieResponse>>> =
        MutableLiveData<Event<PagingData<MovieResponse>>>()
    val moviesNowPlaying: LiveData<Event<PagingData<MovieResponse>>>
        get() = _moviesNowPlaying

    private fun getMoviesNowPlaying() {
        movieUseCase.getNowPlayingMovie().fetchData(
            success = {
                _moviesNowPlaying.value = Event(it)
            },
            error = {},
            state = {
            }
        )
    }

    private var _moviesPopular: MutableLiveData<Event<PagingData<MovieResponse>>> =
        MutableLiveData<Event<PagingData<MovieResponse>>>()
    val moviesPopular: LiveData<Event<PagingData<MovieResponse>>>
        get() = _moviesPopular

    private fun getMoviesPopular() {
        movieUseCase.getPopularMovie().fetchData(
            success = {
                _moviesPopular.value = Event(it)
            },
            error = {},
            state = {
            }
        )
    }

    private var _moviesUpComing: MutableLiveData<Event<PagingData<MovieResponse>>> =
        MutableLiveData<Event<PagingData<MovieResponse>>>()
    val moviesUpComing: LiveData<Event<PagingData<MovieResponse>>>
        get() = _moviesUpComing

    private fun getMoviesUpComing() {
        movieUseCase.getUpComingMovie().fetchData(
            success = {
                _moviesUpComing.value = Event(it)
            },
            error = {},
            state = {
            }
        )
    }


//    fun getMovie() {
//        Observable.zip(
//            movieUseCase.getUpComingMovie(),
//            movieUseCase.getNowPlayingMovie(),
//            movieUseCase.getPopularMovie(),
//            MovieCombine()
//        ).fetchData(
//            success = {
//                _homeLiveData.value = Event(it)
//            },
//            error = {
//
//            },
//            state = {
//                _homeNetworkState.value = Event(it)
//            }
//        )
//    }

    override fun onCleared() {
        super.onCleared()
        cancelAutoScroll()
    }
}