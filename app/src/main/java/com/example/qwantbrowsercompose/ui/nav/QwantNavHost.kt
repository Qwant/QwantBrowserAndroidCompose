package com.example.qwantbrowsercompose.ui.nav

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.qwantbrowsercompose.ui.PrivacyMode
import com.example.qwantbrowsercompose.ext.navigateSingleTopTo
import com.example.qwantbrowsercompose.preferences.frontend.FrontEndPreferences
import com.example.qwantbrowsercompose.preferences.frontend.FrontEndPreferencesViewModel
import com.example.qwantbrowsercompose.ui.QwantApplicationViewModel
import com.example.qwantbrowsercompose.ui.browser.BrowserScreen
import com.example.qwantbrowsercompose.ui.tabs.TabsScreen

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
        composable(NavDestination.Settings.route) { TestPrefs() }
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