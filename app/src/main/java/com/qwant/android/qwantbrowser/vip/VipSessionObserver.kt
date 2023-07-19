package com.qwant.android.qwantbrowser.vip

import mozilla.components.concept.engine.EngineSession
import mozilla.components.concept.engine.window.WindowRequest

class VipSessionObserver(
    val closePopup: () -> Unit,
    val loadUrl: (url: String) -> Unit,
): EngineSession.Observer {
    override fun onWindowRequest(windowRequest: WindowRequest) {
        if (windowRequest.type == WindowRequest.Type.CLOSE) {
            closePopup()
        } else {
            loadUrl(windowRequest.url)
        }
    }
}