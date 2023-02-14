package com.example.qwantbrowsercompose.ui.browser.mozaccompose

import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qwantbrowsercompose.mozac.MozacViewModel
import mozilla.components.browser.state.selector.selectedTab
import mozilla.components.concept.engine.EngineView
import mozilla.components.feature.session.SessionFeature
import mozilla.components.lib.state.ext.observeAsComposableState
import mozilla.components.support.base.feature.ViewBoundFeatureWrapper

@Composable
fun SessionFeature(
    engineView: EngineView,
    viewModel: MozacViewModel = hiltViewModel()
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val feature = remember { ViewBoundFeatureWrapper<SessionFeature>() }

    LaunchedEffect(engineView) {
        feature.set(
            feature = SessionFeature(
                store = viewModel.mozac.store,
                goBackUseCase = viewModel.useCases.sessionUseCases.goBack,
                engineView = engineView
            ),
            owner = lifecycleOwner,
            view = engineView.asView()
        )
    }

    val selectedTabContent by viewModel.mozac.store.observeAsComposableState { state -> state.selectedTab?.content }
    BackHandler(selectedTabContent?.canGoBack ?: false) {
        viewModel.useCases.sessionUseCases.goBack()
    }
    BackHandler(engineView.canClearSelection()) {
        engineView.clearSelection()
    }
}