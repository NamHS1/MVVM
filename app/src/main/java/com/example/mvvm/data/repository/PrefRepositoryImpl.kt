package com.example.mvvm.data.repository

import com.example.mvvm.data.model.MovieDetail
import com.example.mvvm.data.service.PrefService

class PrefRepositoryImpl(
    private val prefService: PrefService
) : PrefRepository {
    override fun putListMovie(key: String, data: List<MovieDetail>) = prefService.put(key, data)

    override fun getListMovie(key: String) = prefService.getListAny(key, MovieDetail::class.java)

    override fun removeListMovie(key: String) = prefService.remove(key)
}