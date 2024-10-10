package com.qwant.android.qwantbrowser.vip

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import org.json.JSONObject
import org.mozilla.geckoview.WebExtension
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VIPState @Inject constructor() {
    internal var communicationPort: WebExtension.Port? = null

    var enabled by mutableStateOf(true)
        internal set

    fun toggleProtection() {
        communicationPort?.postMessage(JSONObject().also {
            it.put("action", "toggleProtection")
        })
    }
}