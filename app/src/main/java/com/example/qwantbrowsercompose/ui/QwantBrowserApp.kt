@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.qwantbrowsercompose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qwantbrowsercompose.ext.navigateSingleTopTo
import com.example.qwantbrowsercompose.ui.nav.MainMenuNavBar
import com.example.qwantbrowsercompose.ui.nav.NavDestination
import com.example.qwantbrowsercompose.ui.nav.NavDestinations
import com.example.qwantbrowsercompose.ui.nav.QwantNavHost
import com.example.qwantbrowsercompose.ui.theme.QwantBrowserTheme
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.lib.state.ext.observeAsComposableState

@Composable
fun QwantBrowserApp(
    applicationViewModel: QwantApplicationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentScreen = NavDestinations.find {
        it.route == currentBackStack?.destination?.route
    } ?: NavDestination.Browser

    val isPrivate by applicationViewModel.isPrivate.collectAsState()

    QwantBrowserTheme(
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
                // onPrivacyChanged = { mode -> applicationViewModel.setPrivacyMode(mode) },
                modifier = Modifier.padding(innerPadding),
                appViewModel = applicationViewModel
            )
        }
    }
}


