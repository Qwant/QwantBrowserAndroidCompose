package com.qwant.android.qwantbrowser.ui.browser.pullToRefresh

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshBox(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: () -> Boolean = { true },
    content: @Composable BoxScope.() -> Unit
) {
    var pullToRefreshRunning = false
    val pullRefreshState = rememberPullToRefreshState()

    Box(modifier.pointerInput(true) {
        detectVerticalDragGesturesUnconsumed(
            onDragStart = {},
            onDragEnd = {
                if (pullRefreshState.progress == 1f) {
                    onRefresh()
                }
                pullToRefreshRunning = false
                pullRefreshState.animateOffset(0f, 200)
            },
            onDragCancel = {
                pullToRefreshRunning = false
                pullRefreshState.animateOffset(0f, 200)
            },
            onVerticalDrag = { change, dragAmount ->
                if (dragAmount > 0 && (pullToRefreshRunning || enabled())) {
                    pullRefreshState.addOffset(dragAmount)
                    pullToRefreshRunning = true
                    change.consume()
                } else if (dragAmount < 0 && pullToRefreshRunning) {
                    pullRefreshState.addOffset(dragAmount)
                    if (pullRefreshState.verticalOffset == 0f) {
                        pullToRefreshRunning = false
                    }
                    change.consume()
                }
            }
        )
    }) {
        content()

        val outlineColor by animateColorAsState(
            targetValue = if (pullRefreshState.progress == 1f)
                MaterialTheme.colorScheme.primary
            else
                MaterialTheme.colorScheme.outline,
            animationSpec = tween(100),
            label = "pullToRefreshColor"
        )

        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier
                .align(Alignment.TopCenter)
                .offset(y = (-5).dp),
            indicator = { state -> PullToRefreshDefaults.Indicator(
                state = state,
                modifier = Modifier.border(
                    width = 1.dp,
                    color = outlineColor,
                    shape = CircleShape
                )
            ) },
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}