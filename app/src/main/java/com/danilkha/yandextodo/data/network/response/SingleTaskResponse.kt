package com.danilkha.yandextodo.data.network.response

import com.google.gson.annotations.SerializedName

data class SingleTaskResponse(
    @SerializedName("status") val status: String,
    @SerializedName("element") val task: TaskListResponse,
    @SerializedName("revision") val revision: Int,
)