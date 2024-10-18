package com.example.mvvmcountry.DI

import com.example.mvvmcountry.model.DataModel
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("/v3.1/all")
    suspend fun getData(): Response<List<DataModel>>
}