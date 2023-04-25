package com.example.mvvm.extension

import com.example.mvvm.util.Constant

fun Int?.orZero(): Int = this ?: 0

fun String?.orW600xH900(): String =
    if (this.isNullOrEmpty()) Constant.EMPTY else Constant.POSTER_PATH + this

fun String?.orW533xH300(): String =
    if (this.isNullOrEmpty()) Constant.EMPTY else Constant.BACKDROP_PATH + this

fun Double?.convert(): String = if (this == null) Constant.EMPTY else "$this / 10"

fun Double?.rating(): Float = this?.div(2)?.toFloat() ?: 0f