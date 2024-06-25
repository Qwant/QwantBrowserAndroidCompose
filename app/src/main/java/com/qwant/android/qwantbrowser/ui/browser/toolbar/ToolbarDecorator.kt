package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
    modifier: Modifier = Modifier,
    onQwantIconClicked: () -> Unit = {}
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(40.dp)
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(50))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        when (state) {
            is BrowserToolbarState -> {
                val shouldShowQwantIcon = state.hasFocus || state.onQwant || state.text.text.isEmpty()
                AnimatedVisibility(visible = shouldShowQwantIcon) {
                    QwantIconOnBackground(
                        shape = CircleShape,
                        Modifier
                            .size(32.dp)
                            .clickable { onQwantIconClicked() }
                    )
                }
                AnimatedVisibility(visible = !shouldShowQwantIcon) {
                    SiteSecurityIcon(state)
                }
            }
            else -> QwantIconOnBackground(
                shape = CircleShape,
                Modifier
                    .size(32.dp)
                    .clickable { onQwantIconClicked() }
            )
        }

        Box(modifier = Modifier
            .weight(2f)
            .padding(start = 12.dp)
        ) {
            // { !viewModel.toolbarState.hasFocus && currentUrl?.isNotBlank() ?: false && !(currentUrl?.isQwantUrl() ?: false) }
            if (state.text.text.isEmpty()) {
                Text(
                    text = when (state) {
                        is BrowserToolbarState -> {
                            val currentUrl by state.currentUrl.collectAsState()
                            if (currentUrl?.isNotBlank() == true && currentUrl?.startsWith("http://") == false && currentUrl?.startsWith("https://") == false) {
                                currentUrl ?: ""
                            } else stringResource(id = R.string.browser_toolbar_hint)
                        }
                        else -> stringResource(id = R.string.browser_toolbar_hint)
                    },
                    fontSize = 16.sp,
                    color = hintColor,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            innerTextField()
        }

        trailingIcons()
    }
}
