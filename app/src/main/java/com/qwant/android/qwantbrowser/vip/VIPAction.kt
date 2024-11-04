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
import com.qwant.android.qwantbrowser.ui.theme.LocalQwantTheme
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown

@Composable
fun VIPAction(
    vipState: VIPState,
    openLink: (String) -> Unit
) {
    val isDarkTheme = LocalQwantTheme.current.dark
    val iconId = when {
        vipState.enabled && isDarkTheme -> R.drawable.icons_vip_enabled_night
        vipState.enabled && !isDarkTheme -> R.drawable.icons_vip_enabled
        !vipState.enabled && isDarkTheme -> R.drawable.icons_vip_disabled_night
        !vipState.enabled && !isDarkTheme -> R.drawable.icons_vip_disabled
        else -> throw (Exception("Invalid icon for VIP action"))
    }

    Box {
        var showVIPPopup by remember { mutableStateOf(false) }
        ToolbarAction(onClick = { showVIPPopup = true }) {
            Image(
                painter = painterResource(id = iconId),
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

