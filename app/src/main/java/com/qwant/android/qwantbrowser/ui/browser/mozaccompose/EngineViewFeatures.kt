package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.qwant.android.qwantbrowser.ui.browser.BrowserScreenViewModel
import com.qwant.android.qwantbrowser.ui.browser.toolbar.DynamicToolbarFeature
import mozilla.components.concept.engine.EngineView

@Composable
fun BoxScope.EngineViewFeatures(
    engineView: EngineView,
    viewModel: BrowserScreenViewModel = hiltViewModel(),
) {
    val canGoBack by viewModel.canGoBack.collectAsState()
    SessionFeature(
        engineView = engineView,
        store = viewModel.store,
        canGoBack = canGoBack,
        goBackUseCase = viewModel.goBack,
        backEnabled = { !viewModel.toolbarState.hasFocus }
    )

    ThumbnailFeature(
        engineView = engineView,
        store = viewModel.store
    )

    val shouldHideToolbarOnScroll by viewModel.toolbarState.shouldHideOnScroll.collectAsState()
    DynamicToolbarFeature(
        engineView = engineView,
        toolbarState = viewModel.toolbarState,
        enabled = shouldHideToolbarOnScroll && !viewModel.showFindInPage
    )

    FindInPageFeature(
        engineView = engineView,
        store = viewModel.store,
        enabled = { viewModel.showFindInPage },
        onDismiss = { viewModel.updateShowFindInPage(false) },
        modifier = Modifier.align(Alignment.BottomCenter)
    )
}

