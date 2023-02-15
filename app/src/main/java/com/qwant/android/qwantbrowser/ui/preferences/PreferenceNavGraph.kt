package com.qwant.android.qwantbrowser.ui.preferences

import androidx.compose.material3.Text
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.qwant.android.qwantbrowser.ui.preferences.screens.MainPreferencesScreen

internal const val main = "main"
internal const val frontend = "frontend"
internal const val engine = "engine"

fun NavGraphBuilder.preferencesGraph(navController: NavController, route: String) {
    navigation(startDestination = "$route/$main", route = route) {
        composable("$route/$main") { MainPreferencesScreen(
            onFrontendClicked = { navController.navigate("$route/$frontend") },
            onEngineClicked = { navController.navigate("$route/$engine") }
        ) }
        composable("$route/$frontend") { Text("Preferences frontend") }
        composable("$route/$engine") { Text("Preferences engine") }
    }
}