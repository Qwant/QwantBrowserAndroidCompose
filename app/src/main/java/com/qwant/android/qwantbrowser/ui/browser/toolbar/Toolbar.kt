package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.*
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.ui.browser.suggest.Suggest
import mozilla.components.browser.icons.BrowserIcons


@Composable
fun Toolbar(
    onTextCommit: (String) -> Unit,
    modifier: Modifier = Modifier,
    toolbarState: ToolbarState,
    browserIcons: BrowserIcons,
    beforeTextField: @Composable () -> Unit = {},
    beforeTextFieldVisible: () -> Boolean = { true },
    afterTextField: @Composable () -> Unit = {},
    afterTextFieldVisible: () -> Boolean = { true },
    animationDurationMs: Int = 1000,
    animationEasing: Easing = FastOutSlowInEasing
) {
    val toolbarPosition by toolbarState.toolbarPosition.collectAsState()

    val toolbarTextFieldPadding by animateDpAsState(
        targetValue = if (toolbarState.hasFocus) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing)
    )

    // TODO move commitSuggestion to viewModel, and transmit it as param
    val commitSuggestion: (Suggestion) -> Unit = { suggestion ->
        when (suggestion) {
            is Suggestion.SelectTabSuggestion -> onTextCommit(suggestion.url)
            is Suggestion.SearchSuggestion -> onTextCommit(suggestion.text)
            is Suggestion.OpenTabSuggestion -> (suggestion.url ?: suggestion.title)?.let {
                onTextCommit(it)
            }
        }
    }

    Column(modifier = modifier) {
        if (toolbarPosition == ToolbarPosition.BOTTOM) {
            ToolbarSuggest(
                toolbarState = toolbarState,
                commitSuggestion = commitSuggestion,
                browserIcons = browserIcons,
                modifier = Modifier.weight(2f)
            )
            ToolbarProgressBar(toolbarState = toolbarState)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 8.dp)
        ) {
            CompositionLocalProvider(
                LocalContentColor provides MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                AnimatedVisibility(
                    visible = beforeTextFieldVisible(),
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
                        .padding(
                            start = if (!beforeTextFieldVisible()) 8.dp else toolbarTextFieldPadding,
                            end = toolbarTextFieldPadding
                        )
                )

                AnimatedVisibility(
                    visible = afterTextFieldVisible(),
                    enter = expandHorizontally(animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing)),
                    exit = shrinkHorizontally(animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing))
                ) {
                    afterTextField()
                }
            }
        }

        if (toolbarPosition == ToolbarPosition.TOP) {
            ToolbarProgressBar(toolbarState = toolbarState)
            ToolbarSuggest(
                toolbarState = toolbarState,
                commitSuggestion = commitSuggestion,
                browserIcons = browserIcons,
                modifier = Modifier.weight(2f)
            )
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
            .height(4.dp)
    )
}

@Composable
private fun ToolbarSuggest(
    toolbarState: ToolbarState,
    browserIcons: BrowserIcons,
    commitSuggestion: (Suggestion) -> Unit,
    modifier: Modifier = Modifier
) {
    val toolbarPosition by toolbarState.toolbarPosition.collectAsState()

    if (toolbarState.hasFocus && toolbarState.suggestions.any { it.value.isNotEmpty() }) {
        Suggest(
            suggestions = toolbarState.suggestions,
            onSuggestionClicked = { suggestion ->
                commitSuggestion(suggestion)
                toolbarState.updateFocus(false)
            },
            toolbarPosition = toolbarPosition,
            browserIcons = browserIcons,
            modifier = modifier.background(Color.White)
        )
    }
}
