package com.qwant.android.qwantbrowser.preferences.frontend

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.qwant.android.qwantbrowser.ext.getQwantSERPSearch
import com.qwant.android.qwantbrowser.ui.browser.BrowserScreenViewModel

@Composable
fun QwantUrlEngineSyncFeature(
    browserScreenViewModel: BrowserScreenViewModel
) {
    val currentUrl by browserScreenViewModel.currentUrl.collectAsState()
    val isQwantUrlValid by browserScreenViewModel.isQwantUrlValid.collectAsState()

    LaunchedEffect(isQwantUrlValid) {
        if (!isQwantUrlValid) {
            val newUrl = browserScreenViewModel.qwantUseCases.getQwantUrl(
                search = currentUrl?.getQwantSERPSearch()
            )
            Log.d("QWANT_REFRESH", "refresh cause settings changed:\n$currentUrl\n$newUrl")
            browserScreenViewModel.sessionUseCases.loadUrl(newUrl)
        }
    }
}