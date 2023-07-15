package com.danilkha.yandextodo.ui.theme

import android.app.Activity
import android.os.Build
import android.provider.CalendarContract.Colors
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.colorResource
import androidx.core.view.WindowCompat
import com.danilkha.yandextodo.R

@Composable
fun YandexTodoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = androidx.compose.material.Colors(
        primary = colorResource(R.color.color_blue),
        onPrimary = colorResource(R.color.back_primary),
        secondary = colorResource(R.color.color_green),
        onSecondary = colorResource(R.color.back_secondary),
        error = colorResource(R.color.color_red),
        onError = colorResource(R.color.back_primary),
        background = colorResource(R.color.back_primary),
        onBackground = colorResource(R.color.label_primary),
        surface = colorResource(R.color.back_secondary),
        onSurface = colorResource(R.color.label_primary),
        primaryVariant = colorResource(R.color.color_blue_30),
        secondaryVariant = colorResource(R.color.label_secondary),
        isLight = !darkTheme,
    )
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    MaterialTheme(
        colors = colorScheme,
        typography = Typography,
        content = {
            Box(modifier = Modifier.background(color = MaterialTheme.colors.background)) {
                content()
            }
        }
    )
}