package com.example.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvm.data.database.DBConfig
import com.example.mvvm.data.database.entity.Favorite

object FavoriteLocalRepository {

    suspend fun insert(favorite: Favorite) = DBConfig.favoriteDao.insert(favorite)

    suspend fun delete(favorite: Favorite) = DBConfig.favoriteDao.delete(favorite)

    fun getFavorites(): LiveData<List<Favorite>> = DBConfig.favoriteDao.getFavorites()

    fun getFavorite(id: Int): LiveData<Favorite> = DBConfig.favoriteDao.getFavorite(id)

}