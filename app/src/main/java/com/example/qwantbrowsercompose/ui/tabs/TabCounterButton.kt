package com.example.qwantbrowsercompose.ui.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.lib.state.ext.observeAsComposableState

private const val MAX_VISIBLE_TABS = 99
private const val SO_MANY_TABS_OPEN = "∞"

@Composable
fun TabCounterButton(
    store: BrowserStore,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier,
    tabsFilter: (TabSessionState) -> Boolean = { true }
) {
    Box(modifier
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClicked() }
    ) {
        val tabs = store.observeAsComposableState { state -> state.tabs.filter(tabsFilter) }
        val count = tabs.value?.size ?: 0

        Text(
            text = if (count > MAX_VISIBLE_TABS) SO_MANY_TABS_OPEN else count.toString(),
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .border(width = 2.dp, color = Color.White)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}