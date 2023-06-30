package com.danilkha.yandextodo.domain.usecase

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class SimpleUseCase<out R>{

    abstract suspend fun execute(): R

    suspend operator fun invoke(): Result<R>{
        return try {
            val result = withContext(Dispatchers.IO){
                execute()
            }
            Result.success(result)
        }catch (e: Exception){
            Log.e("error", "usecase ${this::class.simpleName} fails", e)
            Result.failure(e)
        }
    }
}