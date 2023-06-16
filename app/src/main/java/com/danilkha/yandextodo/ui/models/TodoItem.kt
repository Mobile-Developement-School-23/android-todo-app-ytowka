package com.danilkha.yandextodo.ui.models

import android.os.Parcel
import android.os.Parcelable
import com.danilkha.yandextodo.domain.models.ImportanceDto
import com.danilkha.yandextodo.domain.models.TodoItemDto
import java.util.Date

data class TodoItem(
    val id: String,
    val text: String,
    val importance: Importance,
    val time: Date?,
    val completed: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
): Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString()?.let { Importance.valueOf(it) } ?: Importance.NORMAL,
        parcel.readSerializable() as Date,
        parcel.readByte() != 0.toByte(),
        parcel.readSerializable() as Date,
        parcel.readSerializable() as Date
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(text)
        parcel.writeString(importance.name)
        parcel.writeSerializable(time)
        parcel.writeByte(if (completed) 1 else 0)
        parcel.writeSerializable(createdAt)
        parcel.writeSerializable(updatedAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoItem> {
        override fun createFromParcel(parcel: Parcel): TodoItem {
            return TodoItem(parcel)
        }

        override fun newArray(size: Int): Array<TodoItem?> {
            return arrayOfNulls(size)
        }
    }

}

fun TodoItem.toDto(): TodoItemDto = TodoItemDto(
    id,
    text,
    importance.toDto(),
    time,
    completed,
    createdAt,
    updatedAt,
)

enum class Importance{ LOW, NORMAL, HIGH }

fun Importance.toDto(): ImportanceDto = when(this){
    Importance.LOW -> ImportanceDto.LOW
    Importance.NORMAL -> ImportanceDto.NORMAL
    Importance.HIGH -> ImportanceDto.HIGH
}
