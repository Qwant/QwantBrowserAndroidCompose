package com.qwant.android.qwantbrowser.ui.browser.toolbar

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.qwant.android.qwantbrowser.ui.widgets.QwantIconOnBackground


@Composable
fun ToolbarDecorator(
    hasFocus: Boolean,
    isEmpty: Boolean,
    hintColor: Color,
    innerTextField: @Composable () -> Unit,
    trailingIcons: @Composable () -> Unit
) {
    val borderSize = if (hasFocus) 2.dp else 1.dp
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(50))
            .border(borderSize, MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
            .padding(4.dp)
    ) {
        QwantIconOnBackground(CircleShape)
        Box(modifier = Modifier
            .weight(2f)
            .padding(horizontal = 4.dp)
        ) {
            if (isEmpty) {
                Text(
                    text = "Search on qwant",
                    fontSize = 14.sp,
                    color = hintColor
                )
            } else {
                innerTextField()
            }
        }
        trailingIcons()
    }
}
