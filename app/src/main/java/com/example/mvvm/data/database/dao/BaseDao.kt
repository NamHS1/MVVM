package com.example.mvvm.data.database.dao

import androidx.lifecycle.LiveData

interface BaseDao<T> {

    suspend fun insert(data: T)

    suspend fun update(data: T)

    suspend fun delete(data: T)

    fun getAllList(): LiveData<T>
}