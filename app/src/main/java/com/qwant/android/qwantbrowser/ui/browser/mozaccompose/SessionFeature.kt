package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import android.util.Log
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
    val feature = remember(engineView) {
        SessionFeature(
            store = store,
            goBackUseCase = goBackUseCase,
            engineView = engineView
        )
    }

    ComposeFeatureWrapper(feature = feature) {
        BackHandler(canGoBack) {
            goBackUseCase()
        }
        BackHandler(engineView.canClearSelection()) {
            engineView.clearSelection()
        }
    }
}