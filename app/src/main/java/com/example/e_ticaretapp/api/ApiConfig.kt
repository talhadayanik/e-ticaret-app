package com.example.e_ticaretapp.api

import com.example.e_ticaretapp.services.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        fun getApiService(): ApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://dummyjson.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(ApiService::class.java)
        }
    }
}