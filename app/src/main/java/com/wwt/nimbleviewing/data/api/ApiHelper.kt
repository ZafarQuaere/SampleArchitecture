package com.wwt.nimbleviewing.data.api

class ApiHelper(private val apiService: ApiService) {

    suspend fun getData() = apiService.getData()
}