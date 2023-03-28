package com.example.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("title") val title: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("revenue") val revenue: Int?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("production_companies") val companies: List<Companies>?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("tagline") val tagline: String?,
)