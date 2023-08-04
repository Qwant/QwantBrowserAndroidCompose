package com.qwant.android.qwantbrowser.ui.tabs

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.qwant.android.qwantbrowser.preferences.app.TabsViewOption
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage

@Composable
fun TabView(
    tabs: List<TabSessionState>,
    selectedTabId: String?,
    thumbnailStorage: ThumbnailStorage,
    browserIcons: BrowserIcons,
    onTabSelected: (tab: TabSessionState) -> Unit,
    onTabDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier,
    tabsViewOption: TabsViewOption = TabsViewOption.LIST
) {
    when (tabsViewOption) {
        TabsViewOption.LIST -> TabList(
            tabs = tabs,
            selectedTabId = selectedTabId,
            thumbnailStorage = thumbnailStorage,
            onTabSelected = onTabSelected,
            onTabDeleted = onTabDeleted,
            modifier = modifier
        )
        TabsViewOption.GRID -> TabGrid(
            tabs = tabs,
            selectedTabId = selectedTabId,
            thumbnailStorage = thumbnailStorage,
            browserIcons = browserIcons,
            onTabSelected = onTabSelected,
            onTabDeleted = onTabDeleted,
            modifier = modifier
        )
        TabsViewOption.UNRECOGNIZED -> {}
    }
}