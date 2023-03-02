package com.qwant.android.qwantbrowser.ui.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R
import com.qwant.android.qwantbrowser.ui.theme.QwantBrowserTheme

@Composable
fun QwantIconOnBackground(shape: Shape) {
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.primary, shape)) {
        Icon(
            painterResource(id = R.drawable.qwant_logo),
            contentDescription = "Qwant",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(6.dp)
        )
    }
}

@Preview
@Composable
fun QwantIconOnBackgroundPreview() {
    QwantBrowserTheme {
        QwantIconOnBackground(shape = RoundedCornerShape(0.5f))
    }
}