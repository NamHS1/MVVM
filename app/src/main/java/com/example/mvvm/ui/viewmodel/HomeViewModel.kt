package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.enum.State
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.data.model.home.NowPlaying
import com.example.mvvm.data.model.home.Popular
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.data.usecase.MovieUseCase

class HomeViewModel(
    private val moviesUseCase: MovieUseCase = MovieUseCase(),
    private var _moviesNowPlaying: MutableLiveData<NowPlaying> = MutableLiveData<NowPlaying>(),
    private var _moviesUpComing: MutableLiveData<UpComing> = MutableLiveData<UpComing>(),
    private var _moviesPopular: MutableLiveData<Popular> = MutableLiveData<Popular>(),
    private var _stateNowPlaying: MutableLiveData<State> = MutableLiveData<State>(),
    private var _stateUpComing: MutableLiveData<State> = MutableLiveData<State>(),
    private var _statePopular: MutableLiveData<State> = MutableLiveData<State>(),
) : BaseViewModel() {

    val moviesNowPlaying: LiveData<NowPlaying> = _moviesNowPlaying
    val moviesUpComing: LiveData<UpComing> = _moviesUpComing
    val moviesPopular: LiveData<Popular> = _moviesPopular

    val stateNowPlaying: LiveData<State> = _stateNowPlaying
    val stateUpComing: LiveData<State> = _stateUpComing
    val statePopular: LiveData<State> = _statePopular

    init {
        getMoviesNowPlaying()
        getMoviesPopular()
        getMoviesUpComing()
    }

    fun getMoviesNowPlaying() {
        val nowPlaying: NowPlaying? = _moviesNowPlaying.value
        var page = 1
        nowPlaying?.let {
            if (it.page < it.totalPage) {
                page = it.page + 1
            }
        }
        moviesUseCase.getMoviesNowPlaying(page = page).fetchData(
            success = {
                if (it.page < it.totalPage) {
                    _stateNowPlaying.value = State.LOAD_MORE
                }

                _moviesNowPlaying.value = it
            },
            error = {

            },
            state = {
                _stateNowPlaying.value = it
            }
        )
    }

    fun getMoviesPopular() {
        val popular: Popular? = _moviesPopular.value
        var page = 1
        popular?.let {
            if (it.page < it.totalPage) {
                page = it.page + 1
            }
        }
        moviesUseCase.getMoviesPopular(page = page).fetchData(
            success = {
                if (it.page < it.totalPage) {
                    _statePopular.value = State.LOAD_MORE
                }
                _moviesPopular.value = it
            },
            error = {

            },
            state = {
                _statePopular.value = it
            }
        )
    }

    fun getMoviesUpComing(page: Int = 1) {
        moviesUseCase.getMoviesUpComing(page = page).fetchData(
            success = {
                _moviesUpComing.value = it
            },
            error = {

            },
            state = {
                _stateUpComing.value = it
            }
        )
    }
}