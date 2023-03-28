package com.example.mvvm.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.enum.State
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.usecase.MovieUseCase
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.util.Constant

class MovieDetailViewModel : BaseViewModel() {

    private var _liveData: MutableLiveData<MovieDetail> = MutableLiveData<MovieDetail>()
    val liveData: LiveData<MovieDetail>
        get() = _liveData

    private var _stateMovieDetail: MutableLiveData<State> = MutableLiveData<State>()
    val stateMovieDetail: LiveData<State>
        get() = _stateMovieDetail

    private var _favorite: MutableLiveData<Boolean> = MutableLiveData()
    val favourite: LiveData<Boolean>
        get() = _favorite

    private var _id: Int = 0
        set(value) {
            field = value
            getMovieDetail(value)
        }
    val id: Int
        get() = _id

    fun getMovieDetail(id: Int) {
        MovieUseCase.getMovieDetail(id).fetchData(
            success = {
                _liveData.value = it
            },
            error = {},
            state = {
                _stateMovieDetail.value = it
            }
        )
    }

    override fun getArguments(bundle: Bundle) {
        _id = bundle.getInt(Constant.ID, 0)
    }

    fun checkFavorite(id: Int) {
        _favorite.value = MovieUseCase.isFavorite(id)
    }

    fun handleFavorite(isChecked: Boolean) {
        if (isChecked) {
            _liveData.value?.let { addFavorite(it) }
        } else {
            removeFavorite(id)
        }

        _favorite.value = isChecked
    }

    fun addHistory(movieDetail: MovieDetail) = MovieUseCase.addHistory(movieDetail)

    private fun addFavorite(movieDetail: MovieDetail) = MovieUseCase.addFavorite(movieDetail)

    private fun removeFavorite(id: Int) = MovieUseCase.removeFavorite(id)
}