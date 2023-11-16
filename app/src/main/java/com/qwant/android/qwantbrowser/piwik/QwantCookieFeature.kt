package com.qwant.android.qwantbrowser.piwik

import android.util.Log
import com.qwant.android.qwantbrowser.preferences.app.AppPreferencesRepository
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.json.JSONObject
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.WebExtension.MessageDelegate
import org.mozilla.geckoview.WebExtension.Port
import org.mozilla.geckoview.WebExtension.PortDelegate
import javax.inject.Inject

/**
 * Feature to enable Qwant cookies
 */
class QwantCookieFeature @Inject constructor(
    private val geckoRuntime: GeckoRuntime,
    private val appPreferencesRepository: AppPreferencesRepository
) {
    private var port: Port? = null

    private val coroutineScope = MainScope()

    private val portDelegate = object : PortDelegate {
        override fun onPortMessage(message: Any, port: Port) {
            Log.d("QWANT_BROWSER_EXTENSION", "Qwant cookies WebExtension sends $message")
        }
        override fun onDisconnect(port: Port) {
            if (port == this@QwantCookieFeature.port) {
                this@QwantCookieFeature.port = null
            }
        }
    }
    private val messageDelegate = object : MessageDelegate {
        override fun onConnect(port: Port) {
            this@QwantCookieFeature.port = port
            port.setDelegate(portDelegate)
        }
    }

    fun run() {
        geckoRuntime.webExtensionController.ensureBuiltIn(URL, ID).accept({ extension ->
            Log.d("QWANT_BROWSER_EXTENSION", "Qwant cookies WebExtension installed")
            extension?.run { setMessageDelegate(messageDelegate, "browser") }
            coroutineScope.launch {
                appPreferencesRepository.flow
                    .map { it.piwikOptout }
                    .collectLatest {
                        Log.d("QWANT_BROWSER_EXTENSION", "Piwik optout changed. Sending info to webextension")
                        port?.postMessage(JSONObject().apply {
                            put("piwikOptout", it)
                        })
                    }
            }
        }, { exception ->
            Log.e("QWANT_BROWSER_EXTENSION", "Error registering Qwant cookies WebExtension", exception)
        })
    }

    companion object {
        const val ID = "qwant-cookie-android@qwant.com"
        const val URL = "resource://android/assets/qwant_cookies/"
    }
}
