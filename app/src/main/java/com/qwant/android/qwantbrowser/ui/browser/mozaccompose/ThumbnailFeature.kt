package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import mozilla.components.browser.state.store.BrowserStore
import mozilla.components.browser.thumbnails.BrowserThumbnails
import mozilla.components.concept.engine.EngineView

@Composable
fun ThumbnailFeature(
    engineView: EngineView,
    store: BrowserStore
) {
    ComposeFeatureWrapper(feature = BrowserThumbnails(
        LocalContext.current, engineView, store
    ))
}