@file:OptIn(ExperimentalMaterial3Api::class)

package com.qwant.android.qwantbrowser.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.qwant.android.qwantbrowser.ext.navigateSingleTopTo
import com.qwant.android.qwantbrowser.ui.nav.MainMenuNavBar
import com.qwant.android.qwantbrowser.ui.nav.NavDestination
import com.qwant.android.qwantbrowser.ui.nav.NavDestinations
import com.qwant.android.qwantbrowser.ui.nav.QwantNavHost
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun QwantBrowserApp(
    applicationViewModel: QwantApplicationViewModel = hiltViewModel()
) {
    val navController = rememberNavController()
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentScreen = NavDestinations.find {
        currentBackStack?.destination?.route?.startsWith(it.route) == true
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
                modifier = Modifier.padding(innerPadding),
                appViewModel = applicationViewModel
            )
        }
    }
}


