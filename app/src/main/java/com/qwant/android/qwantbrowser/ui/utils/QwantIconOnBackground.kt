package com.qwant.android.qwantbrowser.ui.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.qwant.android.qwantbrowser.R

@Composable
fun QwantIconOnBackground(shape: Shape) {
    Box(modifier = Modifier
        .background(MaterialTheme.colorScheme.primary, shape)) {
        Icon(
            painterResource(id = R.drawable.qwant_logo),
            contentDescription = "Qwant",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.padding(4.dp)
        )
    }
}