package com.example.mvvm.extension

import com.example.mvvm.util.Constant

fun Int?.orZero(): Int = this ?: 0

fun String?.orW600xH900(): String = if (this == null) "" else Constant.POSTER_PATH + this

fun String?.orW533xH300(): String = if (this == null) "" else Constant.BACKDROP_PATH + this