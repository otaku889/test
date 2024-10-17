package com.example.mvvmcountry.model

import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImpCountryRepo @Inject constructor(private val apiService: ApiService) {
    suspend fun getData(): Response<List<DataModel>> = apiService.getData()
}