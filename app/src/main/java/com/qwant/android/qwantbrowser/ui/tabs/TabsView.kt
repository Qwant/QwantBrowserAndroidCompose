package com.qwant.android.qwantbrowser.ui.tabs

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.qwant.android.qwantbrowser.preferences.app.TabsViewOption
import com.qwant.android.qwantbrowser.ui.widgets.EmptyPagePlaceholder
import mozilla.components.browser.icons.BrowserIcons
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import com.qwant.android.qwantbrowser.R

@Composable
fun TabView(
    tabs: List<TabSessionState>,
    private: Boolean,
    selectedTabId: String?,
    thumbnailStorage: ThumbnailStorage,
    browserIcons: BrowserIcons,
    onTabSelected: (tab: TabSessionState) -> Unit,
    onTabDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier,
    tabsViewOption: TabsViewOption = TabsViewOption.LIST
) {
    if (tabs.isNotEmpty()) {
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
    } else {
        val privateMode = remember { private }
        EmptyPagePlaceholder(
            icon = if (privateMode) R.drawable.icons_privacy_mask else R.drawable.icons_tab_smiley,
            title = stringResource(id = if (privateMode) R.string.browser_tabs_empty_title_private else R.string.browser_tabs_empty_title),
            subtitle = stringResource(id = if (privateMode) R.string.browser_tabs_empty_subtitle_private else R.string.browser_tabs_empty_subtitle)
        )
    }
}