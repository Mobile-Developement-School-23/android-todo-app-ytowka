package com.danilkha.yandextodo.data.local.entity

import com.danilkha.yandextodo.domain.models.ImportanceDto

enum class ImportanceDB { LOW, BASIC, HIGH }

fun ImportanceDB.toDto(): ImportanceDto = when(this){
    ImportanceDB.LOW -> ImportanceDto.LOW
    ImportanceDB.BASIC -> ImportanceDto.NORMAL
    ImportanceDB.HIGH -> ImportanceDto.HIGH
}