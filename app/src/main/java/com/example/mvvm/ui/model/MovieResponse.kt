package com.example.mvvm.ui.model

data class MovieResponse(
    val id: Int,
    val title: String,
    val posterPath: String,
    val backdropPath: String,
    val release: String,
    val voteAverage: Float
)