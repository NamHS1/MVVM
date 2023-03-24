package com.example.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class Countries(
    @SerializedName("iso_3166_1") val iso: String?,
    @SerializedName("name") val name: String?
)
