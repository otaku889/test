package com.example.mvvmcountry.model

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/v3.1/all")
    suspend fun getData(): Response<List<DataModel>>
}