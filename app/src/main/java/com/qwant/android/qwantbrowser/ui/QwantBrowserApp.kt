package com.qwant.android.qwantbrowser.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.QwantUrlEngineSyncFeature
import com.qwant.android.qwantbrowser.ui.nav.QwantNavHost
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.ui.Modifier
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.ui.zap.ZapFeature

@Composable
fun QwantBrowserApp(
    applicationViewModel: QwantApplicationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val isPrivate by applicationViewModel.isPrivate.collectAsState()
    val appearance by applicationViewModel.appearance.collectAsState()
    val toolbarPosition by applicationViewModel.toolbarPosition.collectAsState()

    val systemTheme = isSystemInDarkTheme()
    val darkTheme by remember(appearance, systemTheme) { derivedStateOf {
        when (appearance) {
            Appearance.LIGHT -> false
            Appearance.DARK -> true
            Appearance.SYSTEM_SETTINGS -> systemTheme
            else -> false
        }
    } }

    val homeUrl by applicationViewModel.homeUrl.collectAsState()
    val qwantTabs by applicationViewModel.qwantTabs.collectAsState()

    if (appearance != null && appearance != Appearance.UNRECOGNIZED
        && toolbarPosition != ToolbarPosition.UNRECOGNIZED
    ) {
        homeUrl?.let {
            // TODO remember this for recompositions somehow
            QwantUrlEngineSyncFeature(
                it,
                qwantTabs,
                applicationViewModel.loadUrlUseCase,
                applicationViewModel.getQwantUrlUseCase
            )
        }

        QwantBrowserTheme(
            darkTheme = darkTheme,
            privacy = isPrivate
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(applicationViewModel.snackbarHostState) },
            ) { scaffoldPadding ->
                QwantNavHost(
                    navController = navController,
                    appViewModel = applicationViewModel,
                    modifier = Modifier.padding(scaffoldPadding)
                )
                ZapFeature(state = applicationViewModel.zapState)
            }
        }
    } else {
        // TODO splash screen ?
    }
}


