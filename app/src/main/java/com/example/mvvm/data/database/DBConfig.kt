package com.example.mvvm.data.database

import androidx.room.Room
import com.example.mvvm.MovieApplication
import com.example.mvvm.data.database.database.DataBase

object DBConfig {
    private const val DATABASE_NAME = "database-movie"
    private val dataBase = Room.databaseBuilder(
        MovieApplication.application(),
        DataBase::class.java, DATABASE_NAME
    ).build()

    val favoriteDao = dataBase.favoriteDao()

}