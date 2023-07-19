package com.qwant.android.qwantbrowser.suggest

sealed class Suggestion(val provider: SuggestionProvider, val search: String) {
    class SearchSuggestion(provider: SuggestionProvider, search: String,
                           val text: String
    ): Suggestion(provider, search)
    class SelectTabSuggestion(provider: SuggestionProvider, search: String,
                              val tabId: String,
                              val title: String,
                              val url: String
    ): Suggestion(provider, search)
    class OpenTabSuggestion(provider: SuggestionProvider, search: String,
                            val title: String?,
                            val url: String?
    ): Suggestion(provider, search)
}