package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.data.model.home.NowPlaying
import com.example.mvvm.data.model.home.Popular
import com.example.mvvm.data.model.home.UpComing
import com.example.mvvm.data.usecase.MovieUseCase

class HomeViewModel(
    private val moviesUseCase: MovieUseCase = MovieUseCase(),
    private var _moviesNowPlaying: MutableLiveData<NowPlaying> = MutableLiveData<NowPlaying>(),
    private var _moviesUpComing: MutableLiveData<UpComing> = MutableLiveData<UpComing>(),
    private var _moviesPopular: MutableLiveData<Popular> = MutableLiveData<Popular>()
) : BaseViewModel() {

    val moviesNowPlaying: LiveData<NowPlaying> = _moviesNowPlaying
    val moviesUpComing: LiveData<UpComing> = _moviesUpComing
    val moviesPopular: LiveData<Popular> = _moviesPopular

    override fun initViewModel() {
        getMoviesNowPlaying()
        getMoviesPopular()
        getMoviesUpComing()
    }

    private fun getMoviesNowPlaying(page: Int = 1) {
        moviesUseCase.getMoviesNowPlaying(page = page).fetchData(
            success = {
                _moviesNowPlaying.value = it
            },
            error = {

            }
        )
    }

    private fun getMoviesPopular(page: Int = 1) {
        moviesUseCase.getMoviesPopular(page = page).fetchData(
            success = {
                _moviesPopular.value = it
            },
            error = {

            }
        )
    }

    private fun getMoviesUpComing(page: Int = 1) {
        moviesUseCase.getMoviesUpComing(page = page).fetchData(
            success = {
                _moviesUpComing.value = it
            },
            error = {

            }
        )
    }
}