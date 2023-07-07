package com.danilkha.yandextodo.domain.usecase

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class SimpleFlowUseCase<out R>{

    abstract fun execute(): Flow<R>

    operator fun invoke(): Flow<Result<R>> {
        return execute()
            .catch {
                Log.e("error", "usecase ${this::class.simpleName} fails", it)
                Result.failure<R>(it)
            }
            .flowOn(Dispatchers.IO)
            .map {
                Result.success(it)
            }
    }
}