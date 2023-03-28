package com.example.mvvm.data.model

import com.google.gson.annotations.SerializedName

data class Companies(
    @SerializedName("logo_path")  val logoPath: String?,
    @SerializedName("name") val name: String?,
)
