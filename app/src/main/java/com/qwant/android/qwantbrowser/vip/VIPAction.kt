package com.qwant.android.qwantbrowser.vip

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.browser.ToolbarAction
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown

@Composable
fun VIPAction(
    vipState: VIPState,
    openLink: (String) -> Unit
) {
    Box {
        var showVIPPopup by remember { mutableStateOf(false) }
        ToolbarAction(onClick = { showVIPPopup = true }) {
            Image(
                painter = painterResource(
                    id = if (vipState.enabled) R.drawable.icons_webext_vip
                    else R.drawable.icons_webext_vip_night
                ),
                contentDescription = "Qwant VIP",
                modifier = Modifier.fillMaxSize()
            )
        }

        Dropdown(
            expanded = showVIPPopup,
            onDismissRequest = { showVIPPopup = false }
        ) {
            VIPPopup(vipState, openLink)
        }
    }
}

