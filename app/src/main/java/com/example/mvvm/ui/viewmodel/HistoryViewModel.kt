package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.ui.base.BaseViewModel

class HistoryViewModel(
    private val movieUseCase: MovieUseCase = MovieUseCase.getInstance()
) : BaseViewModel() {
    private var _liveData: MutableLiveData<List<MovieDetail>> = MutableLiveData<List<MovieDetail>>()
    val liveData: LiveData<List<MovieDetail>>
        get() = _liveData

    fun fetchData() {
        movieUseCase.getHistory().apply {
            _liveData.value = this
        }
    }

    fun clearHistory() {
        movieUseCase.clearAllHistory()
        _liveData.value = emptyList()
    }
}