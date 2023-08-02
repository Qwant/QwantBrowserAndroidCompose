package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.widgets.Dropdown
import com.qwant.android.qwantbrowser.ui.widgets.QwantIconOnBackground
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
        // TODO confirm site security dropdown
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
                Text(text = "host: ${securityInfo.host}", style = MaterialTheme.typography.labelSmall)
                Text(text = "issuer: ${securityInfo.issuer}", style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun ToolbarDecorator(
    state: ToolbarState,
    hintColor: Color,
    innerTextField: @Composable () -> Unit,
    trailingIcons: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val siteSecurity by state.siteSecurity.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(40.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        val shouldShowQwantIcon = state.hasFocus || state.onQwant
        AnimatedVisibility(visible = shouldShowQwantIcon) {
            QwantIconOnBackground(shape = CircleShape)
        }
        AnimatedVisibility(visible = !shouldShowQwantIcon,) {
            siteSecurity?.let {
                SiteSecurityIcon(securityInfo = it)
            } ?: Box(modifier = Modifier.size(24.dp))
        }

        // TODO hide toolbar cursor cleverly.
        /* val customTextSelectionColors = TextSelectionColors(
            handleColor = Color.Unspecified,
            backgroundColor = Color.Unspecified
        ) */

        Box(modifier = Modifier
            .weight(2f)
            .padding(horizontal = 4.dp)
        ) {
            if (state.text.text.isEmpty()) {
                Text(
                    text = "Search on qwant",
                    fontSize = 14.sp,
                    color = hintColor
                )
            }
            // CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                innerTextField()
            // }
        }
        trailingIcons()
    }
}
