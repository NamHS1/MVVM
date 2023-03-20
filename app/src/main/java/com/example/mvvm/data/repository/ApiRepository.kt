package com.example.mvvm.data.repository

import com.example.mvvm.data.api.ApiConfig
import com.example.mvvm.data.service.ApiService

abstract class ApiRepository(protected val apiService: ApiService = ApiConfig.apiService)