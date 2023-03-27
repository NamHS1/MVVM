package com.example.mvvm.data.service

interface PrefService {
    fun getInt(key: String): Int

    fun getLong(key: String): Long

    fun getFloat(key: String): Float

    fun getString(key: String): String?

    fun getBoolean(key: String): Boolean

    fun <T> getAny(key: String, clazz: Class<T>): T?

    fun <T> getListAny(key: String, clazz: Class<T>): List<T>?

    fun <T> put(key: String, data: T)

    fun remove(key: String)

    fun clear()
}