package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.*
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.preferences.app.ToolbarPosition
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.ui.browser.suggest.Suggest
import com.qwant.android.qwantbrowser.ui.theme.LocalQwantTheme
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
    animationDurationMs: Int = 400,
    animationEasing: Easing = FastOutSlowInEasing
) {
    val toolbarPosition by toolbarState.toolbarPosition.collectAsState()

    /* val toolbarTextFieldPaddingEnd by animateDpAsState(
        targetValue = if (toolbarState.hasFocus) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing),
        label = "toolbarTextFieldPaddingEnd"
    ) */

    val toolbarTextFieldPaddingStart by animateDpAsState(
        targetValue = if (!beforeTextFieldVisible() || toolbarState.hasFocus) 8.dp else 0.dp,
        animationSpec = tween(durationMillis = animationDurationMs, easing = animationEasing),
        label = "toolbarTextFieldPaddingStart"
    )

    // TODO move commitSuggestion to viewModel, and transmit it as param
    val commitSuggestion: (Suggestion) -> Unit = { suggestion ->
        when (suggestion) {
            is Suggestion.SelectTabSuggestion -> onTextCommit(suggestion.url)
            is Suggestion.SearchSuggestion -> onTextCommit(suggestion.text) // TODO toolbarState.updateText(suggestion.text), but it looses focus on the toolbar, which causes issues.
            is Suggestion.OpenTabSuggestion -> (suggestion.url ?: suggestion.title)?.let {
                onTextCommit(it)
            }
        }
    }

    // TODO Move toolbar divider color to some material theme color
    val qwantTheme = LocalQwantTheme.current
    val dividerColor = if (qwantTheme.dark || qwantTheme.private) Color(0xFF4B5058) else Color(0xFFD9DBDE)

    Column(modifier = modifier) {
        if (toolbarPosition == ToolbarPosition.BOTTOM) {
            ToolbarSuggest(
                toolbarState = toolbarState,
                commitSuggestion = commitSuggestion,
                browserIcons = browserIcons,
                modifier = Modifier.weight(2f)
            )
            ToolbarProgressBar(toolbarState = toolbarState)
            HorizontalDivider(thickness = 1.dp, color = dividerColor)
        }

        Row(modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(MaterialTheme.colorScheme.primaryContainer)
            // .padding(vertical = 8.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { toolbarState.updateFocus(true) }
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
                            start = toolbarTextFieldPaddingStart,
                            end = 8.dp, // toolbarTextFieldPaddingEnd,
                            top = 8.dp,
                            bottom = 8.dp
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
            HorizontalDivider(thickness = 1.dp, color = dividerColor)
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
    val suggestions by toolbarState.suggestions.collectAsState()

    if (toolbarState.hasFocus && suggestions.any { it.value.isNotEmpty() }) {
        Suggest(
            suggestions = suggestions,
            onSuggestionClicked = { suggestion ->
                commitSuggestion(suggestion)
                toolbarState.updateFocus(false)
            },
            onSetTextClicked = {
                toolbarState.updateText(
                    TextFieldValue(it, selection = TextRange(it.length))
                )
            },
            toolbarPosition = toolbarPosition,
            browserIcons = browserIcons,
            modifier = modifier.background(Color.White)
        )
    }
}
