package com.qwant.android.qwantbrowser.ui.browser

import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProgressBar(
    loadingProgress: Float,
    modifier: Modifier = Modifier
) {
    if (loadingProgress < 1) {
        LinearProgressIndicator(
            progress = loadingProgress,
            modifier = modifier
        )
    }
}