package com.example.mvvm.extension

fun <T> List<T>?.orEmpty(): MutableList<T> {
    return this?.toMutableList() ?: mutableListOf()
}