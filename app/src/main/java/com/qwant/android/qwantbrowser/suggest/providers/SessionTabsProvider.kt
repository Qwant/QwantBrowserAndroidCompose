package com.qwant.android.qwantbrowser.suggest.providers

import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import mozilla.components.browser.state.store.BrowserStore

class SessionTabsProvider(
    val store: BrowserStore
): SuggestionProvider {
    override suspend fun getSuggestions(text: String): List<Suggestion> {
        return store.state.tabs
            .filter {
                it.content.title.contains(text, ignoreCase = true) || it.content.url.contains(text, ignoreCase = true)
                        && !it.content.url.isQwantUrl()
            }
            .take(3)
            .map { Suggestion.SelectTabSuggestion(this, text, it.id, it.content.title, it.content.url) }
    }
}