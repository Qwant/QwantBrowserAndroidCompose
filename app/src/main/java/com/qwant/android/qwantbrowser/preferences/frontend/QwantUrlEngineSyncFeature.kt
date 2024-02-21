package com.qwant.android.qwantbrowser.preferences.frontend

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.qwant.android.qwantbrowser.ext.getQwantSERPSearch
import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.ui.browser.BrowserScreenViewModel

@Composable
fun QwantUrlEngineSyncFeature(
    browserScreenViewModel: BrowserScreenViewModel
) {
    val currentUrl by browserScreenViewModel.currentUrl.collectAsState()
    val qwantUrl by browserScreenViewModel.qwantUrl.collectAsState()

    LaunchedEffect(currentUrl, qwantUrl) {
        currentUrl?.let { url ->
            if (url.isQwantUrl()) {
                val newUrl = browserScreenViewModel.qwantUseCases.getQwantUrl(
                    search = url.getQwantSERPSearch()
                )
                if (newUrl != url) {
                    browserScreenViewModel.sessionUseCases.loadUrl(newUrl)
                }
            }
        }
    }
}