package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.ui.adapter.FavoriteAdapter
import kotlinx.coroutines.launch

class FavouriteViewModel(
    private val movieUseCase: MovieUseCase = MovieUseCase.getInstance()
) : BaseViewModel() {
    fun favorites(): LiveData<List<Favorite>> = movieUseCase.getFavorites()

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        movieUseCase.deleteFavorite(favorite)
    }
}