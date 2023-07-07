package com.danilkha.yandextodo.data.network

import com.danilkha.yandextodo.data.network.request.TaskListRequest
import com.danilkha.yandextodo.data.network.request.TaskRequest
import com.danilkha.yandextodo.data.network.response.SingleTaskResponse
import com.danilkha.yandextodo.data.network.response.TaskListResponse
import com.danilkha.yandextodo.data.network.response.TaskResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface Api {

    @GET("list")
    suspend fun getTaskList(@Header("X-Last-Known-Revision") version: Int): TaskListResponse

    @GET("list/{id}")
    suspend fun getTaskById(@Header("X-Last-Known-Revision") version: Int, @Path("id") id: String): SingleTaskResponse

    @PATCH("list")
    suspend fun updateTasks(@Header("X-Last-Known-Revision") version: Int, @Body tasks: TaskListRequest): TaskListResponse

    @POST("list")
    suspend fun createTask(@Header("X-Last-Known-Revision") version: Int, @Body task: TaskRequest): SingleTaskResponse

    @PUT("list/{id}")
    suspend fun updateTask(@Header("X-Last-Known-Revision") version: Int, @Body task: TaskRequest, @Path("id") id: String): SingleTaskResponse
    @DELETE("list/{id}")
    suspend fun deleteTask(@Header("X-Last-Known-Revision") version: Int, @Path("id") id: String): SingleTaskResponse
}