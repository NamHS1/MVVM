package com.example.mvvm.ui.viewmodel

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.NetworkState
import com.example.mvvm.data.mapper.FavoriteMapper
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.util.Constant
import kotlinx.coroutines.launch

class MovieDetailViewModel(
    private val movieUseCase: MovieUseCase = MovieUseCase.getInstance()
) : BaseViewModel() {

    private var _liveData: MutableLiveData<MovieDetail> = MutableLiveData<MovieDetail>()
    val liveData: LiveData<MovieDetail>
        get() = _liveData

    private var _networkState: MutableLiveData<NetworkState> = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private var _id: Int = 0
        set(value) {
            field = value
            getMovieDetail(value)
        }
    val id: Int
        get() = _id

    fun favorite(): LiveData<Favorite> = movieUseCase.getFavorite(id)
    fun getMovieDetail(id: Int) {
        movieUseCase.getMovieDetail(id).fetchData(
            success = {
                _liveData.value = it
            },
            error = {},
            state = {
                _networkState.value = it
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

    fun addHistory(movieDetail: MovieDetail) = movieUseCase.addHistory(movieDetail)

    private fun insertFavorite(movieDetail: MovieDetail) = viewModelScope.launch {
        movieUseCase.insertFavorite(FavoriteMapper.mapFrom(movieDetail))
    }

    private fun deleteFavorite(movieDetail: MovieDetail) = viewModelScope.launch {
        movieUseCase.deleteFavorite(FavoriteMapper.mapFrom(movieDetail))
    }
}