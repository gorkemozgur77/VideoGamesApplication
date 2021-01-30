package com.example.videogamesapplication.service

import com.example.videogamesapplication.constants.Constants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor{
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader("Content-Type", Constants.content_tyoe_HEADER)
        requestBuilder.addHeader("x-rapidapi-key", Constants.x_rapidapi_key_HEADER)
        requestBuilder.addHeader("x-rapidapi-host", Constants.x_rapidapi_host_HEADER)

        return chain.proceed(requestBuilder.build())
    }
}