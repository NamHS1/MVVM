package com.example.mvvm.extension

import android.view.View
import android.view.ViewGroup
import androidx.core.view.forEach
import com.example.mvvm.util.Constant

fun Int?.orZero(): Int = this ?: 0

fun String?.orW600xH900(): String = if (this == null) "" else Constant.POSTER_PATH + this

fun String?.orW533xH300(): String = if (this == null) "" else Constant.BACKDROP_PATH + this

fun ViewGroup.onVisibility(vararg views: View) {
    this.forEach {
        view@ for (view in views) {
            if (it.id == view.id) {
                it.visibility = View.VISIBLE
                break@view
            } else {
                it.visibility = View.INVISIBLE
            }
        }
    }
}

fun <T> List<T>?.orEmpty() : MutableList<T> {
    return this?.toMutableList() ?: mutableListOf()
}