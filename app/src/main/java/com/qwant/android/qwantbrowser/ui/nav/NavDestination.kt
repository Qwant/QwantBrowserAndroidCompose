package com.qwant.android.qwantbrowser.ui.nav

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.qwant.android.qwantbrowser.ui.browser.TabOpening


sealed class NavDestination(
    val match: String,
    val arguments: List<NamedNavArgument> = listOf()
) {
    open fun route() : String { return this.match }

    object Browser : NavDestination(
        match = "browse?openNewTab={openNewTab}",
        arguments = listOf(navArgument("openNewTab") {
            type = NavType.EnumType(TabOpening::class.java)
            defaultValue = TabOpening.NONE
        })
    ) {
        override fun route(): String { return this.route(TabOpening.NONE) }
        fun route(openNewTab: TabOpening): String {
            return "browse?openNewTab=${openNewTab}"
        }
    }
    object Tabs : NavDestination(match = "tabs")
    object Bookmarks : NavDestination(match = "bookmarks")
    object Preferences : NavDestination(match = "preferences")
    object History : NavDestination(match = "history")
}