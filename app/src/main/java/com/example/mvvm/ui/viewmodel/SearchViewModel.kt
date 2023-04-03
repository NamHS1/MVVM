package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.data.enumtype.State
import com.example.mvvm.data.model.search.Search
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.util.Constant
import com.example.mvvm.util.Event
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchViewModel : BaseViewModel() {

    private var _liveData: MutableLiveData<Event<Search>> = MutableLiveData()
    val liveData: LiveData<Event<Search>>
        get() = _liveData

    private var _state: MutableLiveData<Event<State>> = MutableLiveData()
    val state: LiveData<Event<State>>
        get() = _state

    fun favorites(): LiveData<List<Favorite>> = MovieUseCase.getFavorites()

    private var job: Job? = null
    private fun getListSearch(keyword: String?, page: Int = 1) {
        MovieUseCase.getMovieSearch(keyword = keyword, page = page).fetchData(
            success = {
                if (it.movies.isEmpty()) {
                    _state.value = Event(State.ERROR)
                }
                _liveData.value = Event(it)
            },
            error = {

            },
            state = {
                _state.value = Event(it)
            }
        )
    }

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
    }

    private fun insertFavorite(favorite: Favorite) = viewModelScope.launch {
        MovieUseCase.insertFavorite(favorite)
    }

    private fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        MovieUseCase.deleteFavorite(favorite)
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}