package com.qwant.android.qwantbrowser.contentBlocker

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import mozilla.components.concept.engine.EngineSession
import mozilla.components.concept.engine.permission.PermissionRequest

class ContentBlockerObserver(
    private val contentBlockerState: ContentBlockerState
): EngineSession.Observer {
    override fun onLocationChange(url: String, hasUserGesture: Boolean) {
        if (url.isQwantUrl()) {
            contentBlockerState.onQwant()
        } else {
            contentBlockerState.check(url)
        }
    }

    override fun onAppPermissionRequest(permissionRequest: PermissionRequest) = Unit
    override fun onContentPermissionRequest(permissionRequest: PermissionRequest) = Unit
}

@Composable
fun ContentBlockerObserver(
    contentBlockerState: ContentBlockerState,
    session: EngineSession?
) {
    val observer = remember { ContentBlockerObserver(contentBlockerState) }
    DisposableEffect(session) {
        session?.register(observer)
        onDispose {
            contentBlockerState.cancel()
            session?.unregister(observer)
        }
    }
}