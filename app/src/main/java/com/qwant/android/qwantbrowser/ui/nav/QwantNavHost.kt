package com.qwant.android.qwantbrowser.ui.nav

import android.os.Build
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
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
    val transitionTimeMs = 400
    val offsetForBrowserScreen = { fullSize: Int -> fullSize / 5 }

    NavHost(
        navController = navController,
        startDestination = NavDestination.Browser.match,
        modifier = modifier,
        enterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Left,
                animationSpec = tween(durationMillis = transitionTimeMs)
            )
        },
        exitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = transitionTimeMs)
            )
        },
        popEnterTransition = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = transitionTimeMs)
            )
        },
        popExitTransition = {
            slideOutOfContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Right,
                animationSpec = tween(durationMillis = transitionTimeMs)
            )
        }
    ) {
        composable(
            route = NavDestination.Browser.match,
            arguments = NavDestination.Browser.arguments,
            enterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = transitionTimeMs),
                    initialOffset = offsetForBrowserScreen
                )
            },
            popEnterTransition = {
                slideIntoContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Right,
                    animationSpec = tween(durationMillis = transitionTimeMs),
                    initialOffset = offsetForBrowserScreen
                )
            },
            exitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = transitionTimeMs),
                    targetOffset = offsetForBrowserScreen
                )
            },
            popExitTransition = {
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Left,
                    animationSpec = tween(durationMillis = transitionTimeMs),
                    targetOffset = offsetForBrowserScreen
                )
            }
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
        composable(NavDestination.Tabs.match) {
            TabsScreen(
                appViewModel = appViewModel,
                onClose = { openNewTab ->
                    navController.navigateSingleTopTo(NavDestination.Browser.route(openNewTab) )
                }
            )
        }
        composable(NavDestination.History.match) {
            HistoryScreen(onClose = onBrowse)
        }
        composable(NavDestination.Bookmarks.match) {
            BookmarksScreen(onBrowse, appViewModel)
        }
        composable(NavDestination.Preferences.match) {
            PreferencesScreen(onClose = onBrowse, applicationViewModel = appViewModel)
        }
    }
}