package com.qwant.android.qwantbrowser.ui.preferences

import androidx.compose.runtime.Composable
import com.qwant.android.qwantbrowser.ui.preferences.BasePreferenceScreen
import com.qwant.android.qwantbrowser.ui.preferences.PreferenceNavDestination
import com.qwant.android.qwantbrowser.ui.preferences.widgets.PreferenceLink

@Composable
fun MainPreferencesScreen(
    onNavigateTo: (PreferenceNavDestination) -> Unit
) {
    BasePreferenceScreen(destination = PreferenceNavDestination.Main) {
        PreferenceLink(
            destination = PreferenceNavDestination.App,
            onNavigateTo = onNavigateTo
        )
        PreferenceLink(
            destination = PreferenceNavDestination.Frontend,
            onNavigateTo = onNavigateTo
        )
        PreferenceLink(
            destination = PreferenceNavDestination.Engine,
            onNavigateTo = onNavigateTo
        )
    }
}