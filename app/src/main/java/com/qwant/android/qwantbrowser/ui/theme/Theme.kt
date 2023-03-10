package com.qwant.android.qwantbrowser.ui.theme

import android.app.Activity
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat


private val LightColorScheme = lightColorScheme(
    primary = ActionBlue200,
    onPrimary = Color.White,
    secondaryContainer = Color.White,
    onSecondaryContainer = Color.Black
)

private val DarkColorScheme = darkColorScheme(
    primary = ActionBlue400,
    secondaryContainer = GreySecondary,
    onSecondaryContainer = Color.White
)

private val DarkPrivateColorScheme = DarkColorScheme.copy(
    primary = Purple200
)

@Composable
private fun animateColor(targetValue: Color) =
    animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = 1000)
    ).value

@Composable
fun ColorScheme.animatedColors() = copy(
    primary = animateColor(primary),
    background = animateColor(background),
    surface = animateColor(surface),
    onSurface = animateColor(onSurface),
    secondaryContainer = animateColor(secondaryContainer),
    onSecondaryContainer = animateColor(onSecondaryContainer)
)

@Composable
fun QwantBrowserTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    privacy: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        privacy -> DarkPrivateColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }.animatedColors()

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