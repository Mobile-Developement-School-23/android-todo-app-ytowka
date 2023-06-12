package com.danilkha.yandextodo.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.ViewModelInitializer

inline fun<reified T : ViewModel> Fragment.viewModel(crossinline initializer: () -> T) = lazy {
    ViewModelProvider(this, object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer() as T
        }
    })[T::class.java]
}