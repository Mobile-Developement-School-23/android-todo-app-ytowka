package com.danilkha.yandextodo.ui.utils

import android.R
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.danilkha.yandextodo.App
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.launch

val Fragment.app
    get() = requireActivity().application as App

fun <T> Flow<T>.collectWithLifecycle(lifecycleOwner: LifecycleOwner, collector: FlowCollector<T>){
    lifecycleOwner.lifecycleScope.launch {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            collect(collector)
        }
    }
}

fun TextView.setTextDrawableColor(color: Int){
    for (drawable in this.compoundDrawables) {
        drawable?.setTint(color)
        drawable?.mutate()
        drawable?.invalidateSelf()
    }
}