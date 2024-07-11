package com.qwant.android.qwantbrowser.contentBlocker

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
fun ContentBlockerOverlay(contentBlockerStatus: ContentBlockerState.Status) {
    Log.d("QwantContentBlocker", "compose ContentBlockerOverlay")
    if (contentBlockerStatus != ContentBlockerState.Status.ALLOWED) {
        Log.d("QwantContentBlocker", "compose ContentBlockerOverlay not allowed")
        Box(modifier = Modifier
            .fillMaxSize()
            .background(if (contentBlockerStatus != ContentBlockerState.Status.BLOCKED) Color.Blue else Color.Red)
        )
    } else {
        Log.d("QwantContentBlocker", "compose ContentBlockerOverlay allowed")
    }
}