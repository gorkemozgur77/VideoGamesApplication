package com.example.videogamesapplication.service

import com.example.videogamesapplication.constants.Constants
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Client {

    private lateinit var apiService: ApiService


    fun getApiService(): ApiService {

        if(!::apiService.isInitialized) {

            val retrofit = Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okhttpClient())
                .build()


            apiService = retrofit.create(ApiService::class.java)
        }

        return apiService
    }

    private fun okhttpClient() : OkHttpClient {
        return OkHttpClient.Builder().addInterceptor(HeaderInterceptor()).build()
    }

}