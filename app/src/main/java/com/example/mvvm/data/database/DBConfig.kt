package com.example.mvvm.data.database

import androidx.room.Room
import com.example.mvvm.MovieApplication
import com.example.mvvm.data.database.database.DataBase

object DBConfig {
    private val dataBase = Room.databaseBuilder(
        MovieApplication.application(),
        DataBase::class.java, "database-movie"
    ).build()

    val favoriteDao = dataBase.favoriteDao()

}