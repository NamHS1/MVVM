package com.example.mvvm.extension

import com.example.mvvm.util.Constant
import java.text.SimpleDateFormat
import java.util.*

fun String?.formatDate(format: String): String {
    if (this.isNullOrEmpty()) {
        return Constant.EMPTY
    }
    val parser = SimpleDateFormat(Constant.YYYY_DD_MM_2, Locale.ENGLISH)
    val formatter = SimpleDateFormat(format, Locale.ENGLISH)
    val output: String? = this.let {
        parser.parse(it)?.let { date ->
            formatter.format(date)
        }
    }
    return output ?: Constant.EMPTY
}