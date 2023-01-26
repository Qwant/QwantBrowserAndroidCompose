package com.example.qwantbrowsercompose.ui.browser

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qwantbrowsercompose.browser.BrowserScreenViewModel
import mozilla.components.concept.engine.EngineView
import mozilla.components.feature.session.SessionFeature
import mozilla.components.lib.state.ext.observeAsComposableState
import mozilla.components.support.base.feature.ViewBoundFeatureWrapper

@Composable
fun EngineViewWrapper(
    modifier: Modifier = Modifier,
    viewModel: BrowserScreenViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val sessionFeature = ViewBoundFeatureWrapper<SessionFeature>()

    val selectedTabId by viewModel.mozac.store.observeAsComposableState { state -> state.selectedTabId }

    AndroidView(
        modifier = modifier,
        factory = { context ->
            viewModel.mozac.engine.createView(context).asView()
        },
        update = { view ->
            sessionFeature.set(
                feature = SessionFeature(
                    store = viewModel.mozac.store,
                    goBackUseCase = viewModel.useCases.sessionUseCases.goBack,
                    engineView = view as EngineView,
                    tabId = selectedTabId
                ),
                owner = lifecycleOwner,
                view = view
            )
        }
    )
}