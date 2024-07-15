/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package com.qwant.android.qwantbrowser.vip

import android.util.Log
import mozilla.components.concept.engine.webextension.WebExtensionRuntime

/**
 * Feature to enable Qwant tracking protection extension
 */
object QwantVIPFeature {
    const val ID = "qwant-vip-android@qwant.com"
    private const val URL = "resource://android/assets/qwant_webext/"
    private const val LAST_VERSION = "7.3.0.2"

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
