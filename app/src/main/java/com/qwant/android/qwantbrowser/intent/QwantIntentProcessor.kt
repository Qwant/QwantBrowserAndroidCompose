package com.qwant.android.qwantbrowser.intent

import android.app.SearchManager
import android.content.Intent
import android.nfc.NfcAdapter
import com.qwant.android.qwantbrowser.usecases.QwantUseCases
import mozilla.components.browser.state.state.SessionState
import mozilla.components.browser.state.state.externalPackage
import mozilla.components.concept.engine.EngineSession
import mozilla.components.feature.tabs.TabsUseCases
import mozilla.components.support.ktx.kotlin.isUrl
import mozilla.components.support.ktx.kotlin.toNormalizedUrl
import mozilla.components.support.utils.SafeIntent
import mozilla.components.support.utils.WebURLFinder

class QwantIntentProcessor(
    private val tabsUseCases: TabsUseCases,
    private val qwantUseCases: QwantUseCases
) {
    private fun processViewIntent(intent: SafeIntent): Boolean {
        val url = intent.dataString

        return if (url.isNullOrEmpty()) {
            false
        } else {
            addNewTab(url.toNormalizedUrl(), SessionState.Source.External.ActionView(intent.externalPackage()))
            true
        }
    }

    private suspend fun processSendIntent(intent: SafeIntent): Boolean {
        val extraText = intent.getStringExtra(Intent.EXTRA_TEXT)

        return if (extraText.isNullOrBlank()) {
            false
        } else {
            val url = WebURLFinder(extraText).bestWebURL()
            val source = SessionState.Source.External.ActionSend(intent.externalPackage())
            if (url != null) {
                addNewTab(url, source)
            } else {
                qwantUseCases.openQwantPage(search = extraText) // TODO should add external source
            }
            true
        }
    }

    private suspend fun processSearchIntent(intent: SafeIntent): Boolean {
        val searchQuery = intent.getStringExtra(SearchManager.QUERY)

        return if (searchQuery.isNullOrBlank()) {
            false
        } else {
            val source = SessionState.Source.External.ActionSearch(intent.externalPackage())
            if (searchQuery.isUrl()) {
                addNewTab(searchQuery, source)
            } else {
                qwantUseCases.openQwantPage(search = searchQuery) // TODO should add external source
            }
            true
        }
    }

    private fun addNewTab(url: String, source: SessionState.Source) {
        tabsUseCases.addTab(
            url.toNormalizedUrl(),
            source = source,
            flags = EngineSession.LoadUrlFlags.external(),
        )
    }

    suspend fun process(intent: Intent): Boolean {
        val safeIntent = SafeIntent(intent)
        return when (safeIntent.action) {
            Intent.ACTION_VIEW, Intent.ACTION_MAIN -> processViewIntent(safeIntent) // TODO add, test and support nft ACTION_NDEF_DISCOVERED
            Intent.ACTION_SEND -> processSendIntent(safeIntent)
            Intent.ACTION_SEARCH, Intent.ACTION_WEB_SEARCH -> processSearchIntent(safeIntent)
            else -> false
        }
    }
}