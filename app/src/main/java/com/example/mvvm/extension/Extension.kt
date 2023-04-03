package com.example.mvvm.extension

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.view.forEach
import com.bumptech.glide.Glide
import com.example.mvvm.util.Constant
import java.text.SimpleDateFormat
import java.util.*

fun Int?.orZero(): Int = this ?: 0

fun String?.orW600xH900(): String =
    if (this.isNullOrEmpty()) Constant.EMPTY else Constant.POSTER_PATH + this

fun String?.orW533xH300(): String =
    if (this.isNullOrEmpty()) Constant.EMPTY else Constant.BACKDROP_PATH + this

fun Double?.convert(): String = if (this == null) Constant.EMPTY else "$this / 10"

fun Double?.rating(): Float = this?.div(2)?.toFloat() ?: 0f

fun String?.formatDate(format: String): String {
    val parser = SimpleDateFormat(Constant.YYYY_DD_MM_2, Locale.ENGLISH)
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    val output: String? = this?.let { it ->
        parser.parse(it)?.let { date ->
            formatter.format(date)
        }
    }
    return output ?: Constant.EMPTY
}

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

fun <T> List<T>?.orEmpty(): MutableList<T> {
    return this?.toMutableList() ?: mutableListOf()
}

fun View.showKeyBoard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun View.hideKeyBoard() {
    val imm: InputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun AppCompatImageView.loadImage(path: String) {
    Glide.with(context)
        .load(path)
        .into(this)
}