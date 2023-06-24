package com.danilkha.yandextodo.domain.usecase

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
            Result.failure(e)
        }
    }
}