package com.wwt.nimbleviewing.data.api

import com.wwt.nimbleviewing.data.model.Photos
import retrofit2.http.GET

interface ApiService {

    @GET("photos")
    suspend fun getData(): List<Photos>

}