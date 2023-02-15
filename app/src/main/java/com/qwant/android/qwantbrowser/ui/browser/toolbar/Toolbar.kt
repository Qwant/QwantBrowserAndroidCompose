package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Backspace
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.utils.IconAction

@Composable
fun Toolbar(
    url: String,
    onTextChanged: (String) -> Unit,
    onTextCommit: (String) -> Unit,
    hasFocus: Boolean,
    onFocusChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    pageActions: @Composable () -> Unit = {},
    browserActions: @Composable () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    var displayedText by remember(url) { mutableStateOf(url) }

    val localStyle = LocalTextStyle.current
    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current))

    val clearFocus = remember(focusManager, onFocusChanged) { {
        onFocusChanged(false)
        focusManager.clearFocus()
    } }

    BackHandler(hasFocus) {
        displayedText = url
        onTextChanged("")
        clearFocus()
    }

    Row(modifier = modifier.padding(start = 8.dp, top = 8.dp, bottom = 8.dp)) {
        BasicTextField(
            value = displayedText,
            onValueChange = {
                displayedText = it
                onTextChanged(it)
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri, imeAction = ImeAction.Go),
            keyboardActions = KeyboardActions(
                onGo = {
                    onTextCommit(displayedText)
                    clearFocus()
                }
            ),
            singleLine = true,
            enabled = true,
            textStyle = mergedStyle,
            cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
            modifier = Modifier
                .weight(2f, true)
                .onFocusChanged {
                    if (!it.hasFocus) {
                        displayedText = url
                        onTextChanged("")
                    }
                    onFocusChanged(it.hasFocus)
                }
        ) { innerTextField ->
            ToolbarDecorator(
                hasFocus = hasFocus,
                isEmpty = displayedText.isEmpty(),
                hintColor = mergedStyle.color.copy(alpha = 0.6f),
                innerTextField = innerTextField,
                trailingIcons = {
                    if (hasFocus) {
                        if (displayedText.isNotEmpty()) {
                            IconAction(label = "Clear", icon = Icons.Default.Backspace) {
                                displayedText = ""
                                onTextChanged("")
                            }
                        }
                    } else {
                        pageActions()
                    }
                }
            )
        }
        browserActions()
    }
}
