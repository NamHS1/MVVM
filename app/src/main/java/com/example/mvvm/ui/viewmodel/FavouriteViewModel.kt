package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.usecase.MovieUseCase
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.ui.view.adapter.FavoriteAdapter

class FavouriteViewModel : BaseViewModel() {
    private var _liveData: MutableLiveData<List<MovieDetail>> = MutableLiveData<List<MovieDetail>>()
    val liveData: LiveData<List<MovieDetail>>
        get() = _liveData

    fun fetchData() {
        MovieUseCase.getListFavorite().apply {
            _liveData.value = this
        }
    }

    fun removeFavorite(id: Int, adapter: FavoriteAdapter, position: Int) {
        MovieUseCase.removeFavorite(id)
        adapter.movies.removeAt(position)
        adapter.notifyItemRemoved(position)
        adapter.notifyItemRangeChanged(position, adapter.movies.size)

        if (adapter.movies.isEmpty()) {
            fetchData()
        }
    }
}