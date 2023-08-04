package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import mozilla.components.browser.state.state.SecurityInfoState

@Composable
fun SiteSecurityIcon(securityInfo: SecurityInfoState) {
    var showSecurityInfo by remember { mutableStateOf(false) }
    Box {
        Icon(
            painter = painterResource(id = if (securityInfo.secure) R.drawable.icons_lock else R.drawable.icons_warning),
            contentDescription = "security icon",
            tint = if (securityInfo.secure) LocalContentColor.current else Color.Red, // TODO change "RED" color ?
            modifier = Modifier
                .clickable { showSecurityInfo = true }
                .padding(horizontal = 8.dp)
                .size(16.dp)
        )
        // TODO confirm site security dropdown texts
        Dropdown(
            expanded = showSecurityInfo,
            onDismissRequest = { showSecurityInfo = false },
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = if (!securityInfo.secure) {
                    "This connection is not secure ! be careful"
                } else {
                    "Connection is secure"
                },
                style = MaterialTheme.typography.titleMedium
            )
            if (securityInfo.secure) {
                Text(
                    text = "host: ${securityInfo.host}",
                    style = MaterialTheme.typography.labelSmall
                )
                Text(
                    text = "issuer: ${securityInfo.issuer}",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}