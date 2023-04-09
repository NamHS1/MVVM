package com.example.mvvm.ui.model

import androidx.paging.PagingData

class MovieCombine :
        (PagingData<MovieResponse>, PagingData<MovieResponse>, PagingData<MovieResponse>) -> HomeData {
    override fun invoke(
        p1: PagingData<MovieResponse>,
        p2: PagingData<MovieResponse>,
        p3: PagingData<MovieResponse>
    ): HomeData {
        return HomeData(
            upComing = p1,
            nowPlaying = p2,
            popular = p3
        )
    }
}