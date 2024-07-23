package com.qwant.android.qwantbrowser.cookies

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.channels.Channel
import org.json.JSONObject
import org.mozilla.geckoview.WebExtension
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QwantCookieState @Inject constructor() {
    internal var communicationPort: WebExtension.Port? = null

    var isConnected by mutableStateOf(false)
        private set

    private val restoreChannel = Channel<Unit>(0)

    internal fun messageReceived(message: JSONObject) {
        Log.d("QWANT_COOKIES", "Received message in native app from webextension: ${message}")

        when (message.getString("code")) {
            "user_logged" -> isConnected = message.getBoolean("value")
            "restored" -> {
                if (message.getBoolean("value")) Log.d("QWANT_COOKIES", "Qwant cookies restored.")
                else Log.e("QWANT_COOKIES", "Failed restoring qwant cookies. Unlocking zap anyway.")
                restoreChannel.trySend(Unit)
            }
            else -> {}
        }

        communicationPort?.postMessage(JSONObject().also {
            it.put("code", "message_received")
        })
    }

    suspend fun restoreCookies(then: () -> Unit) {
        communicationPort?.postMessage(JSONObject().also {
            it.put("code", "restore_cookies")
        })
        restoreChannel.receive()
        then()
    }
}