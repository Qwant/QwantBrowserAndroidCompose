package com.qwant.android.qwantbrowser.preferences.frontend

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.qwant.android.qwantbrowser.ext.getQwantSERPSearch
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import mozilla.components.browser.state.state.TabSessionState
import mozilla.components.feature.session.SessionUseCases

@Composable
fun QwantUrlEngineSyncFeature(
    qwantUrl: String,
    qwantTabs: List<TabSessionState>,
    loadUrlUseCases: SessionUseCases.DefaultLoadUrlUseCase,
    getQwantUrlUseCase: QwantUseCases.GetQwantUrlUseCase
) {
    LaunchedEffect(qwantUrl) {
        qwantTabs.forEach { tab ->
            if (tab.content.url.isQwantUrl()) {
                val newUrl = getQwantUrlUseCase.invoke(
                    search = tab.content.url.getQwantSERPSearch()
                )
                if (newUrl != tab.content.url) {
                    loadUrlUseCases.invoke(
                        url = getQwantUrlUseCase.invoke(
                            search = tab.content.url.getQwantSERPSearch()
                        ),
                        sessionId = tab.id
                    )
                }
            }
        }
    }
}