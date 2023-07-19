package com.qwant.android.qwantbrowser.preferences.frontend

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.feature.session.SessionUseCases

@Composable
fun QwantUrlEngineSyncFeature(
    qwantUrl: String,
    qwantTabs: List<TabSessionState>,
    loadUrlUseCases: SessionUseCases.DefaultLoadUrlUseCase
) {
    LaunchedEffect(qwantUrl) {
        qwantTabs.forEach { tab ->
            val tabUrl = tab.content.url
            if (tabUrl.isQwantUrl()) {
                val query: String? = if (tabUrl.contains("?q=") || tabUrl.contains("&q=")) {
                    tabUrl.split("?q=", "&q=")[1].split("&")[0]
                } else null
                val reloadUrl = when (query) {
                    null -> qwantUrl
                    else -> "$qwantUrl&q=$query"
                }
                loadUrlUseCases.invoke(url = reloadUrl, sessionId = tab.id)
            }
        }
    }
}