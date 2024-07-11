package com.qwant.android.qwantbrowser.ui.widgets

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.ui.theme.JuniorLogoBackground
import com.qwant.android.qwantbrowser.ui.theme.JuniorLogoForeground

@Composable
fun QwantIconOnBackground(
    shape: Shape,
    modifier: Modifier = Modifier,
    color: Color = JuniorLogoForeground,
    bgColor: Color = JuniorLogoBackground,
) {
    GenericQwantIconOnBackground(shape = shape, modifier = modifier, color = color, bgColor = bgColor)
}

@Preview
@Composable
fun QwantIconOnBackgroundPreview() {
    QwantIconOnBackground(shape = CircleShape, modifier = Modifier.size(32.dp))
}