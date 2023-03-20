package com.example.mvvm.extension

import com.example.mvvm.util.Constant

fun Int?.orZero(): Int = this ?: 0

fun String?.convertPath(): String = if (this == null) "" else Constant.POSTER_PATH + this