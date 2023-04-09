package com.example.mvvm.data.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.mvvm.MovieApplication
import com.example.mvvm.data.service.PrefService
import com.google.gson.Gson
import com.google.gson.JsonElement

class PrefConfig {
    companion object {
        private const val PREFERENCE_NAME = "MOVIE_PREF"
    }

    private inner class Pref : PrefService {

        private var pref: SharedPreferences =
            MovieApplication.application()
                .getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)

        private val editor: SharedPreferences.Editor
            get() = pref.edit()

        private val gson = Gson()

        override fun getInt(key: String): Int = pref.getInt(key, 0)

        override fun getLong(key: String): Long = pref.getLong(key, 0)

        override fun getFloat(key: String): Float = pref.getFloat(key, 0f)

        override fun getString(key: String): String? = pref.getString(key, null)

        override fun getBoolean(key: String): Boolean = pref.getBoolean(key, false)

        override fun <T> getAny(key: String, clazz: Class<T>): T? =
            getString(key)?.let { gson.fromJson(it, clazz) }

        override fun <T> getListAny(key: String, clazz: Class<T>): List<T>? =
            getString(key)?.let { json ->
                val any = gson.fromJson(json, JsonElement::class.java).asJsonArray
                return any.map {
                    gson.fromJson(it, clazz)
                }
            }

        override fun <T> put(key: String, data: T) {
            when (data) {
                is Int -> editor.putInt(key, data)
                is Boolean -> editor.putBoolean(key, data)
                is String -> editor.putString(key, data)
                is Long -> editor.putLong(key, data)
                is Float -> editor.putFloat(key, data)
                else -> editor.putString(key, gson.toJson(data))
            }.commit()
        }

        override fun remove(key: String) {
            editor.remove(key).commit()
        }

        /**
         *  remove all values from the preferences
         */
        override fun clear() {
            editor.clear().apply()
        }

        fun build() = Pref()
    }

    val prefService: PrefService = Pref().build()
}