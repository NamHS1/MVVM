package com.example.mvvm.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mvvm.data.database.entity.Favorite

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insert(data: Favorite)

    @Delete
    suspend fun delete(data: Favorite)

    @Query("SELECT * FROM Favorite")
    fun getFavorites(): LiveData<List<Favorite>>

    @Query("SELECT * FROM Favorite WHERE id = :id")
    fun getFavorite(id: Int): LiveData<Favorite>

}