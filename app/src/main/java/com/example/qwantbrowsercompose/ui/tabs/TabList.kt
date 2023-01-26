package com.example.qwantbrowsercompose.ui.tabs

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.lib.state.ext.observeAsComposableState

@Composable
fun TabList(
    store: BrowserStore,
    private: Boolean,
    // thumbnailStorage: ThumbnailStorage,
    onTabSelected: (tab: TabSessionState) -> Unit,
    onTabDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier,
) {
    val tabs = store.observeAsComposableState { state -> state.tabs }

    val filteredTabs by remember(private) {
        derivedStateOf { tabs.value?.filter { it.content.private == private } ?: emptyList() }
    }

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(filteredTabs) { tab ->
            TabRow(
                tab = tab,
                // thumbnailStorage = thumbnailStorage,
                onSelected = onTabSelected,
                onDeleted = onTabDeleted
            )
        }
    }
}
