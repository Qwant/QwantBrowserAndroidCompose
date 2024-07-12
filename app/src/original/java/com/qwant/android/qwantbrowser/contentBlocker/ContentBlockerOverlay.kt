package com.qwant.android.qwantbrowser.contentBlocker

import androidx.compose.runtime.Composable

@Suppress("UNUSED_PARAMETER")
@Composable
fun ContentBlockerOverlay(
    status: ContentBlockerState.Status,
    blockReason: ContentBlockerState.BlockReason,
    imageModifier: Modifier = Modifier,
    imageScale: ContentScale = ContentScale.Fit,
    alignment: Alignment = Alignment.Center
) {}