package com.danilkha.yandextodo.domain.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class UseCase<in P, out R>{

    abstract suspend fun execute(params: P): R

    suspend operator fun invoke(params: P): Result<R>{
        return try {
            val result = withContext(Dispatchers.IO){
                execute(params)
            }
            Result.success(result)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}