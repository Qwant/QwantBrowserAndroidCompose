package com.qwant.android.qwantbrowser.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.theme.Grey900
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun QwantIconOnBackground(
    shape: Shape,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onPrimary,
    bgColor: Color = MaterialTheme.colorScheme.primary,
) {
    Box(modifier = modifier
        .background(bgColor, shape)
    ) {
        Icon(
            painterResource(id = R.drawable.qwant_logo),
            contentDescription = "Qwant",
            tint = color,
            modifier = Modifier
                .padding(bottom = 6.dp, start = 6.dp, top = 4.dp, end = 4.dp)
                .align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun QwantIconOnBackgroundPreview() {
    QwantIconOnBackground(shape = CircleShape, modifier = Modifier.size(32.dp))
}