package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.style.TextOverflow
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
    val currentUrl by state.currentUrl.collectAsState()

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(40.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        val shouldShowQwantIcon = state.hasFocus || state.onQwant || state.text.text.isEmpty()
        AnimatedVisibility(visible = shouldShowQwantIcon) {
            QwantIconOnBackground(shape = CircleShape)
        }
        AnimatedVisibility(visible = !shouldShowQwantIcon) {
            SiteSecurityIcon(state)
            /* siteSecurity?.let {
                SiteSecurityIcon(securityInfo = it)
            } ?: Box(modifier = Modifier.size(24.dp)) */
        }

        // TODO hide toolbar cursor cleverly.
        /* val customTextSelectionColors = TextSelectionColors(
            handleColor = Color.Unspecified,
            backgroundColor = Color.Unspecified
        ) */

        Box(modifier = Modifier
            .weight(2f)
            .padding(start = 12.dp)
        ) {
            // { !viewModel.toolbarState.hasFocus && currentUrl?.isNotBlank() ?: false && !(currentUrl?.isQwantUrl() ?: false) }
            if (state.text.text.isEmpty()) {
                if (currentUrl?.isNotBlank() == true && currentUrl?.startsWith("http://") == false && currentUrl?.startsWith("https://") == false) {
                    // Intermediate status when loading pages ("about:blank" example) for which we don't want the hint to show
                    Text(
                        text = currentUrl ?: "",
                        fontSize = 16.sp,
                        color = hintColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Text(
                        text = stringResource(id = R.string.browser_toolbar_hint),
                        fontSize = 16.sp,
                        color = hintColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
            // CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                innerTextField()
            // }
        }
        trailingIcons()
    }
}
