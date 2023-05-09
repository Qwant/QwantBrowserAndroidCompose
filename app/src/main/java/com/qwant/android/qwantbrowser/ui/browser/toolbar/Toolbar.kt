package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.*
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.browser.ProgressBar
import com.qwant.android.qwantbrowser.ui.browser.suggest.Suggest


@Composable
fun Toolbar(
    onTextCommit: (String) -> Unit,
    modifier: Modifier = Modifier,
    toolbarState: ToolbarState,
    beforeTextField: @Composable () -> Unit = {},
    afterTextField: @Composable () -> Unit = {},
    animationDurationMs: Int = 1000,
    animationEasing: Easing = FastOutSlowInEasing
) {
    val toolbarPosition by toolbarState.toolbarPosition.collectAsState()

    val toolbarTextFieldPadding by animateDpAsState(
        targetValue = if (toolbarState.hasFocus) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing)
    )

    Column(modifier = modifier) {
        if (toolbarPosition == HideOnScrollPosition.Bottom) {
            ToolbarSuggest(toolbarState = toolbarState, Modifier.weight(2f))
            ToolbarProgressBar(toolbarState = toolbarState)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .drawBehind { drawRect(Color.White) }
            .padding(vertical = 8.dp)
        ) {
            AnimatedVisibility(
                visible = !toolbarState.hasFocus,
                enter = expandHorizontally(animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing)),
                exit = shrinkHorizontally(shrinkTowards = Alignment.Start, animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing))
            ) {
                beforeTextField()
            }

            ToolbarTextField(
                toolbarState = toolbarState,
                onCommit = onTextCommit,
                modifier = Modifier
                    .weight(2f, true)
                    .padding(horizontal = toolbarTextFieldPadding)
            )

            AnimatedVisibility(
                visible = !toolbarState.hasFocus,
                enter = expandHorizontally(animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing)),
                exit = shrinkHorizontally(animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing))
            ) {
                afterTextField()
            }
        }

        if (toolbarPosition == HideOnScrollPosition.Top) {
            ToolbarProgressBar(toolbarState = toolbarState)
            ToolbarSuggest(toolbarState = toolbarState, Modifier.weight(2f))
        }
    }
}

@Composable
private fun ToolbarProgressBar(
    toolbarState: ToolbarState
) {
    val loadingProgress by toolbarState.loadingProgress.collectAsState()

    ProgressBar(
        loadingProgress = loadingProgress,
        modifier = Modifier
            .fillMaxWidth()
            .height(5.dp)
    )
}

@Composable
private fun ToolbarSuggest(
    toolbarState: ToolbarState,
    modifier: Modifier = Modifier
) {
    val suggestions by toolbarState.suggestions.collectAsState()

    Suggest(
        suggestions = suggestions,
        onSuggestionClicked = { suggestion ->
            // viewModel.commitSuggestion(suggestion)
            // focusManager.clearFocus()
        },
        modifier = modifier.background(Color.White)
    )
}
