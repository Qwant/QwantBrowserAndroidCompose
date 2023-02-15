package com.qwant.android.qwantbrowser.ui.nav

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qwant.android.qwantbrowser.ext.navigateSingleTopTo
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferences
import com.qwant.android.qwantbrowser.preferences.frontend.FrontEndPreferencesViewModel
import com.qwant.android.qwantbrowser.ui.preferences.preferencesGraph
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.browser.BrowserScreen
import com.qwant.android.qwantbrowser.ui.tabs.TabsScreen

@Composable
fun QwantNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
) {
    NavHost(
        navController = navController,
        startDestination = NavDestination.Browser.route,
        modifier = modifier
    ) {
        composable(NavDestination.Browser.route) { BrowserScreen() }
        composable(NavDestination.Tabs.route) { TabsScreen(
            appViewModel = appViewModel,
            onClose = { navController.navigateSingleTopTo(NavDestination.Browser.route) }
        ) }
        composable(NavDestination.History.route) { Text("History") }
        composable(NavDestination.Bookmarks.route) { Text("Bookmarks") }
        preferencesGraph(navController, NavDestination.Preferences.route)
        // composable(NavDestination.Settings.route) { TestPrefs() }
    }
}

@Composable
fun TestPrefs(
    viewModel: FrontEndPreferencesViewModel = hiltViewModel()
) {
    val prefs by viewModel.flow.collectAsState(initial = FrontEndPreferences.getDefaultInstance())

    Button(onClick = { viewModel.updateShowNews(!prefs.showNews) }) {
        Text(text = prefs.showNews.toString())
    }
}