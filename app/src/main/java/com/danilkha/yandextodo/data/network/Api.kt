package com.danilkha.yandextodo.data.network

import com.danilkha.yandextodo.data.network.response.SingleTaskResponse
import com.danilkha.yandextodo.data.network.response.TaskListResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface Api {

    @GET("list")
    suspend fun getTaskList(@Header("X-Last-Known-Revision") version: Int): TaskListResponse

    @GET("list/{id}")
    suspend fun getTaskById(@Path("id") id: String): SingleTaskResponse
}