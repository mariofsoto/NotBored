package com.example.notbored

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {
    private const val BASE_URL = "http://www.boredapi.com/api/"

    fun getInstance() : Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}