package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.concept.engine.EngineView
import mozilla.components.feature.session.SessionFeature
import mozilla.components.feature.session.SessionUseCases

@Composable
fun SessionFeature(
    engineView: EngineView,
    store: BrowserStore,
    canGoBack: Boolean,
    goBackUseCase: SessionUseCases.GoBackUseCase,
    backEnabled: () -> Boolean = { true }
) {
    val feature = remember(engineView) {
        SessionFeature(
            store = store,
            goBackUseCase = goBackUseCase,
            engineView = engineView
        )
    }

    ComposeFeatureWrapper(feature = feature) {
        if (backEnabled()) {
            // TODO better handling of session back (if can't go back, go to parent tabs, then homepage, then only leave)
            BackHandler(canGoBack) {
                goBackUseCase()
            }
            BackHandler(engineView.canClearSelection()) {
                engineView.clearSelection()
            }
        }
    }
}