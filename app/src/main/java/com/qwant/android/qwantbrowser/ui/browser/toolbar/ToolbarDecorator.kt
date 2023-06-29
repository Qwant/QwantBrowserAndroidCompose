package com.qwant.android.qwantbrowser.ui.browser.toolbar


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.widgets.QwantIconOnBackground


@Composable
fun ToolbarDecorator(
    hasFocus: Boolean,
    isEmpty: Boolean,
    hintColor: Color,
    innerTextField: @Composable () -> Unit,
    trailingIcons: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    val latestInnerTextField by rememberUpdatedState(innerTextField)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        QwantIconOnBackground(CircleShape)
        Box(modifier = Modifier
            .weight(2f)
            .padding(horizontal = 4.dp)
        ) {
            latestInnerTextField()
            /* if (isEmpty) {
                Text(
                    text = "Search on qwant",
                    fontSize = 14.sp,
                    color = hintColor
                )
            } else {
                latestInnerTextField()
            } */
        }
        trailingIcons()
    }
}
