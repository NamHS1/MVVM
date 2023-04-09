package com.example.mvvm.data.repository

import com.example.mvvm.data.model.MovieDetail

interface PrefRepository {
    fun putListMovie(key: String, data: List<MovieDetail>)

    fun getListMovie(key: String): List<MovieDetail>?

    fun removeListMovie(key: String)
}