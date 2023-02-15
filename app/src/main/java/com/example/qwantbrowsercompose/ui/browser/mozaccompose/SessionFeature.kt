package com.example.qwantbrowsercompose.ui.browser.mozaccompose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.EngineView
import mozilla.components.feature.session.SessionFeature
import mozilla.components.feature.session.SessionUseCases
import mozilla.components.support.base.feature.ViewBoundFeatureWrapper

@Composable
fun SessionFeature(
    engineView: EngineView,
    store: BrowserStore,
    canGoBack: Boolean,
    goBackUseCase: SessionUseCases.GoBackUseCase
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val feature = remember { ViewBoundFeatureWrapper<SessionFeature>() }

    LaunchedEffect(engineView) {
        feature.set(
            feature = SessionFeature(
                store = store,
                goBackUseCase = goBackUseCase,
                engineView = engineView
            ),
            owner = lifecycleOwner,
            view = engineView.asView()
        )
    }

    BackHandler(canGoBack) {
        goBackUseCase()
    }
    BackHandler(engineView.canClearSelection()) {
        engineView.clearSelection()
    }
}