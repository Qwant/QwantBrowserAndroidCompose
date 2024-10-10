/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.qwant.android.qwantbrowser.vip

import android.util.Log
import mozilla.components.concept.engine.webextension.WebExtensionRuntime
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
    vipState: VIPState
) {
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

    companion object {
        private const val URL = "resource://android/assets/qwant_vip/"
    }
}

/*
object QwantVIPFeature {
    const val ID = "qwant-vip-android@qwant.com"
    private const val URL = "resource://android/assets/qwant_vip/"
    private const val LAST_VERSION = "8.0.0"

    /**
     * Installs the web extension in the runtime through the WebExtensionRuntime install method
     */
    fun install(runtime: WebExtensionRuntime) {
        runtime.listInstalledWebExtensions({ list ->
            var extensionFound = false
            list.forEach { ext ->
                if (ext.id == ID && ext.getMetadata()?.version == LAST_VERSION) {
                    extensionFound = true
                    // TODO update qwant vip at launch
                    /* Log.d("QWANT_BROWSER_EXTENSION", "Qwant Extension found: should try updating")
                    runtime.updateWebExtension(ext,
                        onSuccess = {
                            Log.d("QWANT_BROWSER_EXTENSION", "Qwant webextension updated: ${it?.getMetadata()}")
                        },
                        onError = { _, throwable ->
                            Log.e("QWANT_BROWSER_EXTENSION", "Error updating Qwant webextension", throwable)
                        }
                    ) */
                }
            }
            if (!extensionFound) {
                runtime.installWebExtension(
                    ID, URL,
                    onSuccess = { ext ->
                        runtime.setAllowedInPrivateBrowsing(
                            ext,
                            allowed = true,
                            onError = { throwable ->
                                Log.e("QWANT_BROWSER_EXTENSION","Error allowing Qwant WebExtension in private browsing", throwable)
                            }
                        )
                    },
                    onError = { _, throwable ->
                        Log.e("QWANT_BROWSER_EXTENSION","Error registering Qwant WebExtension", throwable)
                    }
                )
            }
        })

    }
}
*/