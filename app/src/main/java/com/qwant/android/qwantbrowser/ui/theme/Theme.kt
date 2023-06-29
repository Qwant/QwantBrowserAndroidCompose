package com.qwant.android.qwantbrowser.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat


data class QwantTheme(val dark: Boolean, val private: Boolean)
val LocalQwantTheme = compositionLocalOf { QwantTheme(dark = false, private = false) }

private val LightColorScheme = lightColorScheme(
    primary = ActionBlue400,
    onPrimary = Color.White,
    primaryContainer = Color.White,
    onPrimaryContainer = Grey900,
    secondaryContainer = ActionBlue50,
    onSecondaryContainer = Grey900,
    tertiary = ActionBlue500,
    tertiaryContainer = Color.White,
    onTertiaryContainer = Grey900,
    outline = Grey900Alpha12,
)

private val DarkColorScheme = darkColorScheme(
    primary = ActionBlue200,
    onPrimary = Grey900,
    primaryContainer = Grey750,
    onPrimaryContainer = Color.White,
    secondaryContainer = Grey650,
    onSecondaryContainer = Color.White,
    tertiary = ActionBlue200,
    tertiaryContainer = Grey700,
    onTertiaryContainer = Color.White,
    outline = Grey000Alpha16,
)

private val PrivateColorScheme = DarkColorScheme.copy(
    primary = Purple200,
    onPrimary = Grey900,
    primaryContainer = Purple700,
    onPrimaryContainer = Color.White,
    secondaryContainer = Grey000Alpha16,
    onSecondaryContainer = Color.White,
    tertiary = ActionBlue200,
    tertiaryContainer = PurpleTertiary,
    onTertiaryContainer = Grey900,
    outline = Grey000Alpha16,
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
    onPrimary = animateColor(onPrimary),
    primaryContainer = animateColor(primaryContainer),
    onPrimaryContainer = animateColor(onPrimaryContainer),
    secondaryContainer = animateColor(secondaryContainer),
    onSecondaryContainer = animateColor(onSecondaryContainer)
).copy(
    surface = primaryContainer,
    onSurface = onPrimaryContainer,
    background = primaryContainer,
    onBackground = onPrimaryContainer
)

@Composable
fun QwantBrowserTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    privacy: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        privacy -> PrivateColorScheme
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }.animatedColors()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            /* window.navigationBarColor = colorScheme.primary.toArgb()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = Color.Red.toArgb()
            } */
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
            // WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = darkTheme
        }
    }

    CompositionLocalProvider(
        LocalQwantTheme provides QwantTheme(darkTheme, privacy)
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = MaterialTheme.shapes.copy(extraSmall = RoundedCornerShape(16.dp)),
            typography = Typography,
            content = content
        )
    }
}