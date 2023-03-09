@file:OptIn(ExperimentalMaterial3Api::class)

package com.qwant.android.qwantbrowser.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qwant.android.qwantbrowser.ext.navigateSingleTopTo
import com.qwant.android.qwantbrowser.preferences.frontend.Appearance
import com.qwant.android.qwantbrowser.preferences.frontend.QwantUrlEngineSyncFeature
import com.qwant.android.qwantbrowser.ui.nav.MainMenuNavBar
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.nav.NavDestinations
import com.qwant.android.qwantbrowser.ui.nav.QwantNavHost
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun QwantBrowserApp(
    intent_action: String? = null,
    applicationViewModel: QwantApplicationViewModel = hiltViewModel()
) {
    // TODO move navController and current screen to viewmodel
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentScreen = NavDestinations.find {
        currentBackStack?.destination?.route?.startsWith(it.route) == true
    } ?: NavDestination.Browser

    LaunchedEffect(key1 = true) {
        if (intent_action == "CHANGED_LANGUAGE") {
            // navController.restoreState()
            // navController.navigate(NavDestination.Preferences.route + "/" + PreferenceNavDestination.FrontendInterface.route) {
                // restoreState = true
                // TODO this does not work. explore navController methods and options to restore full settings backstack on language change
            // }
        }
    }

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

    if (appearance != null && appearance != Appearance.UNRECOGNIZED) {
        homeUrl?.let {
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
            Scaffold(bottomBar = {
                MainMenuNavBar(
                    currentScreen = currentScreen,
                    onTabSelected = { destination ->
                        navController.navigateSingleTopTo(destination.route)
                    }
                )
            }) { innerPadding ->
                QwantNavHost(
                    navController = navController,
                    modifier = Modifier.padding(innerPadding),
                    appViewModel = applicationViewModel
                )
            }
        }
    } else {
        // TODO splash screen
        Text("Splash screen")
    }
}


