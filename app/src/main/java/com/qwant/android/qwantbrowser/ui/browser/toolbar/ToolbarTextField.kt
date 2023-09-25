package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ext.activity

@Composable
fun ToolbarTextField(
    toolbarState: ToolbarState,
    onCommit: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val localStyle = LocalTextStyle.current
    val mergedStyle = localStyle.merge(TextStyle(color = LocalContentColor.current))

    val focusManager = LocalFocusManager.current
    val activity = LocalContext.current.activity
    val focusRequester = remember { FocusRequester() }
    BackHandler(toolbarState.hasFocus) {
        focusManager.clearFocus()
        activity?.forceHideKeyboard()
    }
    LaunchedEffect(toolbarState.hasFocus) {
        if (toolbarState.hasFocus) {
            focusRequester.requestFocus()
        } else {
            focusManager.clearFocus()
            activity?.forceHideKeyboard()
        }
    }

    BasicTextField(
        value = toolbarState.text,
        onValueChange = { toolbarState.updateText(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Uri, imeAction = ImeAction.Go),
        keyboardActions = KeyboardActions(
            onGo = {
                onCommit(toolbarState.text.text)
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        enabled = true,
        textStyle = mergedStyle,
        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
        modifier = modifier
            .focusRequester(focusRequester)
            .onFocusChanged {
                toolbarState.updateFocus(it.hasFocus)
            }
    ) { innerTextField ->
        ToolbarDecorator(
            state = toolbarState,
            hintColor = mergedStyle.color.copy(alpha = 0.6f),
            innerTextField = innerTextField,
            trailingIcons = {
                if (toolbarState.hasFocus) {
                    if (toolbarState.text.text.isNotEmpty()) {
                        IconButton(onClick = { toolbarState.updateText("") }) {
                            Icon(
                                painterResource(id = R.drawable.icons_close_circled),
                                contentDescription = "clear"
                            )
                        }
                    }
                }
            }
        )
    }
}

// TODO move this elsewhere
@Composable
fun KeyboardObserver(
    toolbarState: ToolbarState
) {
    val activity = LocalContext.current.activity
    DisposableEffect(activity) {
        activity?.registerOnKeyboardHiddenCallback {
            if (toolbarState.hasFocus) {
                toolbarState.updateFocus(false)
            }
        }
        onDispose {
            activity?.unregisterOnKeyboardHiddenCallback()
        }
    }
}

