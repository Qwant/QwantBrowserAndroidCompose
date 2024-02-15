package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.engine.mediaquery.PreferredColorScheme

@Composable
fun EngineSettingsFeature(
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
    engine: Engine
) {
    val appearance by appViewModel.appearance.collectAsState()

    LaunchedEffect(appearance) {
        engine.settings.preferredColorScheme = when (appearance) {
            Appearance.LIGHT -> PreferredColorScheme.Light
            Appearance.DARK -> PreferredColorScheme.Dark
            else -> PreferredColorScheme.System
        }
    }
}