package com.qwant.android.qwantbrowser.ui.browser.mozaccompose


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import mozilla.components.concept.engine.Engine
import mozilla.components.concept.engine.EngineView


@Composable
fun EngineView(
    engine: Engine,
    modifier: Modifier = Modifier,
    features: @Composable (EngineView) -> Unit = {},
) {
    var engineView: EngineView? by remember { mutableStateOf(null) }
    
    val latestFeatures by rememberUpdatedState(features)

    AndroidView(
        modifier = modifier,
        factory = { context ->
            engine.createView(context).asView()
        },
        update = { view ->
            engineView = view as EngineView
        }
    )

    engineView?.let {
        latestFeatures(it)
    }
}