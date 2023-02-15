package com.qwant.android.qwantbrowser.ui.tabs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Masks
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.utils.animateAlignmentAsState

@Composable
fun TabPrivacySwitch(
    // store: BrowserStore,
    tabCount: Int,
    private: Boolean,
    onPrivateChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val selectorAlignment by animateAlignmentAsState(if (private) Alignment.CenterEnd else Alignment.CenterStart)

    Box(modifier = modifier
        .width(140.dp)
        .height(40.dp)
        .background(
            Color.Gray,
            shape = RoundedCornerShape(50)
        )
    ) {
        Box(
            Modifier
                .width(70.dp)
                .height(40.dp)
                .background(MaterialTheme.colorScheme.primary, shape = RoundedCornerShape(50))
                .align(selectorAlignment)
        ) {}

        Row(verticalAlignment = Alignment.CenterVertically) {
            TabCounterButton(
                tabCount = tabCount,
                onClicked = { onPrivateChange(false) },
                modifier = Modifier
                    .width(70.dp)
                    .height(40.dp)
                    .padding(8.dp)

            )
            Image(
                Icons.Default.Masks,
                contentScale = ContentScale.Fit,
                contentDescription = "privacy tabs",
                colorFilter = ColorFilter.tint(Color.White),
                modifier = Modifier
                    .width(70.dp)
                    .height(40.dp)
                    .padding(8.dp)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        onPrivateChange(true)
                    }
            )
        }
    }
}