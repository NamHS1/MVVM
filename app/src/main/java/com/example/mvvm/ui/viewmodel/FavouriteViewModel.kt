package com.example.mvvm.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvm.data.database.entity.Favorite
import com.example.mvvm.usecase.MovieUseCase
import com.example.mvvm.ui.base.BaseViewModel
import com.example.mvvm.ui.view.adapter.FavoriteAdapter
import kotlinx.coroutines.launch

class FavouriteViewModel : BaseViewModel() {
    fun favorites(): LiveData<List<Favorite>> = MovieUseCase.getFavorites()

    fun deleteFavorite(favorite: Favorite) = viewModelScope.launch {
        MovieUseCase.deleteFavorite(favorite)
    }
}