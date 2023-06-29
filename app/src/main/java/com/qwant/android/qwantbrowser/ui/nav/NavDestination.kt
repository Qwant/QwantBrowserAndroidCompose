package com.qwant.android.qwantbrowser.ui.nav

import androidx.annotation.StringRes
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.TabOpening


sealed class NavDestination(
    val match: String,
    @StringRes val label: Int,
    val arguments: List<NamedNavArgument> = listOf()
) {
    open fun route() : String { return this.match }

    object Browser : NavDestination(
        match = "browse?openNewTab={openNewTab}",
        arguments = listOf(navArgument("openNewTab") {
            type = NavType.EnumType(TabOpening::class.java)
            defaultValue = TabOpening.NONE
        }),
        label = R.string.browser_search
    ) {
        override fun route(): String { return this.route(TabOpening.NONE) }
        fun route(openNewTab: TabOpening): String {
            return "browse?openNewTab=${openNewTab}"
        }
    }
    object Tabs : NavDestination(
        match = "tabs",
        label = R.string.tabs
    )
    object Bookmarks : NavDestination(
        match = "bookmarks",
        label = R.string.bookmarks
    )
    object Preferences : NavDestination(
        match = "preferences",
        label = R.string.settings
    )
    object History : NavDestination(
        match = "history",
        label = R.string.app_name
    )
}

val NavDestinations = listOf(
    NavDestination.Browser,
    NavDestination.Tabs,
    NavDestination.History,
    NavDestination.Bookmarks,
    NavDestination.Preferences
)