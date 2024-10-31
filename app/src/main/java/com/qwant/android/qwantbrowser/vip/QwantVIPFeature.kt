/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.qwant.android.qwantbrowser.vip

import android.util.Log
import org.json.JSONObject
import org.mozilla.gecko.util.ThreadUtils.runOnUiThread
import org.mozilla.geckoview.GeckoRuntime
import org.mozilla.geckoview.WebExtension
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Feature to enable Qwant tracking protection extension
 */
@Singleton
class QwantVIPFeature @Inject constructor(
    private val vipState: VIPState
) {
    private var shouldAskStatusAfterConnectionForUrl: String? = null

    private val portDelegate = object: WebExtension.PortDelegate {
        override fun onPortMessage(message: Any, port: WebExtension.Port) {
            try {
                with(message as JSONObject) {
                    if (this.getString("action") == "tabStatus") {
                        vipState.enabled = this.getBoolean("protectionEnabled")
                    }
                }
            } catch (e: Exception) {
                Log.e("QWANT_VIP", "Could not parse message from webextension", e)
            }
        }

        override fun onDisconnect(port: WebExtension.Port) {
            if (port == vipState.communicationPort) {
                vipState.communicationPort = null
            }
        }
    }

    private val messageDelegate = object: WebExtension.MessageDelegate {
        override fun onConnect(port: WebExtension.Port) {
            Log.d("QWANT_VIP", "Communication port between native and webextension opened")
            vipState.communicationPort = port
            vipState.communicationPort?.setDelegate(portDelegate)
            shouldAskStatusAfterConnectionForUrl?.let { askStatus(it) }
        }
    }

    /**
     * Installs the web extension in the runtime
     */
    fun install(runtime: GeckoRuntime) {
        runtime.webExtensionController.installBuiltIn(URL).accept {
            it?.let { extension ->
                runOnUiThread {
                    runtime.webExtensionController.setAllowedInPrivateBrowsing(extension, true)
                    extension.setMessageDelegate(messageDelegate, "qwant_browser")
                }
            }
        }
    }

    fun restoreTabsDone(selectedTabUrl: String) {
        if (vipState.communicationPort != null) {
            askStatus(selectedTabUrl)
        } else {
            shouldAskStatusAfterConnectionForUrl = selectedTabUrl
        }
    }

    private fun askStatus(selectedTabUrl: String) {
        Log.d("QWANT_VIP", "askTabStatus - selected tab url is " + selectedTabUrl)
        vipState.communicationPort?.postMessage(JSONObject().also {
            it.put("action", "askTabStatusForUrl")
            it.put("selectedTabUrl", selectedTabUrl)
        })
    }

    companion object {
        private const val URL = "resource://android/assets/qwant_vip/"
    }
}