package com.qwant.android.qwantbrowser.ui.preferences.screens.frontendInterface

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.ui.preferences.viewmodels.FrontEndPreferencesViewModel
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.preferences.screens.BasePreferenceScreen
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceGroup
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceToggle


@Composable
fun FrontendInterfacePreferencesScreen(
    frontEndPreferencesViewModel: FrontEndPreferencesViewModel = hiltViewModel()
) {
    val prefs by frontEndPreferencesViewModel.preferencesState.collectAsState()

    BasePreferenceScreen(destination = PreferenceNavDestination.FrontendInterface) {
        PreferenceGroup(
            title = "Interface language",
            icon = Icons.Default.Language,
        ) {
            InterfaceLanguageDropdown(
                value = prefs.interfaceLanguage,
                onValueChange = { frontEndPreferencesViewModel.updateInterfaceLanguage(it) }
            )
        }

        PreferenceGroup(
            title = "Appearance",
            icon = Icons.Default.WbSunny,
            description = when (prefs.appearance) {
                Appearance.LIGHT -> "Light"
                Appearance.DARK -> "Dark"
                Appearance.SYSTEM_SETTINGS -> "System"
                else -> "Unrecognized or null"
            }
        ) {
            AppearanceSelector(
                value = prefs.appearance,
                onValueChange = { frontEndPreferencesViewModel.updateAppearance(it) }
            )
        }

        val themeEnabled = (prefs.appearance == Appearance.LIGHT)
            || (prefs.appearance == Appearance.SYSTEM_SETTINGS && !isSystemInDarkTheme())

        val themeDescription = if (themeEnabled) {
            "Customize the color on the entire site"
        } else {
            "You cannot change the theme color in dark mode"
        }

        PreferenceGroup(
            title = "Theme",
            icon = Icons.Default.Palette,
            description = themeDescription
        ) {
            CustomPageColorSelector(
                value = prefs.customPageColor,
                onValueChange = { frontEndPreferencesViewModel.updateCustomPageColor(it) },
                enabled = themeEnabled
            )
        }

        PreferenceGroup(
            title = "Characters",
            icon = Icons.Default.People,
            description = "Change the character on the home page"
        ){
            CustomPageCharacterSelector(
                value = prefs.customPageCharacter,
                onValueChange = { frontEndPreferencesViewModel.updateCustomPageCharacter(it) }
            )
        }

        PreferenceGroup {
            PreferenceToggle(
                label = "News on homepage",
                icon = rememberVectorPainter(image = Icons.Default.Pages),
                value = prefs.showNews,
                onValueChange = { frontEndPreferencesViewModel.updateShowNews(it) }
            )
            PreferenceToggle(
                label = "Sponsored shortcuts on homepage",
                icon = rememberVectorPainter(image = Icons.Default.Bolt),
                value = prefs.showSponsor,
                onValueChange = { frontEndPreferencesViewModel.updateShowSponsor(it) }
            )
        }
    }
}