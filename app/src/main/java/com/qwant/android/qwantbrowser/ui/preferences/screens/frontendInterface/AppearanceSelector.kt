package com.qwant.android.qwantbrowser.ui.preferences.screens.frontendInterface

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.filled.SettingsSystemDaydream
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelector
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceSelectorOption

@Composable
fun AppearanceSelector(
    value: Appearance,
    onValueChange: (Appearance) -> Unit
) {
    PreferenceSelector(
        options = listOf(
            PreferenceSelectorOption(
                label = "Light",
                value = Appearance.LIGHT,
                icon = rememberVectorPainter(Icons.Default.LightMode),
                backgroundColor = Color.White,
                iconColor = Color.Black
            ),
            PreferenceSelectorOption(
                label = "Dark",
                value = Appearance.DARK,
                icon = rememberVectorPainter(Icons.Default.DarkMode),
                backgroundColor = Color.Black,
                iconColor = Color.White
            ),
            PreferenceSelectorOption(
                label = "System",
                value = Appearance.SYSTEM_SETTINGS,
                icon = rememberVectorPainter(Icons.Default.SettingsSystemDaydream)
            )
        ),
        selectedValue = value,
        onSelected = onValueChange
    )
}