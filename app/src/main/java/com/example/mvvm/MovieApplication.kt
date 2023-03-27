package com.example.mvvm

import android.app.Application

class MovieApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private lateinit var instance: Application

        fun application(): Application {
            return instance
        }
    }

}