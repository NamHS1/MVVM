package com.example.mvvm.data.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvm.data.database.dao.FavoriteDao
import com.example.mvvm.data.database.entity.Favorite

@Database(entities = [Favorite::class], version = 1)
abstract class DataBase : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteDao
}