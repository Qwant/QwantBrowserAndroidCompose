package com.qwant.android.qwantbrowser.ui.nav

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Pageview
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Pageview
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.twotone.Bookmark
import androidx.compose.ui.graphics.vector.ImageVector
import com.qwant.android.qwantbrowser.R

sealed class NavDestination(
    val route: String,
    @StringRes val label: Int,
    val icon: ImageVector? = null,
    val selectedIcon: ImageVector? = icon
) {
    object Browser : NavDestination(
        route = "browse",
        label = R.string.browser_search,
        icon = Icons.Outlined.Pageview,
        selectedIcon = Icons.Filled.Pageview
    )
    object Tabs : NavDestination(
        route = "tabs",
        label = R.string.tabs
    )
    object Bookmarks : NavDestination(
        route = "bookmarks",
        label = R.string.bookmarks,
        icon = Icons.TwoTone.Bookmark,
        selectedIcon = Icons.Filled.Bookmark
    )
    object Preferences : NavDestination(
        route = "preferences",
        label = R.string.settings,
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )
    object History : NavDestination(
        route = "history",
        label = R.string.app_name
    )
}

// Those are needed by menu bars
val NavDestinations = listOf(
    NavDestination.Browser,
    NavDestination.Tabs,
    NavDestination.History,
    NavDestination.Bookmarks,
    NavDestination.Preferences
)
val NavDestinationsForNavigationBar = listOf(
    NavDestination.Browser,
    NavDestination.Bookmarks,
    NavDestination.Tabs,
    NavDestination.Preferences
)