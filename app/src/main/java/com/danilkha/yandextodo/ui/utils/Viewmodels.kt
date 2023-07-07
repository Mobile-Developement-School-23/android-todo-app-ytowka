package com.danilkha.yandextodo.ui.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.ViewModelInitializer
import com.danilkha.yandextodo.di.ViewModelComponent

inline fun<reified T : ViewModel> Fragment.viewModel(crossinline initializer: (ViewModelComponent) -> T) = lazy {
    val viewModelComponent = app.appComponent.viewModelComponent
    ViewModelProvider(this, object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer(viewModelComponent) as T
        }
    })[T::class.java]
}

inline fun<reified T : ViewModel> Fragment.activityViewModel(crossinline initializer: (ViewModelComponent) -> T) = lazy {
    val viewModelComponent = app.appComponent.viewModelComponent
    ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return initializer(viewModelComponent) as T
        }
    })[T::class.java]
}