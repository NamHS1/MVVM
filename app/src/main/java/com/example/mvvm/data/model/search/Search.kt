package com.example.mvvm.data.model.search

import com.example.mvvm.data.model.SearchItem

data class Search(
    val page: Int,
    val totalPage: Int,
    val movies: List<SearchItem>
)