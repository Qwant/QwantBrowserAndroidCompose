package com.qwant.android.qwantbrowser.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.QwantUrlEngineSyncFeature
import com.qwant.android.qwantbrowser.ui.nav.QwantNavHost
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import com.qwant.android.qwantbrowser.ext.navigateSingleTopTo
import com.qwant.android.qwantbrowser.ui.nav.NavDestination

@Composable
fun QwantBrowserApp(
    intent_action: String? = null,
    applicationViewModel: QwantApplicationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()

    val isPrivate by applicationViewModel.isPrivate.collectAsState()
    val appearance by applicationViewModel.appearance.collectAsState()
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

    // TODO add all things needed before first display
    // - toolbar position
    if (appearance != null && appearance != Appearance.UNRECOGNIZED) {
        homeUrl?.let {
            // TODO remember this for recompositions somehow
            QwantUrlEngineSyncFeature(
                it,
                qwantTabs,
                applicationViewModel.loadUrlUseCase
            )
        }

        QwantBrowserTheme(
            darkTheme = darkTheme,
            privacy = isPrivate
        ) {
            Scaffold(
                snackbarHost = { SnackbarHost(applicationViewModel.snackbarHostState) },
            ) {
                QwantNavHost(
                    navController = navController,
                    appViewModel = applicationViewModel
                )

                LaunchedEffect(intent_action) {
                    if (intent_action == "CHANGED_LANGUAGE") {
                        navController.navigateSingleTopTo(NavDestination.Preferences.route())
                    }
                }
            }
        }
    } else {
        // TODO splash screen
        Text("Splash screen")
    }
}


