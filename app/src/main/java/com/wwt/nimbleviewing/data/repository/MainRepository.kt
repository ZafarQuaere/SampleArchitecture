package com.wwt.nimbleviewing.data.repository

import com.wwt.nimbleviewing.data.api.ApiHelper

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun getData() = apiHelper.getData()
}