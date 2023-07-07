package com.danilkha.yandextodo.data.network.response

import com.google.gson.annotations.SerializedName


/*{
  "status": "ok",
  "list": [
    {
      "id": <uuid>, // уникальный идентификатор элемента
      "text": "blablabla",
      "importance": "<importance>", // importance = low | basic | important
      "deadline": <unix timestamp>, // int64, может отсутствовать, тогда нет
      "done": <bool>,
      "color": "#FFFFFF", // может отсутствовать
      "created_at": <unix timestamp>,
      "changed_at": <unix timestamp>,
      "last_updated_by": <device id>
    },
    ...
  ],
  "revision": <revision>, // ревизия данных, int32
}*/

data class TaskListResponse(
    @SerializedName("status") val status: String,
    @SerializedName("list") val list: List<TaskResponse>,
    @SerializedName("revision") val revision: Int,
)