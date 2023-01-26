package com.example.qwantbrowsercompose.browser.toolbar


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * Sub-component of the [BrowserToolbar] responsible for displaying the URL and related
 * controls ("display mode").
 *
 * @param url The URL to be displayed.
 * @param onUrlClicked Will be called when the user clicks on the URL.
 * @param onMenuClicked Will be called when the user clicks on the menu button.
 * @param browserActions Additional browser actions to be displayed on the right side of the toolbar
 * (outside of the URL bounding box) in display mode. Also see:
 * [MDN docs](https://developer.mozilla.org/en-US/Add-ons/WebExtensions/user_interface/Browser_action)
 */
@Composable
fun BrowserDisplayToolbar(
    url: String,
    onUrlClicked: () -> Unit = {},
    onMenuClicked: () -> Unit = {},
    browserActions: @Composable () -> Unit = {},
) {
    Row {
        Text(
            url,
            modifier = Modifier
                .clickable { onUrlClicked() }
                .padding(8.dp)
                .weight(1f)
                .align(Alignment.CenterVertically),
            maxLines = 1,
        )

        browserActions()

        Button(onClick = { onMenuClicked() }) {
            Text(":")
        }
    }
}
