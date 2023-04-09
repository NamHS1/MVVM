package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.NetworkState
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.ui.model.MovieResponse
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.util.Constant
import com.example.mvvm.util.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel(
    private val movieUseCase: MovieUseCase = MovieUseCase.getInstance()
) : BaseViewModel() {

    fun favorites(): LiveData<List<Favorite>> = MutableLiveData()

    private var _liveData: MutableLiveData<Event<PagingData<MovieResponse>>> = MutableLiveData()
    val liveData: LiveData<Event<PagingData<MovieResponse>>>
        get() = _liveData

    private var _networkState: MutableLiveData<Event<NetworkState>> = MutableLiveData()
    val networkState: LiveData<Event<NetworkState>>
        get() = _networkState
    private fun getListSearch(keyword: String) {
        movieUseCase.getMovieSearch(keyword = keyword).fetchData(
            success = {
                _liveData.value = Event(it)
            },
            error = {

            },
            state = {
                _networkState.value = Event(it)
            }
        )
    }

    private var job: Job? = null
    fun changeTextSearch(keyword: String?) {
        job?.cancel()
        if (!keyword.isNullOrEmpty()) {
            job = viewModelScope.launch {
                delay(Constant.DELAY_REQUEST)
                getListSearch(keyword)
            }
        }
    }

    fun handFavorite(favorite: Favorite, isCheck: Boolean) {
        if (isCheck) {
            insertFavorite(favorite)
        } else {
            deleteFavorite(favorite)
        }
        favorites()
    }

    private fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        movieUseCase.insertFavorite(favorite)
    }

    private fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        movieUseCase.deleteFavorite(favorite)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}