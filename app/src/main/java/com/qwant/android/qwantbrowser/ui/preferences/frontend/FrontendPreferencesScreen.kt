package com.qwant.android.qwantbrowser.ui.preferences.frontend

import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.ui.preferences.BasePreferenceScreen
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceLink

@Composable
fun FrontEndPreferencesScreen(
    onNavigateTo: (PreferenceNavDestination) -> Unit
) {
    BasePreferenceScreen(destination = PreferenceNavDestination.Frontend) {
        PreferenceLink(
            destination = PreferenceNavDestination.FrontendInterface,
            onNavigateTo = onNavigateTo
        )
        PreferenceLink(
            destination = PreferenceNavDestination.FrontendSearch,
            onNavigateTo = onNavigateTo
        )
    }
}