package com.danilkha.yandextodo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
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
    val colorScheme = ColorScheme(
        primary = colorResource(R.color.color_blue),
        onPrimary = colorResource(R.color.back_primary),
        primaryContainer = colorResource(R.color.color_blue_30),
        onPrimaryContainer = colorResource(R.color.back_primary),
        secondary = colorResource(R.color.color_green),
        onSecondary = colorResource(R.color.back_secondary),
        secondaryContainer = colorResource(R.color.back_secondary),
        onSecondaryContainer = colorResource(R.color.color_gray),
        tertiary = colorResource(R.color.label_tertiary),
        onTertiary = colorResource(R.color.color_blue),
        tertiaryContainer = colorResource(R.color.color_blue),
        onTertiaryContainer = colorResource(R.color.color_blue),
        error = colorResource(R.color.color_red),
        errorContainer = colorResource(R.color.back_primary),
        onError = colorResource(R.color.back_primary),
        onErrorContainer = colorResource(R.color.back_primary),
        background = colorResource(R.color.back_primary),
        onBackground = colorResource(R.color.label_primary),
        surface = colorResource(R.color.back_elevated),
        onSurface = colorResource(R.color.label_primary),
        surfaceVariant = colorResource(R.color.color_blue),
        onSurfaceVariant = colorResource(R.color.label_secondary),
        outline = colorResource(R.color.support_separator),
        inverseOnSurface = colorResource(R.color.color_gray),
        inverseSurface = colorResource(R.color.color_gray),
        inversePrimary = colorResource(R.color.color_gray),
        surfaceTint = colorResource(R.color.color_blue),
        outlineVariant = colorResource(R.color.support_separator),
        scrim = colorResource(R.color.support_overlay),
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
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}