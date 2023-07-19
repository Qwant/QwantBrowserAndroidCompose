package com.qwant.android.qwantbrowser.ui.browser.toolbar

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import mozilla.components.concept.engine.EngineView

@Composable
fun DynamicToolbarFeature(
    engineView: EngineView,
    toolbarState: ToolbarState,
    enabled: Boolean = true
) {
    LaunchedEffect(enabled, engineView, toolbarState.trueHeightPx) {
        if (enabled) {
            engineView.setDynamicToolbarMaxHeight(toolbarState.trueHeightPx)
        }
    }
}