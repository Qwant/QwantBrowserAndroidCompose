package com.qwant.android.qwantbrowser.ui.preferences.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Language
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceGroup

@Composable
fun FrontendInterfacePreferencesScreen() {
    BasePreferenceScreen(destination = PreferenceNavDestination.FrontendInterface) {
        PreferenceGroup(
            title = "Interface language", 
            icon = Icons.Default.Language, 
            description = "Test de description"
        ) {
            Text(text = "coucou")
        }

        PreferenceGroup(title = "Toto") {
            Text(text = "coucou 2")
            Text(text = "coucou 3")
        }

        PreferenceGroup {
            Text(text = "coucou 2")
            Text(text = "coucou 3")
        }
    }
}