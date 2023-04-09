package com.example.mvvm.data.repository

import androidx.lifecycle.LiveData
import com.example.mvvm.data.database.dao.FavoriteDao
import com.example.mvvm.data.database.entity.Favorite

class FavoriteRepositoryImpl(
    private val favoriteDao: FavoriteDao
) : FavoriteRepository {

    override suspend fun insert(favorite: Favorite) = favoriteDao.insert(favorite)

    override suspend fun delete(favorite: Favorite) = favoriteDao.delete(favorite)

    override fun getFavorites(): LiveData<List<Favorite>> = favoriteDao.getFavorites()

    override fun getFavorite(id: Int): LiveData<Favorite> = favoriteDao.getFavorite(id)

}