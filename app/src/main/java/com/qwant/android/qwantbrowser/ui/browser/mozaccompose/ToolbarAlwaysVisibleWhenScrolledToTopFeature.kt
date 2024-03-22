package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.qwant.android.qwantbrowser.ui.browser.toolbar.ToolbarState
import mozilla.components.concept.engine.EngineSession

class SessionScrollObserver(
    private val toolbarState: ToolbarState
): EngineSession.Observer {
    override fun onScrollChange(scrollX: Int, scrollY: Int) {
        super.onScrollChange(scrollX, scrollY)
        if (scrollY == 0) {
            toolbarState.updateVisibility(true)
        }
    }
}

@Composable
fun ToolbarAlwaysVisibleWhenScrolledToTopFeature(
    toolbarState: ToolbarState,
    session: EngineSession?
) {
    val scrollObserver = remember { SessionScrollObserver(toolbarState) }
    DisposableEffect(session) {
        session?.register(scrollObserver)
        onDispose {
            session?.unregister(scrollObserver)
        }
    }
}