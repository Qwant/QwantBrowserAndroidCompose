package com.qwant.android.qwantbrowser.ui.preferences

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.qwant.android.qwantbrowser.ui.preferences.app.AppPreferencesScreen
import com.qwant.android.qwantbrowser.ui.preferences.frontend.FrontEndPreferencesScreen
import com.qwant.android.qwantbrowser.ui.preferences.frontend.ux.FrontendInterfacePreferencesScreen
import com.qwant.android.qwantbrowser.ui.preferences.frontend.search.FrontendSearchPreferencesScreen


fun NavGraphBuilder.preferencesGraph(navController: NavController, route: String) {
    val onNavigateTo = { destination: PreferenceNavDestination ->
        navController.navigate(route = "$route/${destination.route}")
    }

    navigation(startDestination = "$route/${PreferenceNavDestination.Main.route}", route = route) {
        composable("$route/${PreferenceNavDestination.Main.route}") {
            MainPreferencesScreen(onNavigateTo = onNavigateTo)
        }
        composable("$route/${PreferenceNavDestination.App.route}") {
            AppPreferencesScreen()
        }
        composable("$route/${PreferenceNavDestination.Frontend.route}") {
            FrontEndPreferencesScreen(onNavigateTo = onNavigateTo)
        }
        composable("$route/${PreferenceNavDestination.FrontendSearch.route}") {
            FrontendSearchPreferencesScreen()
        }
        composable("$route/${PreferenceNavDestination.FrontendInterface.route}") {
            FrontendInterfacePreferencesScreen()
        }
        composable("$route/${PreferenceNavDestination.Engine.route}") {
            BasePreferenceScreen(destination = PreferenceNavDestination.Engine)
        }
    }
}