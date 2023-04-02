package com.example.mvvm.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.mapper.FavoriteMapper
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.util.Constant
import kotlinx.coroutines.launch

class MovieDetailViewModel : BaseViewModel() {

    private var _liveData: MutableLiveData<MovieDetail> = MutableLiveData<MovieDetail>()
    val liveData: LiveData<MovieDetail>
        get() = _liveData

    private var _stateMovieDetail: MutableLiveData<State> = MutableLiveData<State>()
    val stateMovieDetail: LiveData<State>
        get() = _stateMovieDetail

    private var _id: Int = 0
        set(value) {
            field = value
            getMovieDetail(value)
        }
    val id: Int
        get() = _id

    fun favorite(): LiveData<Favorite> = MovieUseCase.getFavorite(id)
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

    fun handleFavorite(isChecked: Boolean) {
        _liveData.value?.let {
            if (isChecked) {
                insertFavorite(it)
            } else {
                deleteFavorite(it)
            }
        }
        // update favorite state
        favorite()
    }

    fun addHistory(movieDetail: MovieDetail) = MovieUseCase.addHistory(movieDetail)

    private fun insertFavorite(movieDetail: MovieDetail) = viewModelScope.launch {
        MovieUseCase.insertFavorite(FavoriteMapper.mapFrom(movieDetail))
    }

    private fun deleteFavorite(movieDetail: MovieDetail) = viewModelScope.launch {
        MovieUseCase.deleteFavorite(FavoriteMapper.mapFrom(movieDetail))
    }
}