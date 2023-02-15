@file:OptIn(ExperimentalMaterial3Api::class)

package com.qwant.android.qwantbrowser.ui.tabs


import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.unit.dp
import mozilla.components.browser.state.state.TabSessionState

const val LOGTAG = "QB_TABLIST"

@Composable
fun TabList(
    list: List<TabSessionState>,
    selectedTabId: String?,
    onTabSelected: (tab: TabSessionState) -> Unit,
    onTabDeleted: (tab: TabSessionState) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 200.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        items(list.reversed()) { tab ->
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
                        }
                    )
                    val iconSize by animateDpAsState(
                        targetValue = when (dismissState.targetValue) {
                            DismissValue.Default -> 24.dp
                            DismissValue.DismissedToEnd -> 36.dp
                            DismissValue.DismissedToStart -> 36.dp
                        }
                    )

                    Box(
                        Modifier
                            .fillMaxSize()
                            .background(color)
                            .padding(horizontal = 20.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Star,
                            contentDescription = "Bookmark icon",
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .size(iconSize)
                        )
                        Icon(
                            Icons.Outlined.Delete,
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
                        onSelected = onTabSelected,
                        onDeleted = onTabDeleted
                    )
                }
            )
        }
    }
}
