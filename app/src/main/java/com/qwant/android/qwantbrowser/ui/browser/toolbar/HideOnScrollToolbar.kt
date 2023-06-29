package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import kotlin.math.roundToInt

enum class HideOnScrollPosition {
    Top, Bottom
}

@Composable
fun HideOnScrollToolbar(
    toolbarState: ToolbarState,
    toolbar: @Composable (Modifier) -> Unit,
    height: Dp,
    modifier: Modifier = Modifier,
    content: @Composable (Modifier) -> Unit = {}
) {
    val position by toolbarState.toolbarPosition.collectAsState()
    val shouldHideOnScroll by toolbarState.shouldHideOnScroll.collectAsState()

    val heightPx = with(LocalDensity.current) { height.roundToPx() }

    val offset by animateFloatAsState(
        targetValue = if (toolbarState.visible || !shouldHideOnScroll) {
            0f
        } else {
            if (position == HideOnScrollPosition.Top) {
                -heightPx.toFloat()
            } else {
                heightPx.toFloat()
            }
        },
        animationSpec = tween(durationMillis = 500)
    )

    val trueHeight by remember(heightPx, offset) {
        derivedStateOf {
            if (position == HideOnScrollPosition.Top) {
                heightPx + offset.roundToInt()
            } else {
                heightPx - offset.roundToInt()
            }
        }
    }

    LaunchedEffect(shouldHideOnScroll, trueHeight) {
        toolbarState.updateTrueHeightPx(trueHeight)
    }

    Box(modifier = modifier) {
        toolbar(Modifier
            .fillMaxWidth()
            .align(if (position == HideOnScrollPosition.Top) Alignment.TopCenter else Alignment.BottomCenter)
            .zIndex(2f)
            .then(
                if (shouldHideOnScroll) { Modifier
                    .offset { IntOffset(x = 0, y = offset.roundToInt()) }
                } else Modifier
            )
        )

        content(Modifier
            .nestedScroll(rememberThresholdNestedScrollConnection(
                onScroll = { sign -> toolbarState.updateVisibility(sign == 1f) },
                scrollThreshold = if (position == HideOnScrollPosition.Top) 10 else 1,
                consecutiveThreshold = if (position == HideOnScrollPosition.Top) 4 else 1
            ))
            .fillMaxSize()
            .zIndex(1f)
            .then(
                if (shouldHideOnScroll) {
                    if (position == HideOnScrollPosition.Top) { Modifier
                        .offset { IntOffset(x = 0, y = trueHeight) }
                    } else Modifier
                } else { Modifier
                    .absolutePadding(
                        top = if (position == HideOnScrollPosition.Top) height else 0.dp,
                        bottom = if (position == HideOnScrollPosition.Bottom) height else 0.dp
                    )
                }
            )
        )
    }
}