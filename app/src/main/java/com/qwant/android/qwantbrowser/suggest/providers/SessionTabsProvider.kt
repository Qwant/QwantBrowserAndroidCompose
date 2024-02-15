package com.qwant.android.qwantbrowser.suggest.providers

import com.qwant.android.qwantbrowser.ext.isQwantUrl
import com.qwant.android.qwantbrowser.suggest.Suggestion
import com.qwant.android.qwantbrowser.suggest.SuggestionProvider
import mozilla.components.browser.state.store.BrowserStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionTabsProvider @Inject constructor(
    val store: BrowserStore
): SuggestionProvider {
    override suspend fun getSuggestions(text: String): List<Suggestion> {
        return store.state.tabs
            .filter {
                it.content.title.contains(text, ignoreCase = true) || it.content.url.contains(text, ignoreCase = true)
                        && !it.content.url.isQwantUrl()
            }
            .take(2) // TODO make this suggestion limit a parameter
            .map { Suggestion.SelectTabSuggestion(this, text, it.id, it.content.title, it.content.url) }
    }
}