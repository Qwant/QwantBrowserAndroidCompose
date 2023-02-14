package com.example.qwantbrowsercompose.ui.tabs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private const val MAX_VISIBLE_TABS = 99
private const val SO_MANY_TABS_OPEN = "∞"

@Composable
fun TabCounterButton(
    tabCount: Int,
    onClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(modifier
        .clickable(
            interactionSource = remember { MutableInteractionSource() },
            indication = null
        ) { onClicked() }
    ) {
        Text(
            text = if (tabCount > MAX_VISIBLE_TABS) SO_MANY_TABS_OPEN else tabCount.toString(),
            fontSize = 12.sp,
            color = Color.White,
            modifier = Modifier
                .align(Alignment.Center)
                .border(width = 2.dp, color = Color.White)
                .padding(vertical = 4.dp, horizontal = 8.dp)
        )
    }
}