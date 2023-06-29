package com.qwant.android.qwantbrowser.ui.preferences.widgets

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupProperties
import com.qwant.android.qwantbrowser.ui.widgets.ScreenHeader

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PreferenceSelectionPopup(
    @StringRes label: Int,
    popupContent: @Composable () -> Unit,
    @StringRes description: Int? = null,
    icon: @Composable () -> Unit = {},
    fullscreenPopup: Boolean = false
) {
    var showPopup by remember { mutableStateOf(false) }

    BackHandler(showPopup) {
        showPopup = false
    }

    Box {
        PreferenceRow(
            label = label,
            description = description?.let { stringResource(it) },
            icon = icon,
            onClicked = { showPopup = true }
        )
        if (showPopup) {
            if (fullscreenPopup) {
                Dialog(
                    properties = DialogProperties(usePlatformDefaultWidth = false),
                    onDismissRequest = { showPopup = false }
                ) {
                    Surface(modifier = Modifier.fillMaxSize()) {
                        Column {
                            ScreenHeader(title = stringResource(id = label))
                            popupContent()
                        }
                    }
                }
            } else {
                DropdownMenu(
                    expanded = true,
                    onDismissRequest = { showPopup = false },
                    offset = DpOffset(16.dp, 0.dp),
                    properties = PopupProperties(usePlatformDefaultWidth = true),
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .border(BorderStroke(1.dp, MaterialTheme.colorScheme.outline), MaterialTheme.shapes.extraSmall)
                ) {
                    popupContent()
                }
            }
        }
    }
}