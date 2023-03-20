package com.example.mvvm.data.model.home

import com.example.mvvm.data.model.MovieItem

data class Popular(
    val page: Int,
    val totalPage: Int,
    val movies: List<MovieItem>? = null
)