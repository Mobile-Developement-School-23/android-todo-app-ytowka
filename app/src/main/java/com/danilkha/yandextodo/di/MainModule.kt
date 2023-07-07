package com.danilkha.yandextodo.di

import android.content.Context
import com.danilkha.yandextodo.App
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface MainModule {

    @Binds
    fun getContext(app: App): Context
}