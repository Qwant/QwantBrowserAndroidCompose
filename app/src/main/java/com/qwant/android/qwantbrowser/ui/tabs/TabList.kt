package com.qwant.android.qwantbrowser.ui.tabs

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.thumbnails.storage.ThumbnailStorage
import com.qwant.android.qwantbrowser.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun TabList(
    tabs: List<TabSessionState>,
    selectedTabId: String?,
    thumbnailStorage: ThumbnailStorage,
    onTabSelected: (tab: TabSessionState) -> Unit,
    onTabDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        itemsIndexed(tabs, key = { _, tab -> tab.id }) { _, tab ->
            val currentTab by rememberUpdatedState(newValue = tab)
            val dismissState = rememberDismissState(
                confirmValueChange = { dismissValue ->
                    var result = true
                    if (dismissValue == DismissValue.DismissedToStart) {
                        onTabDeleted(currentTab)
                        result = false
                    } else if (dismissValue == DismissValue.DismissedToEnd) {
                        // TODO bookmark tab !
                        result = false
                    }
                    result
                },
                positionalThreshold = { 92.dp.toPx() } // { it / 3 }
            )

            val haptic = LocalHapticFeedback.current
            LaunchedEffect(key1 = dismissState.targetValue) {
                if (dismissState.targetValue != DismissValue.Default) {
                    haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                }
            }

            SwipeToDismiss(
                state = dismissState,
                background = {
                    val color by animateColorAsState(
                        when (dismissState.targetValue) {
                            DismissValue.Default -> MaterialTheme.colorScheme.secondaryContainer
                            DismissValue.DismissedToEnd -> MaterialTheme.colorScheme.primaryContainer
                            DismissValue.DismissedToStart -> MaterialTheme.colorScheme.error
                        }, label = "tabSwipeColor"
                    )
                    val iconSize by animateDpAsState(
                        targetValue = when (dismissState.targetValue) {
                            DismissValue.Default -> 24.dp
                            DismissValue.DismissedToEnd -> 36.dp
                            DismissValue.DismissedToStart -> 36.dp
                        }, label = "tabSwipeIconSize"
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.icons_add_bookmark), // TODO Tab swipe icons
                            contentDescription = "Bookmark icon",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(iconSize)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.icons_close_circled), // TODO Tab swipe icons
                            contentDescription = "Delete icon",
                            modifier = Modifier
                                .align(Alignment.CenterEnd)
                                .size(iconSize)
                        )
                    }
                },
                dismissContent = {
                    TabRow(
                        tab = tab,
                        selected = tab.id == selectedTabId,
                        thumbnailStorage = thumbnailStorage,
                        onSelected = onTabSelected,
                        onDeleted = onTabDeleted
                    )
                },
                modifier = Modifier.animateItemPlacement()
            )
        }
    }
}
