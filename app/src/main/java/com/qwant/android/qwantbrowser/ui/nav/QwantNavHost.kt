package com.qwant.android.qwantbrowser.ui.nav

import android.os.Build
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.qwant.android.qwantbrowser.ext.navigateSingleTopTo
import com.qwant.android.qwantbrowser.ui.QwantApplicationViewModel
import com.qwant.android.qwantbrowser.ui.bookmarks.BookmarksScreen
import com.qwant.android.qwantbrowser.ui.browser.BrowserScreen
import com.qwant.android.qwantbrowser.ui.browser.TabOpening
import com.qwant.android.qwantbrowser.ui.history.HistoryScreen
import com.qwant.android.qwantbrowser.ui.preferences.PreferencesScreen
import com.qwant.android.qwantbrowser.ui.tabs.TabsScreen

@Composable
fun QwantNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    appViewModel: QwantApplicationViewModel = hiltViewModel(),
) {
    val onBrowse = { navController.navigateSingleTopTo(NavDestination.Browser.route()) }

    NavHost(
        navController = navController,
        startDestination = NavDestination.Browser.match,
        modifier = modifier
    ) {
        composable(
            NavDestination.Browser.match,
            arguments = NavDestination.Browser.arguments
        ) { backStackEntry ->
            val openNewTab = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                backStackEntry.arguments?.getSerializable("openNewTab", TabOpening::class.java) ?: TabOpening.NONE
            } else {
                try {
                    @Suppress("DEPRECATION")
                    backStackEntry.arguments?.getSerializable("openNewTab") as TabOpening
                } catch (_: Exception) {
                    TabOpening.NONE
                }
            }
            BrowserScreen(
                navigateTo = { destination -> navController.navigateSingleTopTo(destination.route()) },
                appViewModel = appViewModel,
                openNewTab = openNewTab
            )
        }
        composable(NavDestination.Tabs.match) { TabsScreen(
            appViewModel = appViewModel,
            onClose = { openNewTab ->
                navController.navigateSingleTopTo(NavDestination.Browser.route(openNewTab) )
            }
        ) }
        composable(NavDestination.History.match) { HistoryScreen(onClose = onBrowse) }
        composable(NavDestination.Bookmarks.match) { BookmarksScreen(onBrowse, appViewModel) }
        composable(NavDestination.Preferences.match) { PreferencesScreen(onClose = onBrowse) }
    }
}