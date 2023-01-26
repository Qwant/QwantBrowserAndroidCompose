@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.qwantbrowsercompose.browser.toolbar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.qwantbrowsercompose.R

/**
 * Sub-component of the [BrowserToolbar] responsible for allowing the user to edit the current
 * URL ("edit mode").
 *
 * @param url The initial URL to be edited.
 * @param onUrlEdit Will be called when the URL value changes. An updated text value comes as a
 * parameter of the callback.
 * @param onUrlCommitted Will be called when the user has finished editing and wants to initiate
 * loading the entered URL. The committed text value comes as a parameter of the callback.
 * @param editActions Optional actions to be displayed on the right side of the toolbar.
 */
@Composable
fun BrowserEditToolbar(
    url: String,
    onUrlEdit: (String) -> Unit = {},
    onUrlCommitted: (String) -> Unit = {},
    editActions: @Composable () -> Unit = {},
) {
    val backgroundColor = MaterialTheme.colorScheme.surface
    val foregroundColor = contentColorFor(backgroundColor)

    TextField(
        url,
        onValueChange = { value ->
            onUrlEdit(value)
        },
        colors = TextFieldDefaults.textFieldColors(
            textColor = foregroundColor,
            containerColor = backgroundColor,
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Uri,
            imeAction = ImeAction.Go,
        ),
        keyboardActions = KeyboardActions(
            onGo = { onUrlCommitted(url) },
        ),
        modifier = Modifier.fillMaxWidth(),
        trailingIcon = {
            editActions()

            if (url.isNotEmpty()) {
                ClearButton(onButtonClicked = { onUrlEdit("") })
            }
        },
    )
}

/**
 * Sub-component of the [BrowserEditToolbar] responsible for displaying a clear icon button.
 *
 * @param onButtonClicked Will be called when the user clicks on the button.
 */
@Composable
fun ClearButton(onButtonClicked: () -> Unit = {}) {
    IconButton(
        modifier = Modifier.requiredSize(40.dp),
        onClick = { onButtonClicked() },
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_settings),
            contentDescription = "clear",
            tint = Color.Black,
        )
    }
}
