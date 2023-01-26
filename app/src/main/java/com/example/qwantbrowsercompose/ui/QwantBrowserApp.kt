@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.qwantbrowsercompose.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.qwantbrowsercompose.PrivacyMode
import com.example.qwantbrowsercompose.QwantApplicationViewModel
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

    val selectedTabPrivacy by applicationViewModel.core.store.observeAsComposableState { state -> state.selectedTab?.content?.private ?: false }
    val privacy by remember { derivedStateOf {
        when (applicationViewModel.privacyMode) {
            PrivacyMode.NORMAL -> false
            PrivacyMode.PRIVATE -> true
            PrivacyMode.SELECTED_TAB_PRIVACY -> selectedTabPrivacy ?: false
        }
    } }

    QwantBrowserTheme(
        privacy = privacy
    ) {
        Scaffold(bottomBar = {
            MainMenuNavBar(
                currentScreen = currentScreen,
                onTabSelected = { destination -> navController.navigateSingleTopTo(destination.route) }
            )
        }) { innerPadding ->
            QwantNavHost(
                navController = navController,
                onPrivacyChanged = { mode -> applicationViewModel.privacyMode = mode },
                modifier = Modifier.padding(innerPadding)
            )
        }
    }
}


