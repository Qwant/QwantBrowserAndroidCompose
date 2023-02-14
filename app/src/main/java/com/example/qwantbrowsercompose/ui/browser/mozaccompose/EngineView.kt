package com.example.qwantbrowsercompose.ui.browser.mozaccompose


import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qwantbrowsercompose.mozac.MozacViewModel
import mozilla.components.concept.engine.EngineView


@Composable
fun EngineView(
    modifier: Modifier = Modifier,
    features: @Composable (EngineView) -> Unit = {},
    viewModel: MozacViewModel = hiltViewModel(),
) {
    var engineView: EngineView? by remember { mutableStateOf(null) }
    
    val latestFeatures by rememberUpdatedState(features)

    AndroidView(
        modifier = modifier,
        factory = { context ->
            viewModel.mozac.engine.createView(context).asView()
        },
        update = { view ->
            engineView = view as EngineView
        }
    )

    engineView?.let {
        latestFeatures(it)
    }
}