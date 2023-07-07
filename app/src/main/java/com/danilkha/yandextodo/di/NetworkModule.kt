package com.danilkha.yandextodo.di

import com.danilkha.yandextodo.App
import com.danilkha.yandextodo.data.network.Api
import com.danilkha.yandextodo.data.network.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
object NetworkModule {

    @Provides
    @AppScope
    fun getRetrofit(): Retrofit {
        val tokenInterceptor = TokenInterceptor(API_TOKEN)

        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(loggingInterceptor)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @AppScope

    fun getApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    const val BASE_URL = "https://beta.mrdekk.ru/todobackend/"
    const val API_TOKEN = "hypsobathymetric"
}