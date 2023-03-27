package com.example.mvvm.data.repository

import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.preference.PrefConfig.prefService

object PrefRepository {

    fun putListMovie(key: String, data: List<MovieDetail>) = prefService.put(key, data)

    fun getListMovie(key: String): List<MovieDetail>? = prefService.getListAny(key, MovieDetail::class.java)

    fun removeListMovie(key: String) = prefService.remove(key)
}