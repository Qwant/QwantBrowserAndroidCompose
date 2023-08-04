package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.widgets.QwantIconOnBackground

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
        AnimatedVisibility(visible = !shouldShowQwantIcon) {
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
                    text = stringResource(id = R.string.browser_toolbar_hint),
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
