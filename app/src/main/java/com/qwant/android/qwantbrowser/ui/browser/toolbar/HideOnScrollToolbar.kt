package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import mozilla.components.concept.engine.EngineView
import kotlin.math.roundToInt
import kotlin.math.sign

enum class HideOnScrollPosition {
    Top, Bottom
}

@Composable
fun HideOnScrollToolbar(
    toolbarState: ToolbarState,
    toolbar: @Composable (Modifier) -> Unit,
    engineView: EngineView?,
    height: Dp,
    modifier: Modifier = Modifier,
    lock: () -> Boolean = { false },
    content: @Composable (Modifier) -> Unit = {}
) {
    val toolbarPosition by toolbarState.toolbarPosition.collectAsState()
    val position = remember(toolbarPosition) {
        when (toolbarPosition) {
            ToolbarPosition.BOTTOM -> HideOnScrollPosition.Bottom
            else -> HideOnScrollPosition.Top
        }
    }

    val shouldHideOnScroll by toolbarState.shouldHideOnScroll.collectAsState()

    val heightPx = with(LocalDensity.current) { height.roundToPx() }

    val offset by animateFloatAsState(
        targetValue = if (toolbarState.visible || (!shouldHideOnScroll && !lock())) {
            0f
        } else {
            if (position == HideOnScrollPosition.Top) {
                -heightPx.toFloat()
            } else {
                heightPx.toFloat()
            }
        },
        animationSpec = tween(durationMillis = 500),
        label = "hideOnScrollOffset"
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

    val nestedScrollConnection = rememberThresholdNestedScrollConnection(
        onScroll = { sign ->
            if (engineView?.canScrollVerticallyUp() == false) {
                toolbarState.updateVisibility(true)
            } else {
                toolbarState.updateVisibility(sign == 1f)
            }
        },
        scrollThreshold = if (position == HideOnScrollPosition.Top) 5 else 1,
        consecutiveThreshold = 1 // if (position == HideOnScrollPosition.Top) 4 else 1
    )

//    val nestedScrollConnection = remember {
//        object: NestedScrollConnection {
//            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
//                toolbarState.updateVisibility(available.y.sign == 1f)
//                return Offset.Zero
//            }
//        }
//    }

    LaunchedEffect(shouldHideOnScroll, trueHeight) {
        toolbarState.updateTrueHeightPx(trueHeight)
    }

    Box(modifier = modifier) {
        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .zIndex(100f)
                .align(if (position == HideOnScrollPosition.Top) Alignment.BottomCenter else Alignment.TopCenter),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        toolbar(
            Modifier
                .fillMaxWidth()
                .align(if (position == HideOnScrollPosition.Top) Alignment.TopCenter else Alignment.BottomCenter)
                .zIndex(2f)
                .offset { IntOffset(x = 0, y = offset.roundToInt()) }
        )

        content(Modifier
            .fillMaxSize()
            .zIndex(1f)
            .then(
                if (!lock()) {
                    Modifier.nestedScroll(nestedScrollConnection)
                } else Modifier
            )
            .then(
                if (position == HideOnScrollPosition.Top) {
                    Modifier.offset { IntOffset(x = 0, y = trueHeight) }
                } else Modifier
            )
        )

    }
}