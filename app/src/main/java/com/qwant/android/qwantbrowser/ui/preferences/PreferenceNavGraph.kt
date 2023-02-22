package com.qwant.android.qwantbrowser.ui.preferences

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.qwant.android.qwantbrowser.ui.preferences.screens.BasePreferenceScreen
import com.qwant.android.qwantbrowser.ui.preferences.screens.FrontEndPreferencesScreen
import com.qwant.android.qwantbrowser.ui.preferences.screens.FrontendInterfacePreferencesScreen
import com.qwant.android.qwantbrowser.ui.preferences.screens.MainPreferencesScreen


fun NavGraphBuilder.preferencesGraph(navController: NavController, route: String) {
    val onNavigateTo = { destination: PreferenceNavDestination ->
        navController.navigate(route = "$route/${destination.route}")
    }

    navigation(startDestination = "$route/${PreferenceNavDestination.Main.route}", route = route) {
        composable("$route/${PreferenceNavDestination.Main.route}") {
            MainPreferencesScreen(onNavigateTo = onNavigateTo)
        }
        composable("$route/${PreferenceNavDestination.Frontend.route}") {
            FrontEndPreferencesScreen(onNavigateTo = onNavigateTo)
        }
        composable("$route/${PreferenceNavDestination.FrontendSearch.route}") {
            BasePreferenceScreen(destination = PreferenceNavDestination.FrontendSearch)
        }
        composable("$route/${PreferenceNavDestination.FrontendInterface.route}") {
            FrontendInterfacePreferencesScreen()
        }
        composable("$route/${PreferenceNavDestination.Engine.route}") {
            BasePreferenceScreen(destination = PreferenceNavDestination.Engine)
        }
    }
}