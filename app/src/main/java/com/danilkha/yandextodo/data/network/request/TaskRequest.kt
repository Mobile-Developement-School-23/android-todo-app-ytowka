package com.danilkha.yandextodo.data.network.request

import com.danilkha.yandextodo.data.network.response.TaskResponse
import com.google.gson.annotations.SerializedName

data class TaskRequest(
    @SerializedName("element") val task: TaskResponse,
)