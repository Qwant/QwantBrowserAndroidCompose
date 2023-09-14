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

private val lightColorScheme = lightColorScheme(
    primary = ActionBlue400,
    onPrimary = Color.White,
    primaryContainer = Color.White,
    onPrimaryContainer = Grey900,
    secondaryContainer = ActionBlue50,
    onSecondaryContainer = Grey900,
    tertiary = ActionBlue300,
    tertiaryContainer = Color.White,
    onTertiaryContainer = Grey900,
    outline = Grey900Alpha12,
    surfaceVariant = Grey100,
    onSurfaceVariant = Grey900
)

private val darkColorScheme = darkColorScheme(
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
    surfaceVariant = Grey600,
    onSurfaceVariant = Color.White
)

private val privateColorScheme = darkColorScheme.copy(
    primary = Purple200,
    onPrimary = Grey900,
    primaryContainer = Purple700,
    onPrimaryContainer = Color.White,
    secondaryContainer = Grey000Alpha16,
    onSecondaryContainer = Color.White,
    tertiary = Purple200,
    tertiaryContainer = PurpleTertiary,
    onTertiaryContainer = Grey900,
    outline = Grey000Alpha16,
    surfaceVariant = Grey000Alpha16,
    onSurfaceVariant = Color.White
)

@Composable
private fun animateColor(targetValue: Color) =
    animateColorAsState(
        targetValue = targetValue,
        animationSpec = tween(durationMillis = 1000),
        label = "theme colors"
    ).value

@Composable
fun ColorScheme.animatedColors() = copy(
    primary = animateColor(primary),
    onPrimary = animateColor(onPrimary),
    primaryContainer = animateColor(primaryContainer),
    onPrimaryContainer = animateColor(onPrimaryContainer),
    secondaryContainer = animateColor(secondaryContainer),
    onSecondaryContainer = animateColor(onSecondaryContainer),
    tertiary = animateColor(tertiary),
    tertiaryContainer = animateColor(tertiaryContainer),
    onTertiaryContainer = animateColor(onTertiaryContainer),
    outline = animateColor(outline),
    surface = animateColor(primaryContainer),
    onSurface = animateColor(onPrimaryContainer),
    surfaceVariant = animateColor(surfaceVariant),
    onSurfaceVariant = animateColor(onSurfaceVariant),
    background = animateColor(primaryContainer),
    onBackground = animateColor(onPrimaryContainer)
)

@Composable
fun QwantBrowserTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    privacy: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        privacy -> privateColorScheme
        darkTheme -> darkColorScheme
        else -> lightColorScheme
    }.animatedColors()

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            window.navigationBarColor = colorScheme.background.toArgb()
            /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                window.navigationBarDividerColor = colorScheme.outline.toArgb()
            } */
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !(darkTheme || privacy)
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = !(darkTheme || privacy)
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