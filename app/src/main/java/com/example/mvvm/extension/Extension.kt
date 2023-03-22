package com.example.mvvm.extension

import com.example.mvvm.util.Constant

fun Int?.orZero(): Int = this ?: 0

fun String?.w600xh900(): String = if (this == null) "" else Constant.POSTER_PATH + this

fun String?.w533xh300(): String = if (this == null) "" else Constant.BACKDROP_PATH + this