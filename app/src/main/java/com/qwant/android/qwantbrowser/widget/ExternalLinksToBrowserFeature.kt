package com.qwant.android.qwantbrowser.widget

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import com.qwant.android.qwantbrowser.ext.activity
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import mozilla.components.concept.engine.EngineSession

@Composable
fun ExternalLinksToBrowserFeature(session: EngineSession) {
    val activity = LocalContext.current.activity as WidgetActivity
    val observer = remember { object: EngineSession.Observer {
        override fun onLocationChange(url: String, hasUserGesture: Boolean) {
            if (url.isNotEmpty() && url != "about:blank" && !url.isQwantUrl()) {
                session.goBack()
                activity.openFullBrowsingActivity(url)
            }
        }
    } }

    DisposableEffect(session) {
        session.register(observer)
        onDispose {
            session.unregister(observer)
        }
    }
}