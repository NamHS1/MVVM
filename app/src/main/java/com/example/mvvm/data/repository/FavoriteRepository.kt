package com.example.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvm.data.database.entity.Favorite

interface FavoriteRepository {
    suspend fun insert(favorite: Favorite)

    suspend fun delete(favorite: Favorite)

    fun getFavorites(): LiveData<List<Favorite>>

    fun getFavorite(id: Int): LiveData<Favorite>
}