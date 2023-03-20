package com.example.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("page") val page: Int?,
    @SerializedName("total_pages") val totalPages: Int?,
    @SerializedName("results") val results: List<Movie>?
)