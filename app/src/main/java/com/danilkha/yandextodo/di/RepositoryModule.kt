package com.danilkha.yandextodo.di

import com.danilkha.yandextodo.domain.repository.TodoItemsRepository
import dagger.Binds
import dagger.Module

@Module
interface RepositoryModule {

    @Binds
    fun getRepository(repository: TodoItemsRepository.Impl): TodoItemsRepository
}