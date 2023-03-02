package com.qwant.android.qwantbrowser.ui.preferences.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Light
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesViewModel
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.preferences.widgets.InterfaceLanguageDropdown
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceGroup


@Composable
fun FrontendInterfacePreferencesScreen(
    frontEndPreferencesViewModel: FrontEndPreferencesViewModel = hiltViewModel()
) {
    val prefs by frontEndPreferencesViewModel.preferencesState.collectAsState()

    BasePreferenceScreen(destination = PreferenceNavDestination.FrontendInterface) {
        PreferenceGroup(
            title = "Interface language",
            icon = Icons.Default.Language, 
            description = "Test de description"
        ) {
            InterfaceLanguageDropdown(
                value = prefs.interfaceLanguage,
                onValueChange = { frontEndPreferencesViewModel.updateInterfaceLanguage(it) }
            )
        }

        PreferenceGroup(
            title = "Appearance",
            icon = Icons.Default.Light
        ) {
            Text(text = "coucou 2")
            Text(text = "coucou 3")
        }

        PreferenceGroup {
            Text(text = "coucou 2")
            Text(text = "coucou 3")
        }
    }
}