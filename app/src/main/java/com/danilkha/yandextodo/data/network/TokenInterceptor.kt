package com.danilkha.yandextodo.data.network

import okhttp3.Interceptor
import okhttp3.Interceptor.Chain
import okhttp3.Response

class TokenInterceptor(private val token: String) : Interceptor{
    override fun intercept(chain: Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader("Authorization", " Bearer $token")
            .build()
        return chain.proceed(request)
    }
}