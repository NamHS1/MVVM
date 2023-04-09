package com.example.mvvm.ui.model

import androidx.paging.PagingData

class HomeData(
    val upComing: PagingData<MovieResponse>,
    val nowPlaying: PagingData<MovieResponse>,
    val popular: PagingData<MovieResponse>,
)