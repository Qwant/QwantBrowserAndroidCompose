package com.qwant.android.qwantbrowser.ui.browser.mozaccompose

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.qwant.android.qwantbrowser.ui.browser.toolbar.BrowserToolbarState
import com.qwant.android.qwantbrowser.ui.browser.toolbar.ToolbarState
import mozilla.components.concept.engine.EngineSession
import mozilla.components.concept.engine.permission.PermissionRequest

class SessionScrollObserver(
    private val toolbarState: BrowserToolbarState
): EngineSession.Observer {
    override fun onScrollChange(scrollX: Int, scrollY: Int) {
        super.onScrollChange(scrollX, scrollY)
        if (scrollY == 0) {
            toolbarState.updateVisibility(true)
        }
    }
    override fun onAppPermissionRequest(permissionRequest: PermissionRequest) = Unit
    override fun onContentPermissionRequest(permissionRequest: PermissionRequest) = Unit
}

@Composable
fun ToolbarAlwaysVisibleWhenScrolledToTopFeature(
    toolbarState: BrowserToolbarState,
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