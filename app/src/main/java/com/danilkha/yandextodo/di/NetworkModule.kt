package com.danilkha.yandextodo.di

import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.data.network.Api
import com.danilkha.yandextodo.data.network.TokenInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkModule(val app: App) {

    val retrofit by lazy{
        val tokenInterceptor = TokenInterceptor(API_TOKEN)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit
    }

    fun getApi(): Api {
        return retrofit.create(Api::class.java)
    }

    companion object{
        const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
        const val API_TOKEN = "hypsobathymetric"
    }
}