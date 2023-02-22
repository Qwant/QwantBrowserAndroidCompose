package com.qwant.android.qwantbrowser.ui.preferences

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.qwant.android.qwantbrowser.R


sealed class PreferenceNavDestination(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector
) {
    object Main : PreferenceNavDestination(route = "main", label = R.string.settings, Icons.Default.Settings)
    object Frontend : PreferenceNavDestination(route = "frontend", label = R.string.settings_frontend_title, Icons.Default.Web)
    object FrontendInterface : PreferenceNavDestination(route = "frontend/interface", label = R.string.settings_frontend_interface_title, Icons.Default.SmartScreen)
    object FrontendSearch : PreferenceNavDestination(route = "frontend/search", label = R.string.settings_frontend_search_title, Icons.Default.Search)
    object Engine : PreferenceNavDestination(route = "engine", label = R.string.settings_engine_title, Icons.Default.Engineering)
}